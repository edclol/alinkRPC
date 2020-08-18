package com.alink.ml;

import com.alibaba.alink.common.io.filesystem.FilePath;
import com.alibaba.alink.common.io.filesystem.HadoopFileSystem;
import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sink.CsvSinkBatchOp;
import com.alibaba.alink.operator.batch.source.AkSourceBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.stream.source.AkSourceStreamOp;
import org.apache.flink.core.fs.FileStatus;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
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

        csvSource.print();

//        CsvSinkBatchOp csvSinkBatchOp = new CsvSinkBatchOp().sinkFrom(csvSource)
////                .setFilePath("hdfs://master:9000/data/iris.txt")
//                .setFilePath(new FilePath("hdfs://master:9000/data/iris.txt"))
//                .setOverwriteSink(true);

//        BatchOperator.execute();

  /*      try {
            String dsf = "hdfs://master:9000/data/iris.csv";
            Configuration conf = new Configuration();

            FileSystem fs = FileSystem.get(URI.create(dsf),conf);

            FSDataInputStream hdfsInStream = fs.open(new Path(dsf));
            String s = hdfsInStream.readLine();
            byte[] ioBuffer = new byte[1024];
            int readLen = hdfsInStream.read(ioBuffer);
            while(readLen!=-1)
            {
                System.out.write(ioBuffer, 0, readLen);
                readLen = hdfsInStream.read(ioBuffer);
            }
            hdfsInStream.close();
            fs.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        byte[] buffer = new byte[123343];
        new HadoopFileSystem("hdfs://master:9000/data/ss.csv").open("hdfs://master:9000/data/ss.csv").read(buffer);
        String s = Arrays.toString(buffer);
        System.out.println(s);
    }
}
