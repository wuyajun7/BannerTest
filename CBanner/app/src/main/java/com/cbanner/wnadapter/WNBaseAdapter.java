package com.cbanner.wnadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wuyajun on 15/6/19.
 * 万能适配器
 */
public abstract class WNBaseAdapter<T extends Object> extends BaseAdapter {

    public int default_MaxEms = 99;
    public DecimalFormat df = new DecimalFormat("#0.00");

    public Date mCurrDate = null;

    protected Context mContext;
    protected List<?> mDatas;
    protected LayoutInflater mInflater;
    protected int mItemId;

    public WNBaseAdapter(Context context, List<?> datas, int itemId) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
        mItemId = itemId;
        //获取当前时间
        mCurrDate = new Date();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public Object getBaseItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public Object getItem(int position) {
        if (position == -1) return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object item = getBaseItem(position);
        if (item instanceof WNBaseAdapter.ItemStatus) {
            int status = ((ItemStatus) item).status;
            switch (status) {
                case ItemStatus.EMPTY:
                    View empty = getEmptyView();
                    empty.setTag(ItemStatus.EMPTY);
                    return empty;
                case ItemStatus.FIRST:
                    View first = getFirstView();
                    first.setTag(ItemStatus.FIRST);
                    return first;
                case ItemStatus.END:
                    View end = getEndView();
                    end.setTag(ItemStatus.END);
                    return end;
                default:
                    return getEmptyView();
            }
        } else {
            if (convertView != null && (!(convertView.getTag() instanceof WNViewHolder) || ((WNViewHolder) convertView.getTag()).mType != 0)) {
                convertView = null;
            }
            WNViewHolder holder = WNViewHolder.getViewHolder(mContext, convertView, parent, mItemId, position);
            convertView(holder, (T) getItem(position), position);
            return holder.getConvertView();
        }
    }

    public View getEmptyView() {
        return new View(mContext);
    }

    public View getFirstView() {
        return new View(mContext);
    }

    public View getEndView() {
        return new View(mContext);
    }

    public abstract void convertView(WNViewHolder holder, T t, int position);

    /**
     * 字符串判断
     * 如果content 不为null或“” 则返回content 否则返回args[0]
     *
     * @param content 判断内容
     * @param args    结果
     * @return
     */
    public String convertString(String content, String... args) {
        return TextUtils.isEmpty(content) ? (args.length <= 0 ? "" : (TextUtils.isEmpty(args[0]) ? "" : args[0])) : content;
    }

    public static class ItemStatus extends Object {
        public int status;
        static final public int EMPTY = 0;
        static final public int FIRST = 1;
        static final public int END = 2;
    }

}
