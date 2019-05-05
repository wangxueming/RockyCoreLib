package com.rocky.customview.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rocky.customview.R;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/25
 */
public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayoutBg;
    private LinearLayout llContentView;
    private TextView txtTitle;
    private TextView txtMsg;
    private Button btnNeg;
    private Button btnPos;
    private ImageView imgLine;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private ScrollView mScrollView;
    private boolean showScroll = false;
    private TextView mTxtMsg2;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.cv_dialog_view_item, null);

        // 获取自定义Dialog布局中的控件
        lLayoutBg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtTitle.setVisibility(View.GONE);
        txtMsg = (TextView) view.findViewById(R.id.txt_msg);
        txtMsg.setVisibility(View.GONE);
        btnNeg = (Button) view.findViewById(R.id.btn_neg);
        btnNeg.setVisibility(View.GONE);
        btnPos = (Button) view.findViewById(R.id.btn_pos);
        btnPos.setVisibility(View.GONE);
        imgLine = (ImageView) view.findViewById(R.id.img_line);
        imgLine.setVisibility(View.GONE);
        llContentView = (LinearLayout) view.findViewById(R.id.llyt_contentView);
        llContentView.setVisibility(View.GONE);

        mScrollView = (ScrollView) view.findViewById(R.id.scroll_view);
        mScrollView.setVisibility(View.GONE);
        mTxtMsg2 = (TextView) view.findViewById(R.id.txt_msg2);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // 调整dialog背景大小
        lLayoutBg.setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));


        return this;
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txtTitle.setText("标题");
        } else {
            txtTitle.setText(title);
        }
        return this;
    }

    public AlertDialog setMsg(CharSequence msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txtMsg.setText("内容");
        } else {
            txtMsg.setText(msg);
        }
        return this;
    }

    public AlertDialog setSVMsg(CharSequence msg) {
        showScroll = true;
        if ("".equals(msg)) {
            mTxtMsg2.setText("内容");
        } else {
            mTxtMsg2.setText(msg);
        }
        return this;
    }

    public AlertDialog setView(View v) {
        llContentView.addView(v);
        llContentView.setVisibility(View.VISIBLE);
        return this;
    }


    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btnNeg.setText("取消");
        } else {
            btnNeg.setText(text);
        }
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txtTitle.setText("提示");
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txtMsg.setVisibility(View.VISIBLE);
        }

        if (showScroll) {
            mScrollView.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btnPos.setText("确定");
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btnPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            imgLine.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
