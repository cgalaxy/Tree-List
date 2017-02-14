package com.cxy.android.testtree.utils.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxy.android.testtree.R;
import com.cxy.android.testtree.utils.Node;
import com.cxy.android.testtree.utils.TreeHelper;

import java.util.List;


/**
 * Created by chenxinying on 17/2/13
 * <p>
 * 因为构造函数传递了泛型参数，类也需要加上泛型
 * <p>
 * abstract 让用户只用写些 可变的方法
 */

public class TreeRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TreeRecyclerAdapter";
    private Context mContext;
    private List<Node> mAllNodes;
    private List<Node> mVisibleNodes;
    private LayoutInflater mInflater;

    private OnTreeNodeClickListener mListener;

    /**
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认显示几级根节点（如果传递数据有错，就显示0级）
     */
    public TreeRecyclerAdapter(Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        Log.e(TAG, "mAllNodes" + mAllNodes.size());
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        Log.e(TAG, "mVisibleNodes" + mVisibleNodes.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        return new MyItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyItemHolder) {
            ((MyItemHolder) holder).setData(mVisibleNodes.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mVisibleNodes.size();
    }

    class MyItemHolder<T> extends RecyclerView.ViewHolder {
        int position;
        Node mNode;
        ImageView mIcon;
        TextView mText;
        RelativeLayout rl;

        public MyItemHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.id_item_icon);
            mText = (TextView) itemView.findViewById(R.id.id_item_text);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }

        void setData(Node node, int pst) {
            position = pst;
            mNode = node;
            mText.setText(node.getName());
            itemView.setPadding(mNode.getLevel() * 30, 3, 3, 3);
            if (node.getIcon() == -1) {
                mIcon.setVisibility(View.INVISIBLE);
            } else {
                mIcon.setVisibility(View.VISIBLE);
                mIcon.setImageResource(node.getIcon());
            }
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandOrCollapse(position);
                }
            });
            rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(mVisibleNodes.get(position), position);
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 展开或者收缩item
     */
    private void expandOrCollapse(int position) {
        Node node = mVisibleNodes.get(position);
        if (node != null) {
            if (node.isLeaf()) {
                return;
            }
            Log.e(TAG, "expandOrCollapse " + !node.isExpand());
            node.setExpand(!node.isExpand());
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置Node的点击回调
     */
    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    public void setOnTreeNodeClickListenr(OnTreeNodeClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 动态插入节点
     *
     * @param position
     * @param content
     */
    public void addExtraNode(int position, String content) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);
        //如果需要存到服务器／数据库，首先请求服务器，新增数据，服务器返回对象，取出id，我们再做显示
        Node extraNode = new Node(-1, node.getId(), content);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

//    public abstract View getConvertView(Node node, int position, View convertView);
}
























