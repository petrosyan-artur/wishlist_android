package com.tlab.wish.new_wish;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;
import com.tlab.wish.utils.ExceptionTracker;
import com.tlab.wish.wishes.Decoration;
import com.tlab.wish.wishes.Wish;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 1/26/16.
 */
public class WishHelper {

    public static Subscription updateWish(final WishPresenter presenter, String id, String content, DecorItem decorItem){
        return WishesAPI.getInstanse().updateWish(getWish(id, content, getDecoration(decorItem)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WishResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.showUnknownError(false);
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(WishResponse wishResponse) {
                        presenter.onWishSent(wishResponse);
                    }
                });
    }

    public static Subscription sendNewWish(final WishPresenter presenter, String content){
        return WishesAPI.getInstanse().sendNewWish(getWish(content, getDecoration()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WishResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.showUnknownError(false);
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(WishResponse wishResponse) {
                        presenter.onWishSent(wishResponse);
                    }
                });
    }

    private static Wish getWish(String content, Decoration decoration){
        return getWish(null, content, decoration);
    }

    private static Wish getWish(String id, String content, Decoration decoration){
        Wish wish = new Wish();

        wish.setId(id);
        wish.setContent(content);
        wish.setDecoration(decoration);

        return wish;
    }

    private static Decoration getDecoration(){
        return getDecoration(LastSelectedDecor.getInstanse().getSelectedDecodeItem());
    }

    private static Decoration getDecoration(DecorItem di){
        Decoration decoration = new Decoration();

        if(di instanceof ColorDecorItem){
            decoration.setColor(((ColorDecorItem) di).getColorStr());
            decoration.setImage("");
        } else {
            //TODO here we will init the PictureDecoration
        }

        return decoration;
    }

    public static void limitTextSymbols(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxChars = ConfigurationManager.getInstanse().getConfigs().getMaxSymbols();

                if(s.length() > maxChars){
                    Toast.makeText(App.getInstance(), R.string.no_more_characters_allowed, Toast.LENGTH_SHORT).show();

                    editText.setText(s.toString().substring(0, maxChars));
                    editText.setSelection(editText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
