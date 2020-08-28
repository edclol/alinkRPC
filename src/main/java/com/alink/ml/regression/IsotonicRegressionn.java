package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.IsotonicRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class IsotonicRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];

        return new IsotonicRegression()
                .setLabelCol(map.getOrDefault("labelcol","labelcol"))
                .setPredictionCol(map.getOrDefault("predictioncol","predictioncol"))

                .setFeatureCol(map.getOrDefault("featurecol",null))
                .setIsotonic(Utils.boolOrTrue(map,"isotonic"))
                .setFeatureIndex(Utils.intOrDefault(map,"featureindex","0"))
                .setWeightCol(map.getOrDefault("weightcol",null))
                .setVectorCol(map.getOrDefault("vectorcol",null))

                ;
    }
}
