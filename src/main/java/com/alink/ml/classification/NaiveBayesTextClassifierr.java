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
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new NaiveBayesTextClassifier()
                //必须设置的参数
                .setLabelCol(map.getOrDefault("labelcol", "labelcol"))
                .setVectorCol(map.getOrDefault("vectorcol", "vectorcol"))
                .setPredictionCol(map.getOrDefault("predictioncol", "f1"))
                //有默认值的参数
                .setModelType(map.getOrDefault("modeltype", "multinomial"))
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setSmoothing(Double.valueOf(map.getOrDefault("smoothing", "1.0")))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                ;
    }
}
