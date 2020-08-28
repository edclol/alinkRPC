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
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))
                .setFeatureCols(fea)
                .setLabelCol(label)

                //不是必须填入有默认值的参数
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featuresubsamplingratio", "0.2"))
                .setNumSubsetFeatures(Integer.valueOf(map.getOrDefault("numsubsetfeatures", "2147483647")))
                .setNumTrees(Integer.valueOf(map.getOrDefault("numtrees", "10")))
                .setSubsamplingRatio(Double.valueOf(map.getOrDefault("subsamplingratio", "100000.0")))
                .setTreeType(map.getOrDefault("treetype", "avg"))
                .setReservedCols(map.getOrDefault("reservedcols", null))
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxdepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minsamplesperleaf", "2")))
                .setCreateTreeMode(map.getOrDefault("createtreemode", "series"))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxbins", "128")))
                .setMaxMemoryInMB(Integer.valueOf(map.getOrDefault("maxmemoryinmb", "64")))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setMaxLeaves(Integer.valueOf(map.getOrDefault("maxleaves", "2147483647")))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minsampleratioperchild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("mininfogain", "0.0")))

                ;
    }
}
