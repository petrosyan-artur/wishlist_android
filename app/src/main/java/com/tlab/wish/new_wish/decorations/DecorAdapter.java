package com.tlab.wish.new_wish.decorations;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tlab.wish.R;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by andranik on 1/26/16.
 */
public class DecorAdapter extends RecyclerView.Adapter<DecorAdapter.DecorViewHolder>{

    private List<DecorItem> items;

    private OnDecorItemClickListener listener;


    public DecorAdapter(List<DecorItem> items, OnDecorItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public DecorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_wish_decor_item, parent, false);
        return new DecorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DecorViewHolder holder, int position) {
        final DecorItem item = items.get(position);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSelectedItem();
                item.setSelected(true);
                notifyDataSetChanged();
                if(listener != null) listener.onDecorItemClick(item);
            }
        });

        if(item.isSelected()){
            holder.layout.setBackgroundColor(holder.selectedColor);
        } else {
            holder.layout.setBackgroundColor(Color.TRANSPARENT);
        }

        item.initDecorViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void resetSelectedItem(){
        for(DecorItem item : items){
            item.setSelected(false);
        }
    }


    public static class DecorViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.new_wish_decor_item_layout)
        View layout;

        @Bind(R.id.new_wish_decor_item_iv)
        ImageView imageView;

        @BindColor(R.color.colorAccent)
        int selectedColor;

        public DecorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnDecorItemClickListener{
        void onDecorItemClick(DecorItem item);
    }
}
