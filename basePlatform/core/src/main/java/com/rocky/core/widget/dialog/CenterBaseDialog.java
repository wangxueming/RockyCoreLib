package com.rocky.core.widget.dialog;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.rocky.core.R;

import static android.content.Context.WINDOW_SERVICE;

/**
 * 在屏幕中心弹出的对话框基类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public abstract class CenterBaseDialog extends BaseCustomDialog {

    protected Context mContext;

    public CenterBaseDialog(Context context) {
        this(context, R.style.base_Win_Dialog_CustomStyle);
        mContext = context;
        initView();
    }

    public CenterBaseDialog(Context context, int style) {
        super(context, style);
    }

    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display display = ((WindowManager) mContext.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        lp.width = (int) (width * (0.75));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

}
