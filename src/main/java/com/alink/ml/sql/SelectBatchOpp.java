package com.alink.ml.sql;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.sql.SelectBatchOp;
import com.alink.ml.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Created by edc on 2020/8/25 下午5 :00
 */
public class SelectBatchOpp {

    public static Logger logger = LogManager.getLogger(SelectBatchOpp.class);

    public String fit(String parameter) {
        HashMap<String, String> map = Utils.json2map(parameter);

        //读取hadoop上schema的数据
        String schemaStr = Utils.readSchemaFromHDFS(map);

        //读取数据
        BatchOperator trainData = Utils.readBatchOpFromHDFS(map, schemaStr);

        //修改数据
        SelectBatchOp selectBatchOp = new SelectBatchOp().setClause(map.getOrDefault("clause", "fea_0"));
        BatchOperator link = trainData.link(selectBatchOp);


        //存储数据更新schame 返回解析后并处理的schema
        String str = Utils.saveDFAndSchema2str(map, link);

        return str;

    }


}
