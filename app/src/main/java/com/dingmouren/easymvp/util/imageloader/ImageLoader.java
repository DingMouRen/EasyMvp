package com.dingmouren.easymvp.util.imageloader;

import android.widget.ImageView;


/**
 */
public class ImageLoader {

    private static volatile IImageLoaderProvider mProvider;

    private static IImageLoaderProvider getProvider() {
        if (mProvider == null) {
            synchronized (ImageLoader.class) {
                if (mProvider == null) {
                    mProvider = new GlideImageLoaderProvider();
                }
            }
        }
        return mProvider;
    }

    public static void displayImage(ImageView iv, String url) {
        ImageRequest request = new ImageRequest.Builder()
                .url(url)
                .imgView(iv)
                .create();
        getProvider().loadImage(request);
    }

    public static void displayImage(ImageView iv, String url, int placeHolder) {
        ImageRequest request = new ImageRequest.Builder()
                .url(url)
                .imgView(iv)
                .placeHolder(placeHolder)
                .create();
        getProvider().loadImage(request);
    }

}
