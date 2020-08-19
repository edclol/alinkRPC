package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.NaiveBayesTextClassifier;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class NaiveBayesTextClassifierr implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new NaiveBayesTextClassifier()
                //必须设置的参数
                .setLabelCol(map.getOrDefault("labelCol", ""))
                .setVectorCol(map.getOrDefault("vectorCol", ""))
                .setPredictionCol(map.getOrDefault("predictionCol", "f1"))
                //有默认值的参数
                .setModelType(map.getOrDefault("modelType", "Multinomial"))
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setSmoothing(Double.valueOf(map.getOrDefault("smoothing", "1.0")))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                ;
    }
}
