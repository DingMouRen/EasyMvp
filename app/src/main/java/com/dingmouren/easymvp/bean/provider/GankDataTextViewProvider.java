package com.dingmouren.easymvp.bean.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.multitype.GankDataText;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/13.
 */
public class GankDataTextViewProvider
        extends ItemViewProvider<GankDataText, GankDataTextViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_data_text, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull GankDataText gankDataText) {
        holder.tv_title.setText(gankDataText.getDesc());
        holder.tv_time.setText(gankDataText.getPublishedAt());
        holder.tv_anthor.setText(gankDataText.getWho().toString());
        holder.cardView.setOnClickListener((view) -> WebDetailActivity.newInstance(holder.tv_time.getContext(),gankDataText.getUrl(),gankDataText.getDesc()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)  CardView cardView;
        @BindView(R.id.tv_title)  TextView tv_title;
        @BindView(R.id.tv_anthor) TextView tv_anthor;
        @BindView(R.id.tv_time) TextView tv_time;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}