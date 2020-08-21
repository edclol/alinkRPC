package com.alink.ml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.regression.LinearRegPredictBatchOp;
import com.alibaba.alink.operator.batch.regression.LinearRegTrainBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.source.CsvSourceStreamOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.LinearSvm;
import com.alibaba.alink.pipeline.regression.LinearRegression;
import com.alink.ml.utils.Utils;

/**
 * Created by edc on 2020/8/14
 */
public class Pipline1_KMeansTrainBatchOp {

    public static void main(String[] args) throws Exception {

        //数据源
        String url = "/home/edc/linearSVM-data.csv";
        String schema = "f0 int, f1 int, label int";

        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter("\t")
                .setFilePath(url).setSchemaStr(schema);

        StreamOperator streamOp = new CsvSourceStreamOp()
                .setFieldDelimiter("\t")
                .setFilePath(url)
                .setSchemaStr(schema);



        BatchOperator lr = new LinearRegTrainBatchOp()
                .setFeatureCols(new String[]{"f0","f1"}).setLabelCol("label");


        BatchOperator pred = new LinearRegPredictBatchOp()
                .setPredictionCol("pred");



        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new LinearRegression()
                        .setFeatureCols("f0")
                        .setFeatureCols("f1")
                        .setLabelCol("label")
                        .setPredictionCol("pred"))

                ;

        PipelineModel pipelineModel = pipeline1.fit(trainData);

//        pipelineModel.save("/home/edc/alink-linearSVM.csv");

        StreamOperator transform = pipelineModel.transform(streamOp);
        transform.print(1,2);
        transform.execute();

//        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-linearSVM.csv");


    }

}
