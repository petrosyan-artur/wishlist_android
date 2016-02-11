package com.tlab.wish.main_view_staff.my_wishes;

import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseView;
import com.tlab.wish.wishes.Wish;

/**
 * Created by andranik on 2/4/16.
 */
public interface MyWishesView extends WishListBaseView{
    
    void openEditWish(Wish wish);

    void showEditError();

    void showDeleteWishDialog(Wish wish);
}
