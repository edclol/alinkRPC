/**
 * Copyright 2020 bejson.com
 */
package com.alink.pipline.entry;


import java.util.List;

public class AlinkCanvas {

    private List<Node> nodes;
    private List<Paths> paths;

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setPaths(List<Paths> paths) {
        this.paths = paths;
    }

    public List<Paths> getPaths() {
        return paths;
    }

    @Override
    public String toString() {
        return "DLCanvas{" +
                "nodes=" + nodes +
                ", paths=" + paths +
                '}';
    }

    public AlinkCanvas(List<Node> nodes, List<Paths> paths) {
        this.nodes = nodes;
        this.paths = paths;
    }
}