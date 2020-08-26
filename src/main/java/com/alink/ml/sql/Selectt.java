package com.alink.ml.sql;

import com.alibaba.alink.pipeline.PipelineStageBase;
import com.alibaba.alink.pipeline.sql.Select;
import com.alink.ml.utils.BaseModule;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/25 下午5:15
 */
public class Selectt implements BaseModule {
    @Override
    public PipelineStageBase getModule(HashMap<String, String> map) {
        return new Select()
                .setClause(map.getOrDefault("clause",""))
                ;
    }
}
