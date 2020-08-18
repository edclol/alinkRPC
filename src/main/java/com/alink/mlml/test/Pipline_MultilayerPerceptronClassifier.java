package com.alink.mlml.test;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.MultilayerPerceptronClassifier;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_MultilayerPerceptronClassifier {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/iris.csv";
        String schema = "sepal_length double, sepal_width double, petal_length double, petal_width double, category string";


        BatchOperator trainData = new CsvSourceBatchOp()
//                .setFieldDelimiter(",")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();

        Pipeline pipeline1 = pipeline
                .add(new MultilayerPerceptronClassifier()
                        .setFeatureCols(new String[]{"sepal_length", "sepal_width", "petal_length", "petal_width"})
                        .setLabelCol("category")
                        .setLayers(new int[]{4, 5, 3})
                        .setMaxIter(20)
                        .setPredictionCol("pred_label")
                        .setPredictionDetailCol("pred_detail"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

//        pipelineModel.save("/home/edc/alink-MultilayerPerceptronClassifier.csv");
//        pipelineModel.transform(trainData).firstN(4).print();


        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-MultilayerPerceptronClassifier.csv");
        pipelineModel1.transform(trainData).firstN(4).print();

    }
}
