package com.cxy.android.testtree;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.cxy.android.testtree.bean.FileBean;
import com.cxy.android.testtree.utils.Node;
import com.cxy.android.testtree.utils.adapter.TreeRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private TreeRecyclerAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recyclerview 控件的layout_width／layout_height 为 match_parent，
        //计算recyclerview高度的时候，无需再遍历getview，如果是wrap_content，会看到
        //初始化的时候getview被调用多次
        initDatas();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        try {
            mAdapter = new TreeRecyclerAdapter<>(MainActivity.this, mDatas, 1);
            initEvent();
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * recyclerview 长按事件
     */
    private void initEvent() {
        //使用自定义布局的dialog时， 官方推荐使用 DialogFragment
        mAdapter.setOnTreeNodeClickListenr(new TreeRecyclerAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(final Node node, final int position) {
                //longclicklistener 和 clicklistener 全return false 时，可以同时触发
                //longclicklistener return true 时，longclick会触发，click不会触发
                /*if (node.isLeaf()) {
                    Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
                }*/
                final EditText et=new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("Add Node")
                        .setView(et)
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.addExtraNode(position,et.getText().toString());
                    }
                }).setNegativeButton("cancel",null)
                .show();
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        FileBean bean = new FileBean(1, 0, "根目录1");
        mDatas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        mDatas.add(bean);
        bean = new FileBean(3, 0, "根目录3");
        mDatas.add(bean);
        bean = new FileBean(4, 1, "根目录1-1");
        mDatas.add(bean);
        bean = new FileBean(5, 1, "根目录1-2");
        mDatas.add(bean);
        bean = new FileBean(6, 5, "根目录1-2-1");
        mDatas.add(bean);
        bean = new FileBean(7, 3, "根目录3-1");
        mDatas.add(bean);
        bean = new FileBean(8, 3, "根目录3-2");
        mDatas.add(bean);
    }
}
