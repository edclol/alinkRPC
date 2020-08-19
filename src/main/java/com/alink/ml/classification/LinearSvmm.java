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
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new LinearSvm()
                //必须填入的参数
                .setFeatureCols(Utils.strArrayOrNull(map, "featureCols"))
                .setLabelCol(map.getOrDefault("labelCol", "a"))

                //不是必须填入有默认值的参数
                .setWeightCol(map.getOrDefault("weightCol", null))
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))
                ;
    }

}
