package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.IsotonicRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class IsotonicRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new IsotonicRegression()
                .setLabelCol(map.getOrDefault("labelCol","labelCol"))
                .setPredictionCol(map.getOrDefault("predictionCol","predictionCol"))

                .setFeatureCol(map.getOrDefault("featureCol",null))
                .setIsotonic(Utils.boolOrTrue(map,"isotonic"))
                .setFeatureIndex(Utils.intOrDefault(map,"featureIndex","0"))
                .setWeightCol(map.getOrDefault("weightCol",null))
                .setVectorCol(map.getOrDefault("vectorCol",null))

                ;
    }
}
