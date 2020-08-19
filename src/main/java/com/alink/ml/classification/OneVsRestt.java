package com.alink.ml.classification;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.classification.OneVsRest;
import com.alink.ml.utils.BaseModule;
import com.alink.ml.utils.Utils;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/14
 */
public class OneVsRestt implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new OneVsRest()
                //必须设置的参数
                .setNumClass(Utils.intOrDefault(map, "numClass", "2"))
                .setPredictionCol(map.getOrDefault("predictionCol", "preCol"))

                //有默认值的参数
                .setReservedCols(Utils.strArrayOrNull(map, "reservedCols"))


                ;
    }
}
