package com.alink.ml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.LinearSvm;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_LinearSvm {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/linearSVM-data.csv";
        String schema = "f0 int, f1 int, label int";

        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter("\t")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new LinearSvm()
                        .setFeatureCols("f0")
                        .setFeatureCols("f1")
                        .setLabelCol("label")
                        .setPredictionCol("pred"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

        pipelineModel.save("/home/edc/alink-linearSVM.csv");

        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-linearSVM.csv");
        pipelineModel1.transform(trainData).print();

    }
}
