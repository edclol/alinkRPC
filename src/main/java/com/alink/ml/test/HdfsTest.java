package com.alink.ml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.batch.sql.UnionAllBatchOp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edc on 2020/8/11
 */
public class HdfsTest {
    public static void main(String[] args) throws Exception {

//        System.out.println(new HadoopFileSystem("hdfs://master:9000").listDirectories("/"));


//        AkSourceBatchOp akSourceBatchOp = new AkSourceBatchOp().setFilePath("hdfs://master:9000/data/als_modle.csv");
//        akSourceBatchOp.print();


//        hadoopFileSystem.listFiles("/").stream().forEach(System.out::println);
//        hadoopFileSystem.listDirectories("/").stream().forEach(System.out::println);


//        new HadoopFileSystem("hdfs://master:9000").listDirectories("/").stream().forEach(System.out::println);

//        System.out.println(new HadoopFileSystem("hdfs://master:9000"));


//        new AkSourceStreamOp().setFilePath("hdfs://master:9000/data/als_modle.csv").print();

        String filePath = "hdfs://master:9000/data/iris.csv";
        String schema = "sepal_length double, sepal_width double, petal_length double, petal_width double, category string";
        CsvSourceBatchOp csvSource = new CsvSourceBatchOp().setFilePath(filePath)
                .setSchemaStr(schema)
                .setFieldDelimiter(",");

        System.out.println(csvSource.count());


        CsvSourceBatchOp csvSource2 = new CsvSourceBatchOp().setFilePath(filePath)
                .setSchemaStr(schema)
                .setFieldDelimiter(",");

        List<BatchOperator<CsvSourceBatchOp>> list = new ArrayList<>();


        list.add(csvSource);
        list.add(csvSource2);

        BatchOperator[] batchOperators = new BatchOperator[list.size()];




        UnionAllBatchOp unionAllBatchOp = new UnionAllBatchOp();
        UnionAllBatchOp unionAllBatchOp1 = unionAllBatchOp.linkFrom(new BatchOperator[]{});
        System.out.println(unionAllBatchOp.count());


//        System.out.println(csvSourceBatchOp.count());
//      /user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00

   /*     String path = "/user/experiment/tmp/14b9ba80-853f-11ea-92f2-000c29192c75-00";
        String uri = "hdfs://master:9000";

        HadoopFileSystem hdfs = new HadoopFileSystem(uri);
        if (hdfs.getFileStatus(path).isDir()) {

            List<String> strings = hdfs.listFiles(path);

            HashMap<Object, CsvSourceBatchOp> map = new HashMap<>();

            for (String string : strings) {
                if (!string.equals(uri+path+"/_SUCCESS")){

                    System.out.println(string);
                    CsvSourceBatchOp csvSourceBatchOp = new CsvSourceBatchOp()
                            .setFilePath(string)
                            .setIgnoreFirstLine(true);

                    map.put(1,csvSourceBatchOp);
                }
            }


        }else {

        }
*/
    }
}
