package com.alink.mlml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.LinearRegression;
import com.alink.mlml.utils.BaseModule;
import com.alink.mlml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class LinearRegressionn implements BaseModule {

    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new LinearRegression()
                .setPredictionCol(map.getOrDefault("predictionCol","predictionCol"))
                .setLabelCol(map.getOrDefault("labelCol","labelCol"))

                .setFeatureCols(Utils.strArrayOrNull(map,"featureCols"))
                .setReservedCols(Utils.strArrayOrNull(map,"reservedCols"))
                .setWeightCol(map.getOrDefault("weightCol",null))
                .setOptimMethod(map.getOrDefault("optimMethod",null))
                .setVectorCol(map.getOrDefault("vectorCol",null))
                .setL1(Utils.douOrDefault(map,"l1","0.0"))
                .setL2(Utils.douOrDefault(map,"l2","0.0"))
                .setWithIntercept(Utils.boolOrTrue(map,"withIntercept"))
                .setMaxIter(Utils.intOrDefault(map,"maxIter","100"))
                .setEpsilon(Utils.douOrDefault(map,"epsilon","1.0E-6"))
                .setWeightCol(map.getOrDefault("vectorCol",null))
                .setStandardization(Utils.boolOrTrue(map,"standardization"))

                ;
    }
}
