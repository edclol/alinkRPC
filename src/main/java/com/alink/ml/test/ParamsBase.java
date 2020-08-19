package com.alink.ml.test;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by edc on 2020/8/11
 */
public class ParamsBase {
    private HashMap<String,String> params;

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ParamsBase{" +
                "params=" + params +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParamsBase that = (ParamsBase) o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }
}
