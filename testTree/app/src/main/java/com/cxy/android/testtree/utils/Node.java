package com.cxy.android.testtree.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxinying on 17/2/9
 */

public class Node {
    private int id;
    /**
     * 跟节点的pId=0
     */
    private int pId = 0;
    private String name;
    /**
     * 树的深度
     */
    private int level;
    /**
     * 是否是展开
     */
    private boolean isExpand = false;
    private int icon;

    private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node() {

    }

    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 父节点是否是展开状态
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 根据当前节点，得到当前节点的层级
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node child : children) {
                child.setExpand(false);
            }
        }
    }
}
