package com.cxy.android.testtree.utils;

import android.util.Log;

import com.cxy.android.testtree.R;
import com.cxy.android.testtree.annotation.TreeNodeId;
import com.cxy.android.testtree.annotation.TreeNodeLabel;
import com.cxy.android.testtree.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenxinying on 17/2/9
 */

public class TreeHelper {
    private static final String TAG = "TreeHelper";

    /**
     * 将用户的收据转换为树形数据
     *
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        for (T data : datas) {
            int id = -1;
            int pId = -1;
            String label = null;
            Class clazz = data.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //判断该field上是否有这个注解
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);//设置访问权限,如果字段是private,可以强制访问
                    id = field.getInt(data);//得到int型的，TreeNodeId注解字段值
                    //getInt（）方法可能会产生异常，抛出去让用户知道
                    //如果是绝对不会发生的异常，自己可以try catch消化掉

                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);//设置访问权限,如果字段是private,可以强制访问
                    pId = field.getInt(data);//得到int型的，TreeNodeId注解字段值
                    //getInt（）方法可能会产生异常，抛出去让用户知道
                    //如果是绝对不会发生的异常，自己可以try catch消化掉

                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);//设置访问权限,如果字段是private,可以强制访问
                    label = (String) field.get(data);//得到int型的，TreeNodeId注解字段值
                    //getInt（）方法可能会产生异常，抛出去让用户知道
                    //如果是绝对不会发生的异常，自己可以try catch消化掉
                }

            }
            node = new Node(id, pId, label);
            nodes.add(node);
        }


        //开始设置关联关系 ,拿每个节点出来，与剩下的节点做比较
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }
        //设置完关联关系，给node设置图标
        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 为node设置图标，没有子view和有子view（有子view，子view是否展开）设置向下或者向右的箭头
     *
     * @param node
     */
    public static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0) {
            node.setIcon(node.isExpand() ? R.drawable.tree_ex : R.drawable.tree_ec);
        } else {
            node.setIcon(-1);
        }
    }

    /**
     * 为了暴露更少的api，写了一个方法进行封装
     * <p>
     * 拿出所有根节点，再拿到叶子
     *
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertDatas2Nodes(datas);
        //获取树的根节点
        List<Node> rootNodes = getRootNodes(nodes);
        for (Node node : rootNodes) {

            addNode(result, node, defaultExpandLevel, 1);
        }

        return result;
    }

    /**
     * 从所有节点中获取根节点
     *
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 通过根node,将子节点，子节点的节点，按顺序排列好
     *
     * @param result
     * @param node
     * @param defaultExpandLevel 默认展示行数
     * @param currentLevel       当前行数
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);//如果默认展开的行数，比当前根节点（level＝1）大，说明此根节点展开了
        }
        if (node.isLeaf()) //如果当前是根节点又是叶子节点，没有子view，就不需要继续
            return;
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * 过滤出需要显示的节点
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes) {

        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()||node.isParentExpand()) {//如果是根节点
                setNodeIcon(node);
                result.add(node);
            }
        }
        Log.e(TAG," result size "+ result.size());
        return result;
    }
}





































