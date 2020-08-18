package com.alink.ml;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.dataproc.SplitBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.recommendation.ALS;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_ALS_load {
    public static void main(String[] args) throws Exception {
        //数据源
        String url = "https://alink-release.oss-cn-beijing.aliyuncs.com/data-files/movielens_ratings.csv";
        String schema = "userid bigint, movieid bigint, rating double, timestamp string";

        BatchOperator data = new CsvSourceBatchOp()
                .setFilePath(url).setSchemaStr(schema);

        SplitBatchOp spliter = new SplitBatchOp().setFraction(0.8);
        spliter.linkFrom(data);

        BatchOperator trainData = spliter;
        BatchOperator testData = spliter.getSideOutput(0);

        //构建管道
        PipelineModel pipelineModel = PipelineModel.load("/home/edc/alink-als.csv");

        pipelineModel.transform(testData).print();

    }
}
