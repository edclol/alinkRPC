package com.alink.mlml.clustering;

import com.alibaba.alink.operator.batch.associationrule.FpGrowthBatchOp;
import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.KMeans;
import com.alink.mlml.utils.BaseModule;
import com.alink.mlml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class KMeanss implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {


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
