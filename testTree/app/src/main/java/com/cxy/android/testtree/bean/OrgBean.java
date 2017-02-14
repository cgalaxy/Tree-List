package com.cxy.android.testtree.bean;

import com.cxy.android.testtree.annotation.TreeNodeId;
import com.cxy.android.testtree.annotation.TreeNodeLabel;
import com.cxy.android.testtree.annotation.TreeNodePid;

/**
 * Created by chenxinying on 17/2/9
 */

public class OrgBean {
    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
