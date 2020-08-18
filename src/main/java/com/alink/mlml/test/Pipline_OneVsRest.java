package com.alink.mlml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.LogisticRegression;
import com.alibaba.alink.pipeline.classification.OneVsRest;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_OneVsRest {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/iris.csv";
        String schema = "sepal_length double, sepal_width double, petal_length double, petal_width double, category string";


        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter(",")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new OneVsRest()
                        .setClassifier(new LogisticRegression()
                                .setFeatureCols("sepal_length", "sepal_width", "petal_length", "petal_width")
                                .setLabelCol("category")
                                .setMaxIter(100)
                        )
                        .setNumClass(3)
                        .setPredictionCol("pred_result")
                        .setPredictionDetailCol("pred_detail")
                );

        PipelineModel pipelineModel = pipeline1.fit(trainData);

        pipelineModel.save("/home/edc/alink-OneVsRest.csv");
        pipelineModel.transform(trainData).print();
//
//        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-OneVsRest.csv");
//        pipelineModel1.transform(trainData).print();

    }
}
