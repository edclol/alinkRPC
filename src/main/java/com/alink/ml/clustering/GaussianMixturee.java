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


        return new GaussianMixture()
                .setVectorCol(map.getOrDefault("vectorcol", "vectorcol"))
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))

                .setK(Utils.intOrDefault(map, "k", "2"))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "100"))
                .setPredictionDetailCol(map.getOrDefault("predictiondetailcol", "predictiondetailcol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "0.01"))

                ;
    }
}
