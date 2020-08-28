package com.alink.ml.clustering;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.KMeans;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class KMeanss implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];

        return new KMeans()
                //必须设置的参数
                .setVectorCol(map.getOrDefault("vectorCol", "vectorCol"))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-4"))
                .setK(Utils.intOrDefault(map, "k", "2"))
                .setInitMode(map.getOrDefault("initMode", "K_MEANS_PARALLEL"))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "20"))
                .setDistanceType(map.getOrDefault("distanceType", "EUCLIDEAN"))
                .setPredictionDistanceCol(map.getOrDefault("predictionDistanceCol", "predictionDistanceCol"))

                ;
    }
}
