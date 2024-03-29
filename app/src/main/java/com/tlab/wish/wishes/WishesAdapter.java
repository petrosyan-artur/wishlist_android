package com.tlab.wish.wishes;

import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.new_wish.decorations.DecorationUtils;
import com.tlab.wish.utils.ExceptionTracker;
import com.tlab.wish.utils.MyRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andranik on 2/2/16.
 */
public class WishesAdapter extends HeaderRecyclerViewAdapter<
        RecyclerView.ViewHolder,
        WishesAdapter.Header,
        Wish,
        WishesAdapter.Footer>{

    private List<Wish> data;
    private WishItemClickListener listener;
    private MyRecyclerOnScrollListener scrollListener;

    public WishesAdapter(WishItemClickListener listener, MyRecyclerOnScrollListener scrollListener) {
        this.data = new ArrayList<>();
        this.listener = listener;
        this.scrollListener = scrollListener;
    }

    public void addData(List<Wish> newData, boolean fromBegining) {
        if(fromBegining){
            data.clear();
            data.addAll(newData);
            return;
        }

        data.addAll(newData);
    }

    public void updateWish(Wish wish){
        if(data.contains(wish)) {
            data.set(data.indexOf(wish), wish);
            notifyDataSetChanged();
        }
    }

    public void addAWish(Wish wish){
        if(!data.contains(wish)) {
            data.add(wish);
            scrollListener.onItemAdded();
            Collections.sort(data);
            notifyDataSetChanged();
        }
    }

    public void removeWish(Wish wish){
        if(data.remove(wish)){
            scrollListener.onItemDeleted();
            notifyDataSetChanged();
        }
    }

    public List<Wish> getData() {
        return data;
    }

    public void resetData(){
        data.clear();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_footer_loader, parent, false);
        return new FooterViewHolder(v);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Wish wish = data.get(position);

        ItemViewHolder vh = (ItemViewHolder) holder;
        vh.render(wish, listener);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.wish_card_view) CardView cardView;
        @Bind(R.id.wish_user_name) TextView username;
        @Bind(R.id.wish_date) TextView date;
        @Bind(R.id.wish_text) TextView text;
        @Bind(R.id.wish_likes) TextView likes;
        @Bind(R.id.wish_like_btn) ImageView likeBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            final Typeface ROBOTO_REGULAR = App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_REGULAR);

            username.setTypeface(ROBOTO_REGULAR);
            date.setTypeface(ROBOTO_REGULAR);
            text.setTypeface(ROBOTO_REGULAR);
            likes.setTypeface(ROBOTO_REGULAR);
        }

        public void render(final Wish wish, final WishItemClickListener listener){
            try {
                username.setText(wish.getUsername());
                username.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onWishUserNameClicked(wish);
                    }
                });

                date.setText(wish.getFormatedDate());
                text.setText(wish.getContent());
                likes.setText(String.valueOf(wish.getLikes()));
                likeBtn.setSelected(wish.isLiked());
                likeBtn.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onWishLikeClicked(wish);
                    }
                });

                cardView.setCardBackgroundColor(DecorationUtils.getColor(wish.getDecoration().getColor()));
                cardView.setOnClickListener(v -> {
                    if (listener != null){
                        listener.onWishItemClicked(wish);
                    }
                });
                cardView.setOnLongClickListener(v -> {
                    if(listener != null){
                        listener.onWishItemLongClicked(wish);
                    }
                    return true;
                });
            } catch (Exception e){
                ExceptionTracker.trackException(e);
            }
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class Header{
    }

    public static class Footer{
    }

    public interface WishItemClickListener{
        void onWishItemClicked(Wish wish);
        void onWishLikeClicked(Wish wish);
        void onWishUserNameClicked(Wish wish);
        void onWishItemLongClicked(Wish wish);
    }
}
