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

        return new KMeans()
                //必须设置的参数
                .setVectorCol(map.getOrDefault("vectorcol", "vectorcol"))
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-4"))
                .setK(Utils.intOrDefault(map, "k", "2"))
                .setInitMode(map.getOrDefault("initmode", "K_MEANS_PARALLEL"))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "20"))
                .setDistanceType(map.getOrDefault("distancetype", "EUCLIDEAN"))
                .setPredictionDistanceCol(map.getOrDefault("predictiondistancecol", "predictiondistancecol"))

                ;
    }
}
