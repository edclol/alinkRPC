package com.alink.pipline.entry;

import com.alibaba.alink.operator.stream.StreamOperator;

import java.util.*;

public class Node {

    private String id;
    private String name;
    private String icon;
    private String category;
    private String operator;
    private List<String> children;
    private HashMap<String, String> nodeParam;
    private int positionX;
    private int positionY;
    private boolean showStatus;
    private String class_id;
    private List<OutPorts> outPorts;
    private List<InPorts>inPorts;
    private Set<Node> prev = new HashSet<>();
    private Set<Node> next = new HashSet<>();
    private int status = 0;
    private List<StreamOperator> fixedOP = new ArrayList<>();




    public Boolean addNext(Node s) {
        return this.next.add(s);
    }

    public Boolean addPrev(Node s) {
        return this.prev.add(s);
    }
    public Boolean addFixedOP(StreamOperator s) {
        return this.fixedOP.add(s);
    }




    public StreamOperator[] getFixedOParray() {
        StreamOperator[] operators = new StreamOperator[fixedOP.size()];
        return fixedOP.toArray(operators);
    }




    public void setFixedOP(List<StreamOperator> fixedOP) {
        this.fixedOP = fixedOP;
    }


    public List<StreamOperator> getFixedOP() {
        return fixedOP;
    }
    public Set<Node> getPrev() {
        return prev;
    }

    public void setPrev(Set<Node> prev) {
        this.prev = prev;
    }

    public Set<Node> getNext() {
        return next;
    }

    public void setNext(Set<Node> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", category='" + category + '\'' +
                ", operator='" + operator + '\'' +
                ", children=" + children +
                ", nodeParam=" + nodeParam +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", showStatus=" + showStatus +
                ", class_id='" + class_id + '\'' +
                ", outPorts=" + outPorts +
                ", inPorts=" + inPorts +
                ", prev=" + prev +
                ", next=" + next +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public HashMap<String, String> getNodeParam() {
        return nodeParam;
    }

    public void setNodeParam(HashMap<String, String> nodeParam) {
        this.nodeParam = nodeParam;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public List<OutPorts> getOutPorts() {
        return outPorts;
    }

    public void setOutPorts(List<OutPorts> outPorts) {
        this.outPorts = outPorts;
    }

    public List<InPorts> getInPorts() {
        return inPorts;
    }

    public void setInPorts(List<InPorts> inPorts) {
        this.inPorts = inPorts;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
