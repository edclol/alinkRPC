/**
 * Copyright 2020 bejson.com
 */
package com.alink.pipline.entry;



/**
 *
 */


public class InPorts {

    private String id;
    private boolean isConnected;

    @Override
    public String toString() {
        return "InPorts{" +
                "id='" + id + '\'' +
                ", isConnected=" + isConnected +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}