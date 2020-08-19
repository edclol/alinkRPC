package com.alink.ml.clustering;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.BisectingKMeans;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class BisectingKMeanss implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new BisectingKMeans()
                .setVectorCol(map.getOrDefault("vectorCol", "vectorCol"))
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))

                .setMinDivisibleClusterSize(Utils.intOrDefault(map, "minDivisibleClusterSize", "1"))
                .setK(Utils.intOrDefault(map, "k", "4"))
                .setDistanceType(map.getOrDefault("distanceType", "EUCLIDEAN"))
                .setMaxIter(Utils.intOrDefault(map, "maxIter", "10"))
                .setPredictionDetailCol(map.getOrDefault("predictionDetailCol", "predictionDetailCol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))

                ;
    }
}
