package com.alink.ml.dataETL;

import com.alibaba.alink.common.io.filesystem.HadoopFileSystem;
import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sink.CsvSinkBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.batch.sql.SelectBatchOp;
import com.alibaba.alink.operator.batch.sql.UnionAllBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.dataproc.MinMaxScaler;
import com.alibaba.alink.pipeline.dataproc.MinMaxScalerModel;
import com.alink.ml.sql.SelectBatchOpp;
import com.alink.ml.utils.Config;
import com.alink.ml.utils.Utils;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by yh on 2020/8/25 下午6:00
 */
public class MinMaxScalerr   {
    public static Logger logger = LogManager.getLogger(SelectBatchOpp.class);

    public String fit(String parameter) {
        String str = "[{\"type\": \"dataframe\", \"schema\": [{\"column_name\": \"fea_0\"}]]";
        try {
            HashMap<String, String> map = Utils.json2map(parameter);
            //获取数据源
            System.out.println(map);

            BatchOperator trainData = getBatchOp(map.getOrDefault("input_data_path", "hdfs:/data/iris.csv"), Config.HADOOP_FSURI);
            Double min = Double.valueOf(map.getOrDefault("min", "0.0"));
            Double max = Double.valueOf(map.getOrDefault("max", "1.0"));
            String outputCols = map.getOrDefault("outputCols", "outputCols");
            String[] selecetCols = Utils.StringToFeature(map.getOrDefault("input_data_path", "none"), Config.HADOOP_FSURI);
            for(String i: selecetCols){
                System.out.println(i);
            }

            System.out.println(selecetCols);
            System.out.println("ddddddddd");
            //修改数据
            Pipeline pipeline = new Pipeline().add(new MinMaxScaler().setMin(min).setMax(max).setSelectedCols(selecetCols).setOutputCols(outputCols));
            PipelineModel model = pipeline.fit(trainData);
            BatchOperator finData = model.transform(trainData);


            //存储数据
            String output_data_path = map.getOrDefault("output_data_path", "hdfs:/data/iris.csv");
            String model_path = output_data_path + "_model";
            output_data_path = Config.HADOOP_FSURI + output_data_path.substring(5);

            model.save(output_data_path + "_model");

            System.out.println("存储数据  " + output_data_path);
            CsvSinkBatchOp csvSink = new CsvSinkBatchOp().setFilePath(output_data_path);
            finData.link(csvSink);

            String s = finData.getSchema().toString();
            System.out.println("-----------------------");


            //解析出schema
            String s1 = s.replaceAll("\\n|:", "")
                    .replace("root |-- ", "")
                    .replaceAll("\\|--", ",");
            System.out.println("解析出schema" + s1);


            //存储schema
            String outputPath = map.getOrDefault("output_data_path", "hdfs:/data/iris.csv");
            outputPath = "/root/schema" + outputPath.substring(outputPath.lastIndexOf("/")) + "_schema";
            System.out.println("存储schema " + outputPath);
            HadoopFileSystem hdfs = new HadoopFileSystem(Config.HADOOP_FSURI);

            FSDataOutputStream fs = hdfs.create(outputPath, FileSystem.WriteMode.OVERWRITE);
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
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("return   " + str);
        }
        return str;
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
