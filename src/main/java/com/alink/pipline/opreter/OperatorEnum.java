package com.alink.pipline.opreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edc on 2020/8/20 下午3:51
 */
public enum OperatorEnum {

    FILTER("filter", "FilterOperter"),
    JOIN("join", ""),
    IN("in","InputOp"),
    AS("in","InputOp"),
    GROUP_BY("in","InputOp"),
    SELECT("in","InputOp"),
    ORDER_BY("in","InputOp"),
    SPLIT("in","InputOp"),
    UNION("in","InputOp"),
    WHERE("in","InputOp"),
    UNION_ALL("in","InputOp"),
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
