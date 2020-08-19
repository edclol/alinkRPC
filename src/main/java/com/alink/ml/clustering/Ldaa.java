package com.alink.ml.clustering;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.clustering.Lda;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class Ldaa implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
//        GbdtRegressor
        return new Lda()
                .setPredictionCol(map.getOrDefault("predictionCol", "predictionCol"))
                .setTopicNum(Utils.intOrDefault(map, "topicNum", ""))
                .setSelectedCol(map.getOrDefault("selectedCol", "selectedCol"))

                .setPredictionDetailCol(map.getOrDefault("predictionDetailCol", "predictionDetailCol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                .setAlpha(Utils.douOrDefault(map, "alpha", "-1.0"))
                .setBeta(Utils.douOrDefault(map, "beta", "-1.0"))
                .setMethod(map.getOrDefault("method", "em"))
                .setOnlineLearningOffset(Utils.douOrDefault(map, "onlineLearningOffset", "1024.0"))
                .setOnlineLearningDecay(Utils.douOrDefault(map, "learningDecay", "0.51"))
                .setOnlineSubSamplingRate(Utils.douOrDefault(map, "subsamplingRate", "0.05"))
                .setOptimizeDocConcentration(Utils.boolOrTrue(map, "optimizeDocConcentration"))
                .setNumIter(Utils.intOrDefault(map, "numIter", "10"))
                .setVocabSize(Utils.intOrDefault(map, "vocabSize", "262144"))

                ;
    }
}
