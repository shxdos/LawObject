package com.shx.law.libs.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.shx.law.R;
import com.shx.law.base.LayoutValue;


/**
 * 描述:对话框管理类， 提供普通消息提示框（一个与两个按钮） 可自定义对话框布局
 */
public class DialogManager {

    /**
     * 对话框实体
     */
    private CustomDialog mCustomDialog;
    /**
     * 通信框框实体
     */
    private CustomDialog mProgressDialog;

    private DialogManager() {
    }

    private static DialogManager mdialogDialogManager = null;

    public static DialogManager getInstance() {
        if (mdialogDialogManager == null) {
            mdialogDialogManager = new DialogManager();
        }
        return mdialogDialogManager;
    }


    /**
     * 描述:隐藏对话框
     */
    public void dissMissCustomDialog() {
        try {
            if (mCustomDialog != null & mCustomDialog.isShowing()) {
                mCustomDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    /**
     * 描述:展示自定义的对话框
     *
     * @param context     上下文对象
     * @param contentView 对话框视图
     */
    public void showCustomDialog(Context context, View contentView) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        mCustomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }

    /**
     * 描述:展示自定义的对话框
     *
     * @param context     上下文对象
     * @param contentView 对话框视图
     */
    public void showCustomDialog(Context context, View contentView, boolean cancelable) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        mCustomDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomDialog.setCancelable(cancelable);
        mCustomDialog.setContentView(contentView);
        showDialog();

    }




    /**
     * 描述: 定义弹出框的宽高，弹出对话框
     */
    public void showDialog() {
        try {
            if (mCustomDialog != null && mCustomDialog.isShowing()) {
                mCustomDialog.dismiss();
            }
            WindowManager windowManager = mCustomDialog.getWindow().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mCustomDialog.getWindow()
                    .getAttributes();
            lp.width = display.getWidth(); //设置宽度
            lp.height = display.getHeight();
            mCustomDialog.getWindow().setAttributes(lp);
            lp.gravity = Gravity.CENTER;
            mCustomDialog.getWindow().setAttributes(lp);
            mCustomDialog.show();
            mCustomDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (Exception e) {
            //弹出对话框时如果activity已经finish会报异常
            // java.lang.IllegalArgumentException: View=com.android.internal.policy.impl.PhoneWindow$DecorView{4294b1f8 V.E..... R.....I. 0,0-0,0} not attached to window manager
            e.printStackTrace();
        }
    }


    /**
     * 描述:显示通信框
     *
     * @param context 上下文
     */
    public void showProgressDialog(Context context) {
        mProgressDialog = createProgressDialog(context);
        mProgressDialog.setCancelable(true);
    }


    /**
     * 描述:显示通信框不可被取消
     *
     * @param context 上下文
     */
    public void showProgressDialogNotCancelbale(Context context) {
        mProgressDialog = createProgressDialog(context);
        mProgressDialog.setCancelable(false);
    }

    /**
     * 描述:隐藏通信框
     */
    public void dissMissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通讯提示框
     */
    public CustomDialog createProgressDialog(final Context con) {
        CustomDialog dlg = new CustomDialog(con, R.style.Theme_Dialog);
        dlg.show();
        dlg.setCancelable(true);
//        Window window = dlg.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.dimAmount = 0f;  //设置背景不变暗
//        window.setAttributes(params);
        LayoutInflater factory = LayoutInflater.from(con);
        // 加载progress_dialog为对话框的布局xml
        View view = factory.inflate(R.layout.progress_dialog, null);
        dlg.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
            }
        });
        dlg.getWindow().setContentView(view);
        WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        lp.width = LayoutValue.SCREEN_WIDTH * 2 / 4;
        lp.height = LayoutValue.SCREEN_WIDTH / 3;
        dlg.getWindow().setAttributes(lp);
        return dlg;
    }


    public CustomDialog getmCustomDialog() {
        return mCustomDialog;
    }


    public void setmCustomDialog(CustomDialog mCustomDialog) {
        this.mCustomDialog = mCustomDialog;
    }
}
