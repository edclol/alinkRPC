package com.alink.ml.sql;

import com.alibaba.alink.common.io.filesystem.HadoopFileSystem;
import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sink.CsvSinkBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.batch.sql.SelectBatchOp;
import com.alibaba.alink.operator.batch.sql.UnionAllBatchOp;
import com.alink.ml.utils.Config;
import com.alink.ml.utils.Utils;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.FileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edc on 2020/8/25 下午5:00
 */
public class SelectBatchOpp {

    public static Logger logger = LogManager.getLogger(SelectBatchOpp.class);

    public static void main(String[] args) {


        String fit = new SelectBatchOpp().fit("{\"input_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-100\", \"output_data_path\": \"hdfs:/user/experiment/tmp/1e139b2c-d789-11ea-b72e-000c29c9d8a2-90\", \"clause\": \"fea_0,fea_1,fea_2\"}");

        System.out.println("return   ---------------- " + fit);


    }

    public String fit(String parameter) {

        String str = "[{\"type\": \"dataframe\", \"schema\": [{\"column_name\": \"fea_0\"}]]";
        try {
            HashMap<String, String> map = Utils.json2map(parameter);
            //获取数据源
            System.out.println(map);

            BatchOperator trainData = getBatchOp(map.getOrDefault("input_data_path", "hdfs:/data/iris.csv"), Config.HADOOP_FSURI);

            trainData.print();
            System.out.println("ddddddddd");
            //修改数据
            BatchOperator selected = trainData.link(new SelectBatchOp().setClause(map.getOrDefault("clause", "fea_0")));

            selected.print();


            //存储数据
            String input_data_path = map.getOrDefault("output_data_path", "hdfs:/data/iris.csv");
            input_data_path = Config.HADOOP_FSURI + input_data_path.substring(5);
//            input_data_path

            System.out.println("存储数据  " + input_data_path);
            CsvSinkBatchOp csvSinkBatchOp = new CsvSinkBatchOp()
                    .setOverwriteSink(true)
                    .setFieldDelimiter(",")
                    .setFilePath(input_data_path);
            BatchOperator csvSinkBatchOp1 = csvSinkBatchOp.link(selected);
            String s = csvSinkBatchOp1.getSchema().toString();
            System.out.println("-----------------------");
            BatchOperator.execute();

            //解析出schema
            String s1 = s.replaceAll("\\n|:", "")
                    .replace("root |-- ", "")
                    .replaceAll("\\|--", ",");
            System.out.println("解析出schema" + s1);


            ///321
            //存储schema
            String outpath = "/root/schema" + map.getOrDefault("input_data_path", "hdfs:/data/iris.csv").substring(20) + "_schema";
            System.out.println("存储schema " + outpath);
            HadoopFileSystem hdfs = new HadoopFileSystem(Config.HADOOP_FSURI);

            FSDataOutputStream fs = hdfs.create(outpath, FileSystem.WriteMode.OVERWRITE);
            fs.write(s1.getBytes());
            fs.close();

            //return
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

            str = Utils.pGson.toJson(list1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            logger.info("return   " + str);
            return str;
        }

//        return "[{\"type\": \"dataframe\", \"schema\": [{\"column_name\": \"fea_0\", \"column_type\": \"int\"}, {\"column_name\": \"fea_1\", \"column_type\": \"int\"}, {\"column_name\": \"fea_2\", \"column_type\": \"int\"}, {\"column_name\": \"fea_3\", \"column_type\": \"int\"}, {\"column_name\": \"fea_4\", \"column_type\": \"int\"}, {\"column_name\": \"fea_5\", \"column_type\": \"int\"}, {\"column_name\": \"fea_6\", \"column_type\": \"int\"}, {\"column_name\": \"fea_7\", \"column_type\": \"int\"}, {\"column_name\": \"fea_8\", \"column_type\": \"int\"}, {\"column_name\": \"fea_9\", \"column_type\": \"int\"}]}]";
    }

    /**
     * 读取hdfs上的csv文件 或者文件夹下的所有csv文件
     *
     * @param path "hdfs:/user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00"
     * @param uri  "hdfs://master:9000"
     * @return
     */
    public static BatchOperator getBatchOp(String path, String uri) {
        try {

            path = path.substring(5);
            logger.info("BatchOperator " + path);
            System.out.println(path);

            String schemaStr = Utils.getSchema(path, uri);

            logger.info("BatchOperator " + schemaStr);

            HadoopFileSystem hdfs = new HadoopFileSystem(uri);

            if (hdfs.getFileStatus(path).isDir()) {

                List<String> strings = hdfs.listFiles(path);

                BatchOperator[] Batch = new BatchOperator[strings.size() - 1];

                int i = 0;
                for (String string : strings) {
                    if (!string.equals(uri + path + "/_SUCCESS")) {

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
                        .setFilePath(uri + path)
                        .setSchemaStr(schemaStr)
                        .setIgnoreFirstLine(true);
                return csvSourceBatchOp;
            }
        } catch (Exception e) {
            logger.error("getBatchOp error ");
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {

        }
        return new CsvSourceBatchOp();

    }


}
