package com.alink.ml.utils;

import com.alibaba.alink.common.io.filesystem.HadoopFileSystem;
import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sink.CsvSinkBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.batch.sql.UnionAllBatchOp;
import com.alink.ml.test.ParamsBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.flink.core.fs.FSDataInputStream;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.FileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 常用工具类
 * Created by edc on 2020/8/12
 */
public final class Utils {

    public static Gson pGson =
            new GsonBuilder()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .serializeSpecialFloatingPointValues()
                    .create();

    public static Logger logger = LogManager.getLogger(Utils.class);


    /**
     * 解析出特征列
     *
     * @param schemastr
     * @return
     */
    public static String[] StringToFeature(String schemastr) {
        String[] arr = schemastr.split(" , ");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].substring(0, arr[i].indexOf(" "));
        }
        return arr;
    }

    /**
     * 解析出特征和标签列
     *
     * @param schemastr
     * @return
     */
    public static HashMap<String, String[]> StringToFeatureLabel(String schemastr) {

        String[] arr = schemastr.split(" , ");

        String[] fea = new String[arr.length - 1];
        String[] label = new String[1];

        for (int i = 0; i < arr.length; i++) {
            if (i != fea.length) {
                fea[i] = arr[i].substring(0, arr[i].indexOf(" "));
                System.out.println(fea[i]);
            } else {
                label[0] = arr[i].substring(0, arr[i].indexOf(" "));
                break;
            }
        }
        System.out.println("feature_length:" + fea.length);
        HashMap<String, String[]> hashMap = new HashMap<>();
        hashMap.put("fea", fea);
        hashMap.put("label", label);
        return hashMap;
    }

    /**
     * 存储数据更新schame 返回解析后并处理的schema
     *
     * @param map
     * @param batchOperator
     * @return
     */
    public static String saveDFAndSchema2str(HashMap<String, String> map, BatchOperator batchOperator) {
        //存储数据
        String input_data_path = map.getOrDefault("output_data_path", "hdfs:/data/iris.csv");
        input_data_path = Config.HADOOP_FSURI + input_data_path.substring(5);


        System.out.println("存储数据  " + input_data_path);

        CsvSinkBatchOp csvSink = new CsvSinkBatchOp().setOverwriteSink(true).setFilePath(input_data_path);
        csvSink.linkFrom(batchOperator);

        String s = batchOperator.getSchema().toString();
        System.out.println("-----------------------");

        //解析出schema
        String s1 = s.replaceAll("\\n|:", "")
                .replace("root |-- ", "")
                .replaceAll("\\|--", ",");
        System.out.println("解析出schema" + s1);

        //存储schema
        FSDataOutputStream fs = null;
        try {
            String path = map.getOrDefault("input_data_path", "hdfs:/data/iris.csv");
            String outpath = "/root/schema" + path.substring(path.lastIndexOf("/")) + "_schema";
            System.out.println("存储schema " + outpath);
            HadoopFileSystem hdfs = new HadoopFileSystem(Config.HADOOP_FSURI);
            fs = hdfs.create(outpath, FileSystem.WriteMode.OVERWRITE);
            fs.write(s1.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //返回
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (String s2 : s1.split(",")) {
            String[] s4 = s2.trim().split(" ");
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("column_name", s4[0]);
            map1.put("column_type", s4[1]);
            list.add(map1);
        }

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("type", "dataframe");
        map1.put("schema", list);

        ArrayList<Object> list1 = new ArrayList<>();
        list1.add(map1);
        return Utils.pGson.toJson(list1);
    }

    /**
     * 读取hdfs上的csv文件 或者文件夹下的所有csv文件
     *
     * @param str                  "hdfs:/user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00"
     * @param "hdfs://master:9000"
     * @return
     */
    public static BatchOperator readBatchOpFromHDFS(HashMap<String, String> params, String schemaStr) {
        try {

            //截掉不需要的hdfs： 前缀 /user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00
            String path = params.getOrDefault("input_data_path", "hdfs:/data/iris.csv").substring(5);

            logger.info("path is " + path);
            System.out.println("path is " + path);


            logger.info("schemaStr  is " + schemaStr);
            System.out.println("schemaStr  is " + schemaStr);

            //分情况读取所有的数据 读取成BatchOpertor并返回
            HadoopFileSystem hdfs = new HadoopFileSystem(Config.HADOOP_FSURI);
            if (hdfs.getFileStatus(path).isDir()) {

                List<String> strings = hdfs.listFiles(path);
                BatchOperator[] Batch = new BatchOperator[strings.size() - 1];
                int i = 0;
                for (String string : strings) {
                    if (!string.equals(Config.HADOOP_FSURI + path + "/_SUCCESS")) {

//                        logger.info("遍历的目录地址 " + string);
                        CsvSourceBatchOp csvSourceBatchOp = new CsvSourceBatchOp()
                                .setFilePath(string)
                                .setSchemaStr(schemaStr)
                                .setIgnoreFirstLine(true);
                        Batch[i] = csvSourceBatchOp;
                        i++;
                    }
                }

                return new UnionAllBatchOp().linkFrom(Batch);
            } else {

                CsvSourceBatchOp csvSourceBatchOp = new CsvSourceBatchOp()
                        .setFilePath(Config.HADOOP_FSURI + path)
                        .setSchemaStr(schemaStr)
                        .setIgnoreFirstLine(true);

                return csvSourceBatchOp;
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        } finally {


        }
        logger.error("读取数据源失败");
        System.out.println("读取数据源失败");
        return new CsvSourceBatchOp();
    }

    /**
     * 从hdfs上读取schema文件 获取schemaStr
     *
     * @param path                 "hdfs:/user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00"
     * @param "hdfs://master:9000"
     * @return "f0 double,f1 int"
     */
    public static String readSchemaFromHDFS(HashMap<String, String> params) {

        try {
            String input_data_path = params.getOrDefault("input_data_path", "hdfs:/data/iris.csv");

            String path1 = input_data_path.substring(5);
            String rrr = "/root/schema" + path1.substring(path1.lastIndexOf("/")) + "_schema";
            System.out.println("schema_path" + rrr);

            FSDataInputStream in = new HadoopFileSystem(Config.HADOOP_FSURI).open(rrr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String s = bufferedReader.readLine();
            System.out.println("getSchema  " + s);
            return s.trim();
        } catch (IOException e) {
            System.out.println("getSchema  error");
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 解析参数 参数对照参考api
     *
     * @param para json字符串
     * @return 解析的hashmap
     */
    public static HashMap<String, String> json2map(String para) {
        HashMap map = pGson.fromJson(para, HashMap.class);
        logger.info("json2map params is " + map);
        HashMap<String, String> map1 = new HashMap<>();
        map.forEach((o, o2) -> map1.put(o.toString(), o2.toString()));
        return map1;
    }


    /**
     * 从总的参数map里面获取str数组参数若没有返回null
     *
     * @param parameter
     * @param key
     * @return
     */
    public static String[] strArrayOrNull(HashMap<String, String> parameter, String key) {
        if (parameter.getOrDefault(key, "---").equals("---")) {
            return null;
        } else {
            String s = parameter.get("reservedCols");
            String s1 = s.substring(1, s.length() - 1);
            return s1.split(",");
        }

    }

    /**
     * 从总的参数map里面获取str数组参数若没有返回null
     *
     * @param parameter
     * @param key
     * @return
     */
    public static double[] douArrayOrDeafult(HashMap<String, String> parameter, String key, double[] dou) {
        if (parameter.getOrDefault(key, "---").equals("---")) {
            return dou;
        } else {
            String s = parameter.get(key);
            String[] split = s.substring(1, s.length() - 1).split(",");
            return Arrays.stream(split).mapToDouble(Double::parseDouble).toArray();
        }

    }

    /**
     * 从总的参数map里面获取int数组若没有返回null
     *
     * @param parameter
     * @param key
     * @return
     */
    public static int[] intArrayOrNull(HashMap<String, String> parameter, String key) {

        if (parameter.getOrDefault(key, "---").equals("---")) {
            return null;
        } else {
            String s = parameter.get(key);
            String[] split = s.substring(1, s.length() - 1).split(",");
            return Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        }

    }


    /**
     * 从总的参数map里面获取bool若没有返回true
     *
     * @param parameter
     * @param key
     * @return
     */
    public static Boolean boolOrTrue(HashMap<String, String> parameter, String key) {

        return parameter.getOrDefault(key, "True").equals("True");

    }

    /**
     * 从总的参数map里面获取bool若没有返回false
     *
     * @param parameter
     * @param key
     * @return
     */
    public static Boolean boolOrFalse(HashMap<String, String> parameter, String key) {

        return !parameter.getOrDefault(key, "False").equals("False");

    }

    public static Double douOrDefault(HashMap<String, String> parameter, String key, String value) {

        return Double.valueOf(parameter.getOrDefault(key, value));
    }

    public static Integer intOrDefault(HashMap<String, String> parameter, String key, String value) {

        return Integer.valueOf(parameter.getOrDefault(key, value));
    }

    //测试用
    public static void main(String[] args) throws Exception {

//        BatchOperator batchOp = getBatchOp("/user/experiment/tmp/0131b504-ac6c-11ea-a731-000c2960831c-110", Config.HADOOP_FSURI);
//        System.out.println(batchOp.count());

//        System.out.println("hdfs:/user/experiment/tmp/0131b504-ac6c-11ea-a731-000c2960831c-110".substring(5));
//        logger.info("aaaa");

//        String str = "[2,2,3]";
//        System.out.println(str.substring(1, str.length() - 1));
//
//        System.out.println(Double.valueOf("222"));


        String sss = "{\"input_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-100\", \"output_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-90\", \"clause\": \"fea_0,fea_1,fea_2\"}";
        System.out.println(Utils.json2map(sss));

        ParamsBase paramsBase = new ParamsBase();
        HashMap<String, String> map = new HashMap<>();
        Object put = map.put("1", "3");

        paramsBase.setParams(map);
        String s = pGson.toJson(map);
        System.out.println(s);
    }


}
