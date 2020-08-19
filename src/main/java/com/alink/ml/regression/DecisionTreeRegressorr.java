package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.DecisionTreeRegressor;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class DecisionTreeRegressorr implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new DecisionTreeRegressor()
                //必须填入的参数
                .setPredictionCol(map.getOrDefault("predictionCol", ""))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setLabelCol(map.getOrDefault("labelCol", ""))

                //不是必须填入有默认值的参数
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxDepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minSamplesPerLeaf", "2")))
                .setCreateTreeMode(map.getOrDefault("createTreeMode", "series"))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxBins", "128")))
                .setMaxMemoryInMB(Integer.valueOf(map.getOrDefault("maxMemoryInMB", "64")))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setMaxLeaves(Utils.intOrDefault(map, "maxLeaves", "2147483647"))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minSampleRatioPerChild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("minInfoGain", "0.0")))

                ;


    }
}
