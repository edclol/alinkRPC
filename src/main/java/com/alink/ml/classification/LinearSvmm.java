package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.LinearSvm;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class LinearSvmm implements BaseModule {

    @Override
    public PipelineStageBase getModule(HashMap<String, String> map,String schemaStr) {
        //读取标签和特征再封装
        HashMap<String, String[]> feaLab = Utils.StringToFeatureLabel(schemaStr);
        String[] fea = feaLab.getOrDefault("fea",null);
        String label = feaLab.get("label")[0];
        return new LinearSvm()
                //必须填入的参数
                .setFeatureCols(fea)
                .setLabelCol(label)

                //不是必须填入有默认值的参数
                .setWeightCol(map.getOrDefault("weightcol", null))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedcols"))
                ;
    }

}
