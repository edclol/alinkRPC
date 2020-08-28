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
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new GbdtRegressor()
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))
                .setFeatureCols(fea)
                .setLabelCol(label)

                //有默认值的参数
                .setLearningRate(Utils.douOrDefault(map, "learningrate", "0.3"))
                .setMinSampleRatioPerChild(Double.valueOf(map.getOrDefault("minsampleratioperchild", "0.0")))
                .setMinInfoGain(Double.valueOf(map.getOrDefault("mininfogain", "0.0")))
                .setMaxLeaves(Utils.intOrDefault(map, "maxleaves", "2147483647"))
                .setMaxDepth(Integer.valueOf(map.getOrDefault("maxdepth", "2147483647")))
                .setMinSamplesPerLeaf(Integer.valueOf(map.getOrDefault("minsamplesperleaf", "2147483647")))
                .setMaxBins(Integer.valueOf(map.getOrDefault("maxbins", "128")))
                .setWeightCol(map.getOrDefault("weightcol", null))
//                .setCategoricalCols()
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setGroupCol(map.getOrDefault("groupcol", null))
                .setFeatureSubsamplingRatio(Utils.douOrDefault(map, "featuresubsamplingratio", "1.0"))
                .setSubsamplingRatio(Utils.douOrDefault(map, "subsamplingratio", "1.0"))
                .setNumTrees(Utils.intOrDefault(map, "numtrees", "100"))
                .setMinSumHessianPerLeaf(Utils.douOrDefault(map, "minsumHessianperleaf", "0.0"))

                ;
    }
}
