package com.alink.mlml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.LassoRegression;
import com.alink.mlml.utils.BaseModule;
import com.alink.mlml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class LassoRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new LassoRegression()
                //必须设置
                .setLambda(Utils.douOrDefault(map, "lambda", ""))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))
                .setLabelCol(map.getOrDefault("labelCol", "labelCol"))
                //有默认的设置
                .setOptimMethod(map.getOrDefault("optimMethod", null))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setVectorCol(map.getOrDefault("vectorCol", null))
                .setWithIntercept(Utils.boolOrTrue(map, "withIntercept"))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setVectorCol(map.getOrDefault("vectorCol", null))
                .setStandardization(Utils.boolOrTrue(map, "standardization"))


                ;
    }
}
