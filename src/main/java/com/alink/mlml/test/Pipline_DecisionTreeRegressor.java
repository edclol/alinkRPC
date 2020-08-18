package com.alink.mlml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.DecisionTreeClassifier;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_DecisionTreeRegressor {
    public static void main(String[] args) throws Exception {
        //数据源
        String filepath = "/home/edc/exampleData.csv";
        String schema = "f0 double, f1 string, f2 bigint, f3 bigint ,label bigint";

        BatchOperator trainData = new CsvSourceBatchOp()
                .setFilePath(filepath).setSchemaStr(schema);

        trainData.print();

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new DecisionTreeClassifier()
                        .setFeatureCols("f0")
                        .setFeatureCols("f1")
                        .setFeatureCols("f2")
                        .setFeatureCols("f3")
                        .setLabelCol("label")
//                        .setPredictionDetailCol(null)
                        .setPredictionCol("pred"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

//        pipelineModel.save("/home/edc/alink-decisionTree.csv");
        pipelineModel.save("hdfs://master:9000/data/alink-decisionTree.csv");

        pipelineModel.transform(trainData).print();

    }
}
