package com.cbanner;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class NNApplication extends Application {

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(50 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(200 * 1024 * 1024)//50M 1024 bit
                .diskCacheFileCount(10000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            initImageLoader(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
