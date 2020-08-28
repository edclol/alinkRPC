package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.DecisionTreeClassifier;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/12
 */
public final class DecisionTreeClassifierr implements BaseModule {

    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new DecisionTreeClassifier()
                //必须填入的参数
                .setPredictionCol(map.getOrDefault("predictioncol", ""))
                .setFeatureCols(fea)
                .setLabelCol(label)

                //不是必须填入有默认值的参数
                .setTreeType(map.getOrDefault("treetype", "avg"))
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxdepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minsamplesperleaf", "2")))
                .setCreateTreeMode(map.getOrDefault("createtreemode", "series"))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxbins", "128")))
                .setMaxMemoryInMB(Integer.valueOf(map.getOrDefault("maxmemoryinmb", "64")))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setMaxLeaves(Utils.intOrDefault(map, "maxleaves", "2147483647"))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minsampleratioperchild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("mininfogain", "0.0")))
                ;

    }
}
