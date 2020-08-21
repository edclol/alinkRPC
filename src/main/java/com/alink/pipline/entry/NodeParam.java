/**
 * Copyright 2020 bejson.com
 */
package com.alink.pipline.entry;



/**
 *
 */


public class NodeParam {

    private String input_shape;
    private String batch_size;
    private String batch_input_shape;
    private String dtype;
    private String input_tensor;
    private String sparse;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getInput_shape() {
        return input_shape;
    }

    public void setInput_shape(String input_shape) {
        this.input_shape = input_shape;
    }

    public String getBatch_size() {
        return batch_size;
    }

    public void setBatch_size(String batch_size) {
        this.batch_size = batch_size;
    }

    public String getBatch_input_shape() {
        return batch_input_shape;
    }

    public void setBatch_input_shape(String batch_input_shape) {
        this.batch_input_shape = batch_input_shape;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getInput_tensor() {
        return input_tensor;
    }

    public void setInput_tensor(String input_tensor) {
        this.input_tensor = input_tensor;
    }

    public String getSparse() {
        return sparse;
    }

    public void setSparse(String sparse) {
        this.sparse = sparse;
    }
}