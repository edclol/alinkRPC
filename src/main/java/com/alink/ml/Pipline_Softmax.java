package com.alink.ml;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.dataproc.SampleBatchOp;
import com.alibaba.alink.operator.batch.dataproc.SampleWithSizeBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.params.dataproc.HasStrategy;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.classification.GbdtClassifier;
import com.alibaba.alink.pipeline.classification.Softmax;
import com.alibaba.alink.pipeline.dataproc.Imputer;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_Softmax {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "/home/edc/alink-Softmax-data.csv";
        String schema = "f0 int, f1 int, label int";


        BatchOperator trainData = new CsvSourceBatchOp()
                .setFieldDelimiter(",")
                .setFilePath(url).setSchemaStr(schema);

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new Imputer()
                        .setSelectedCols("f0")
                        .setStrategy(HasStrategy.Strategy.MAX)
                )
//                .add()
                .add(new Softmax()
                        .setFeatureCols("f0", "f1")
                        .setLabelCol("label")
                        .setPredictionCol("pred")
                );


        PipelineModel pipelineModel = pipeline1.fit(trainData);

//        pipelineModel.save("/home/edc/alink-Softmax.csv");
        System.out.println(pipelineModel.getParams());
        pipelineModel.save("hdfs://master:9000/data/alink-Softmax.csv");
        pipelineModel.transform(trainData).print();

//        PipelineModel pipelineModel1 = PipelineModel.load("/home/edc/alink-Softmax.csv");
//        pipelineModel1.transform(trainData).print();

    }
}
