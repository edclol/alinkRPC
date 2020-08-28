package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.RandomForestClassifier;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class RandomForestClassifierr implements BaseModule {

    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new RandomForestClassifier()
                //必须填入的参数
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setLabelCol(map.getOrDefault("labelCol", "labelCol"))

                //不是必须填入有默认值的参数
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featureSubsamplingRatio", "0.2"))
                .setNumSubsetFeatures(Integer.valueOf(map.getOrDefault("numSubsetFeatures", "2147483647")))
                .setNumTrees(Integer.valueOf(map.getOrDefault("numTrees", "10")))
                .setSubsamplingRatio(Double.valueOf(map.getOrDefault("subsamplingRatio", "100000.0")))
                .setTreeType(map.getOrDefault("treeType", "avg"))
                .setReservedCols(map.getOrDefault("reservedCols", null))
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxDepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minSamplesPerLeaf", "2")))
                .setCreateTreeMode(map.getOrDefault("createTreeMode", "series"))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxBins", "128")))
                .setMaxMemoryInMB(Integer.valueOf(map.getOrDefault("maxMemoryInMB", "64")))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setMaxLeaves(Integer.valueOf(map.getOrDefault("maxLeaves", "2147483647")))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minSampleRatioPerChild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("minInfoGain", "0.0")))

                ;
    }
}
