package com.alink.mlml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.AftSurvivalRegression;
import com.alink.mlml.utils.BaseModule;
import com.alink.mlml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class AftSurvivalRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new AftSurvivalRegression()
                .setCensorCol(map.getOrDefault("censorCol", "censorCol"))
                .setLabelCol(map.getOrDefault("labelCol", "labelCol"))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))

                .setQuantileProbabilities(Utils.douArrayOrDeafult(
                        map,
                        "quantileProbabilities",
                        new double[]{0.01, 0.05, 0.1, 0.25, 0.5, 0.75, 0.9, 0.95, 0.99}))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setWithIntercept(Utils.boolOrTrue(map, "withIntercept"))
                .setVectorCol(map.getOrDefault("vectorCol", null))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setL1(Utils.douOrDefault(map, "l1", "0.0"))
                .setL2(Utils.douOrDefault(map, "l2", "0.0"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setVectorCol(map.getOrDefault("vectorCol", null))
                .setPredictionDetailCol(map.getOrDefault("predictionDetailCol", "predictionDetailCol"))


                ;
    }
}
