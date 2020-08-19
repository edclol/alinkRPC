package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.GeneralizedLinearRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class GeneralizedLinearRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {

        return new GeneralizedLinearRegression()
                .setLabelCol(map.getOrDefault("labelCol", "labelCol"))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))


                .setFamily(map.getOrDefault("family", "gaussian"))
                .setVariancePower(Utils.douOrDefault(map, "variancePower", "0.0"))
                .setLink(map.getOrDefault("link", null))
                .setLinkPower(Utils.douOrDefault(map, "linkPower", "1.0"))
                .setOffsetCol(map.getOrDefault("offsetCol", null))
                .setFitIntercept(Utils.boolOrTrue(map, "fitIntercept"))
                .setRegParam(Utils.douOrDefault(map, "regParam", "0.0"))
                .setLinkPredResultCol(map.getOrDefault("linkPredResultCol", null))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "10"))


                ;


    }
}
