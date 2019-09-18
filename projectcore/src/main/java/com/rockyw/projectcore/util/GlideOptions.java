package com.rockyw.projectcore.util;

import android.graphics.Bitmap;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.rockyw.core.util.DimensionUtil;
import com.rockyw.projectcore.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/31
 */
public class GlideOptions {

    public static RequestOptions getImageOptions(int width, int height) {
        RequestOptions options = new RequestOptions();
        options.override(width, height);
        options.transform(GlideOptions.getRadiusTransformation(0));
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }


    public static RequestOptions getNoCacheImageOptions(int width, int height) {
        RequestOptions options = new RequestOptions();
        options.override(width, height);
        options.transform(GlideOptions.getRadiusTransformation(0));
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.skipMemoryCache(true);
        return options;
    }

    public static RequestOptions getCircleAvatarOptions(int width, int height) {
        RequestOptions options = new RequestOptions();
        options.override(width, height);
        options.transform(GlideOptions.getRadiusTransformation(5));
        options.placeholder(R.drawable.base_bg_gray_dfdfdf_bottom_corner_5);
        options.error(R.drawable.base_bg_gray_dfdfdf_bottom_corner_5);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }

    public static RequestOptions getRadiusOptions(int radius){
        RequestOptions options = new RequestOptions();
        options.transform(GlideOptions.getRadiusTransformation(radius));
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }

    /**
     * 获取指定 radius 的圆角 Transformation
     */
    public static Transformation<Bitmap> getRadiusTransformation(int radius) {
        return new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation((int) DimensionUtil.dp(radius), 0, RoundedCornersTransformation.CornerType.ALL));
    }

}
