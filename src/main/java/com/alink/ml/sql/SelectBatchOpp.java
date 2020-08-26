package com.alink.ml.sql;

import com.alibaba.alink.common.io.filesystem.HadoopFileSystem;
import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sink.CsvSinkBatchOp;
import com.alibaba.alink.operator.batch.sql.SelectBatchOp;
import com.alink.ml.utils.Config;
import com.alink.ml.utils.Utils;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.FileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by edc on 2020/8/25 下午5 :00
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

            BatchOperator trainData = Utils.readBatchOpFromHDFS(map.getOrDefault("input_data_path", "hdfs:/data/iris.csv"));
//            trainData.print();
//            System.out.println("ddddddddd");
            //修改数据
            BatchOperator selected = trainData.link(new SelectBatchOp().setClause(map.getOrDefault("clause", "fea_0")));

            //存储数据
            saveBatchOp(map, selected);

            //解析出新的schema
            String s1 = parseShema(selected);

            //存储新的schema
            saveNewSchema(map, s1);

            //return
            str = schema2return(s1);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("return   " + str);
        }
        return str;

    }

    private String schema2return(String s1) {
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
        String str = Utils.pGson.toJson(list1);
        return str;
    }

    private void saveNewSchema(HashMap<String, String> map, String s1)  {
        FSDataOutputStream fs=null;
       try{

           String outpath = "/root/schema" + map.getOrDefault("input_data_path", "hdfs:/data/iris.csv").substring(20) + "_schema";
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

    }

    private String parseShema(BatchOperator selected) {
        String s = selected.getSchema().toString();
        System.out.println("-----------------------");

        //解析出schema
        String s1 = s.replaceAll("\\n|:", "")
                .replace("root |-- ", "")
                .replaceAll("\\|--", ",");
        System.out.println("解析出schema" + s1);
        return s1;
    }

    private void saveBatchOp(HashMap<String, String> map, BatchOperator selected) {
        String input_data_path = map.getOrDefault("output_data_path", "hdfs:/data/iris.csv");
        input_data_path = Config.HADOOP_FSURI + input_data_path.substring(5);


        System.out.println("存储数据  " + input_data_path);

        CsvSinkBatchOp csvSink = new CsvSinkBatchOp().setOverwriteSink(true).setFilePath(input_data_path);
//            selected.link(csvSink);
        csvSink.linkFrom(selected);
    }


}
