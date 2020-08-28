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
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {

        return new BisectingKMeans()
                //必须设置的参数
                .setVectorCol(map.getOrDefault("vectorcol", "vectorcol"))
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))

                //有默认值的参数
                .setMinDivisibleClusterSize(Utils.intOrDefault(map, "mindivisibleclustersize", "1"))
                .setK(Utils.intOrDefault(map, "k", "4"))
                .setDistanceType(map.getOrDefault("distancetype", "EUCLIDEAN"))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "10"))
                .setPredictionDetailCol(map.getOrDefault("predictiondetailcol", "predictiondetailcol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))

                ;
    }
}
