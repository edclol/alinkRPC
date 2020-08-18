package com.alink.ml;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.NaiveBayesTextClassifier;
import com.alibaba.alink.pipeline.classification.RandomForestClassifier;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_NaiveBayesTextClassifier {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/alink-NaiveBayesTextClassifier-data.csv";
        String schema = "sv string, dv string, label string";


        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter(",")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new NaiveBayesTextClassifier()
                        .setVectorCol("sv")
                        .setLabelCol("label")
                        .setReservedCols("sv")
                        .setReservedCols("label")
                        .setPredictionCol("pred"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

        pipelineModel.save("/home/edc/alink-NaiveBayesTextClassifier.csv");
        pipelineModel.transform(trainData).print();

//        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-NaiveBayesTextClassifier.csv");
//        pipelineModel1.transform(trainData).print();

    }
}
