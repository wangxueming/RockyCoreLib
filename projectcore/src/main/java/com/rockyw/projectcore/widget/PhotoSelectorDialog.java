package com.rockyw.projectcore.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rockyw.core.widget.dialog.BaseCustomDialog;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择照片的dialog
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/15
 */
public class PhotoSelectorDialog extends BaseCustomDialog {

    @BindView(R2.id.core_tv_camera)
    TextView mJumpCameraTv;
    @BindView(R2.id.core_tv_album)
    TextView mJumpAlbumTv;

    public PhotoSelectorDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_dlg_photo_selector;
    }

    @Override
    public void initView(View view) {
    }

    @OnClick(R2.id.core_tv_cancel)
    public void clickCancel() {
        this.dismiss();
    }

    public interface ClickListener {
        /**
         * 状态点击
         * @param dialog
         */
        void onClick(Dialog dialog);
    }

    public static class Builder {

        private PhotoSelectorDialog mDialog;

        public Builder(Context context) {
            mDialog = new PhotoSelectorDialog(context);
        }

        public Builder setJumpCameraClickListener(final ClickListener clickListener) {
            mDialog.mJumpCameraTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Builder setJumpAlbumClickListener(final ClickListener clickListener) {
            mDialog.mJumpAlbumTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Dialog build() {
            return mDialog;
        }

    }
}
