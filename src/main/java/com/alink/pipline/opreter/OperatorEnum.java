package com.alink.pipline.opreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edc on 2020/8/20 下午3:51
 */
public enum OperatorEnum {

    FILTER("FilterStreamOpp", "FilterStreamOpp"),
    IN("InputOp","InputOp"),
    SELECT("SelectStreamOpp","SelectStreamOpp"),
    UNION_ALL("UnionAllStreamOpp","UnionAllStreamOpp"),
    WHERE("WhereStreamOpp","WhereStreamOpp"),
    AS("AsStreamOpp","AsStreamOpp"),
    SPLIT("SplitStreamOp","SplitStreamOp"),
    ;

    private final String cmd;
    private final String className;

    OperatorEnum(String cmd, String className) {
        this.cmd = cmd;
        this.className = className;
    }

    public String getCmd() {
        return cmd;
    }

    public String getClassName() {
        return className;
    }

    public static Map<String, String> getAllClassName() {
        Map<String, String> map = new HashMap<String, String>(16);
        for (OperatorEnum status : values()) {
            map.put(status.getCmd(), "com.alink.pipline.opreter.stream" + status.getClassName());
        }
        return map;
    }

}
