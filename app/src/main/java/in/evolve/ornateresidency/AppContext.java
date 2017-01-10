package in.evolve.ornateresidency;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

/**
 * Created by Brekkishhh on 10-01-2017.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();

    public static AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
    }

    public static final ImageLoader imageLoader = ImageLoader.getInstance();


    public void initImageLoader(Context context){

        File cacheDir;
        ImageLoaderConfiguration config;
        DisplayImageOptions options;


        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"JunkFolder");}
        else{
            cacheDir=context.getCacheDir();}
        if(!cacheDir.exists()){
            cacheDir.mkdirs();}

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.abc_textfield_activated_mtrl_alpha)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .build();

        imageLoader.init(config);
    }

}
