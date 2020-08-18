package com.alink.ml;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.DecisionTreeClassifier;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_DecisionTreeRegressor_load {
    public static void main(String[] args) throws Exception {
        //数据源
        String filepath = "/home/edc/exampleData.csv";
        String schema = "f0 double, f1 string, f2 bigint, f3 bigint ,label bigint";

        BatchOperator trainData = new CsvSourceBatchOp()
                .setFilePath(filepath).setSchemaStr(schema);

        trainData.print();

        //构建管道
        PipelineModel pipelineModel = PipelineModel.load("/home/edc/alink-decisionTree.csv");

        pipelineModel.transform(trainData).print();

    }
}
