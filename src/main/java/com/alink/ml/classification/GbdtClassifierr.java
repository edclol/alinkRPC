package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.GbdtClassifier;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class GbdtClassifierr implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new GbdtClassifier()
                //必须设置的参数
                .setLabelCol(map.getOrDefault("labelCol", "label"))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setPredictionCol(map.getOrDefault("predictionCol", "preCol"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setLearningRate(Utils.douOrDefault(map, "learningRate", "0.3"))
                .setMinSumHessianPerLeaf(Utils.douOrDefault(map, "minSumHessianPerLeaf", "0.0"))
                .setNumTrees(Utils.intOrDefault(map, "numTrees", "100"))
                .setMinSamplesPerLeaf(Utils.intOrDefault(map, "minSamplesPerLeaf", "100"))
                .setMaxDepth(Utils.intOrDefault(map, "maxDepth", "6"))
                .setSubsamplingRatio(Utils.douOrDefault(map, "subsamplingRatio", "1.0"))
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featureSubsamplingRatio", "1.0"))
                .setGroupCol(map.getOrDefault("groupCol", null))
                .setMaxBins(Utils.intOrDefault(map, "maxBins", "128"))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setMaxLeaves(Utils.intOrDefault(map, "maxLeaves", "2147483647"))
                .setMinSampleRatioPerChild(Utils.douOrDefault(map, "minSampleRatioPerChild", "0.0"))
                .setMinInfoGain(Utils.douOrDefault(map, "minInfoGain", "0.0"))
                ;
    }
}
