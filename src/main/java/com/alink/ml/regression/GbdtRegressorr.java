package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.GbdtRegressor;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class GbdtRegressorr implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new GbdtRegressor()
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setLabelCol(map.getOrDefault("labelCol", "labelCol"))

                .setLearningRate(Utils.douOrDefault(map, "learningRate", "0.3"))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minSampleRatioPerChild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("minInfoGain", "0.0")))
                .setMaxLeaves(Utils.intOrDefault(map, "maxLeaves", "2147483647"))
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxDepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minSamplesPerLeaf", "2147483647")))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxBins", "128")))
                .setWeightCol(map.getOrDefault("weightCol", null))
//                .setCategoricalCols()
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setGroupCol(map.getOrDefault("groupCol", null))
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featureSubsamplingRatio", "1.0"))
                .setSubsamplingRatio(Utils.douOrDefault(map, "subsamplingRatio", "1.0"))
                .setNumTrees(Utils.intOrDefault(map, "numTrees", "100"))
                .setMinSumHessianPerLeaf(Utils.douOrDefault(map, "minSumHessianPerLeaf", "0.0"))

                ;
    }
}
