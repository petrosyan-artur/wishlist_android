package com.tlab.wish.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tlab.wish.R;

/**
 * Created by andranik on 1/22/16.
 */
public class DialogUtils {

    public static void showOfflineDialog(Activity activity){
        showAlertDialog(activity, R.string.no_internet_connection);
    }

    public static AlertDialog showSomethingWentWrong(final Activity activity){
        return showSomethingWentWrong(activity, false);
    }

    public static AlertDialog showSomethingWentWrong(final Activity activity, final boolean finishActivity){
        return DialogUtils.showAlertDialog(activity,
                R.string.something_went_wrong,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finishActivity) {
                            activity.onBackPressed();
                        }
                    }
                });
    }

    public static AlertDialog showAlertDialog(Activity activity, int msgId){
        return showAlertDialog(activity, activity.getString(msgId));
    }

    public static AlertDialog showAlertDialog(Activity activity, String msg){
        return showAlertDialog(activity,
                null,
                msg,
                activity.getString(R.string.dialog_ok),
                null,
                null,
                null);
    }

    public static AlertDialog showAlertDialog(Activity activity, String msg, int posBtnTextId, int negBtnTextId, DialogInterface.OnClickListener posBtnClickListener){
        return showAlertDialog(activity,
                null,
                msg,
                activity.getString(posBtnTextId),
                activity.getString(negBtnTextId),
                posBtnClickListener,
                null);
    }

    public static AlertDialog showAlertDialog(Activity activity, int msgId, DialogInterface.OnClickListener posBtnClickListener){
        return showAlertDialog(activity,
                null,
                activity.getString(msgId),
                activity.getString(R.string.dialog_ok),
                null,
                posBtnClickListener,
                null);
    }

    public static AlertDialog showAlertDialog(Activity activity, String title, String message, String posBtnText, String negBtnText, DialogInterface.OnClickListener posBtnClickListener, DialogInterface.OnClickListener negBtnClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(message);
        builder.setCancelable(false);
        if(title != null){ builder.setTitle(title); }
        if (posBtnText != null){ builder.setPositiveButton(posBtnText, posBtnClickListener); }
        if (negBtnText != null){ builder.setNegativeButton(negBtnText, negBtnClickListener); }

        AlertDialog alert = builder.create();
        alert.show();

        return alert;
    }
}
