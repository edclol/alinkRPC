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
                .setLabelCol(label)
                .setFeatureCols(fea)
                .setPredictionCol(map.getOrDefault("predictioncol", "precol"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setLearningRate(Utils.douOrDefault(map, "learningrate", "0.3"))
                .setMinSumHessianPerLeaf(Utils.douOrDefault(map, "minsumhessianperleaf", "0.0"))
                .setNumTrees(Utils.intOrDefault(map, "numtrees", "100"))
                .setMinSamplesPerLeaf(Utils.intOrDefault(map, "minsamplesperleaf", "100"))
                .setMaxDepth(Utils.intOrDefault(map, "maxdepth", "6"))
                .setSubsamplingRatio(Utils.douOrDefault(map, "subsamplingratio", "1.0"))
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featuresubsamplingratio", "1.0"))
                .setGroupCol(map.getOrDefault("groupcol", null))
                .setMaxBins(Utils.intOrDefault(map, "maxbins", "128"))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setMaxLeaves(Utils.intOrDefault(map, "maxleaves", "2147483647"))
                .setMinSampleRatioPerChild(Utils.douOrDefault(map, "minsampleratioperchild", "0.0"))
                .setMinInfoGain(Utils.douOrDefault(map, "mininfogain", "0.0"))
                ;
    }
}
