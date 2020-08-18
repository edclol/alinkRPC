package com.alink.ml;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.dataproc.SplitBatchOp;
import com.alibaba.alink.operator.batch.recommendation.AlsTrainBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.KMeansModel;
import com.alibaba.alink.pipeline.dataproc.vector.VectorAssembler;
import com.alibaba.alink.pipeline.recommendation.ALS;
import com.alibaba.alink.pipeline.recommendation.ALSModel;

/**
 * Created by edc on 2020/8/6
 */
public class Pipline_ALS {
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

        trainData.print();

        //构建管道
        Pipeline pipeline = new Pipeline();
        Pipeline pipeline1 = pipeline
                .add(new ALS().setUserCol("userid")
                        .setItemCol("movieid")
                        .setRateCol("rating")
                        .setNumIter(10).setRank(10).setLambda(0.1)
                        .setPredictionCol("pred_rating"));

        PipelineModel pipelineModel = pipeline1.fit(trainData);

        pipelineModel.save("/home/edc/alink-als.csv");

        pipelineModel.transform(testData).select("rating,pred_rating").print();

    }
}
