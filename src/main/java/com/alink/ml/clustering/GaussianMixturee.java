package com.alink.ml.clustering;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.GaussianMixture;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class GaussianMixturee implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new GaussianMixture()
                .setVectorCol(map.getOrDefault("vectorCol", "vectorCol"))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))

                .setK(Utils.intOrDefault(map, "k", "2"))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "100"))
                .setPredictionDetailCol(map.getOrDefault("predictionDetailCol", "predictionDetailCol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "0.01"))

                ;
    }
}
