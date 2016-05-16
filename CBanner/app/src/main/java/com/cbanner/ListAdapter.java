package com.cbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.cbanner.wnadapter.WNBaseAdapter;
import com.cbanner.wnadapter.WNViewHolder;

import java.util.List;

/**
 * Created by wuyajun on 15/6/24.
 * <p/>
 * 常见问题适配器
 */
public class ListAdapter extends WNBaseAdapter<OnlineRecommendSongListDomain> {

    ViewPager pager;

    public ListAdapter(Context context, List<OnlineRecommendSongListDomain> datas, int itemId) {
        super(context, datas, itemId);
    }

    @Override
    public void convertView(WNViewHolder holder, OnlineRecommendSongListDomain nnHelper, int position) {
        Button btn = holder.getView(R.id.button);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int po = (int) v.getTag();
//                pager.setCurrentItem(po % 3);
//                pager.setCurrentItem(po%3,false);
//                pager.populate(po%3);
            }
        });
    }

    public void setvi(ViewPager pager) {
        this.pager = pager;
    }
}
