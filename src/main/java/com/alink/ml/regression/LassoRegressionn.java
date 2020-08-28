package com.alink.ml.regression;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.regression.LassoRegression;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class LassoRegressionn implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];

        return new LassoRegression()
                //必须设置
                .setLambda(Utils.douOrDefault(map, "lambda", ""))
                .setPredictionCol(map.getOrDefault("predictioncol", "predictioncol"))
                .setLabelCol(label)
                //有默认的设置
                .setOptimMethod(map.getOrDefault("optimmethod", null))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                .setVectorCol(map.getOrDefault("vectorcol", null))
                .setWithIntercept(Utils.boolOrTrue(map, "withintercept"))
                .setMaxIter(Utils.intOrDefault(map, "maxiter", "100"))
                .setEpsilon(Utils.douOrDefault(map, "epsilon", "1.0E-6"))
                .setFeatureCols(fea)
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setVectorCol(map.getOrDefault("vectorcol", null))
                .setStandardization(Utils.boolOrTrue(map, "standardization"))


                ;
    }
}
