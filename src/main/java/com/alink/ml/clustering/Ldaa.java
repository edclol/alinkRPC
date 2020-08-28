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
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {


        return new Lda()
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))
                .setTopicNum(Utils.intOrDefault(map, "topicnum", ""))
                .setSelectedCol(map.getOrDefault("selectedcol", "selectedcol"))
                //有默认值的参数
                .setPredictionDetailCol(map.getOrDefault("predictiondetailcol", "predictiondetailcol"))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setAlpha(Utils.douOrDefault(map, "alpha", "-1.0"))
                .setBeta(Utils.douOrDefault(map, "beta", "-1.0"))
                .setMethod(map.getOrDefault("method", "em"))
                .setOnlineLearningOffset(Utils.douOrDefault(map, "onlinelearningoffset", "1024.0"))
                .setOnlineLearningDecay(Utils.douOrDefault(map, "learningdecay", "0.51"))
                .setOnlineSubSamplingRate(Utils.douOrDefault(map, "subsamplingrate", "0.05"))
                .setOptimizeDocConcentration(Utils.boolOrTrue(map, "optimizedocconcentration"))
                .setNumIter(Utils.intOrDefault(map, "numiter", "10"))
                .setVocabSize(Utils.intOrDefault(map, "vocabsize", "262144"))

                ;
    }
}
