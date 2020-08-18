package com.alink.mlml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.GbdtClassifier;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_GbdtClassifier {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/exampleData.csv";
        String schema = "f0 double, f1 string, f2 bigint, f3 bigint ,label bigint";


        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter(",")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new GbdtClassifier()
                        .setLearningRate(1.0)
                        .setNumTrees(3)
                        .setMinSamplesPerLeaf(1)
                        .setPredictionDetailCol("pred_detail")
                        .setPredictionCol("pred")
                        .setLabelCol("label")
                        .setFeatureCols("f0", "f1", "f2", "f3"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

//        pipelineModel.save("/home/edc/alink-GbdtClassifier.csv");
//        pipelineModel.transform(trainData).print();

        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-GbdtClassifier.csv");
        pipelineModel1.transform(trainData).print();

    }
}
