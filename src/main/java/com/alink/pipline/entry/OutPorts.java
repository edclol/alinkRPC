/**
 * Copyright 2020 bejson.com
 */
package com.alink.pipline.entry;


/**
 */

public class OutPorts {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public OutPorts(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OutPorts{" +
                "id='" + id + '\'' +
                '}';
    }
}