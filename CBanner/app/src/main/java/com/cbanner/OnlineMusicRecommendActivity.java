package com.cbanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineMusicRecommendActivity extends Activity {


    String[] image1 = {"http://img0.imgtn.bdimg.com/it/u=2171358896,542442916&fm=21&gp=0.jpg",
            "http://www.zhiyuanit.com/images/cpbanner02.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2534955516,3055581639&fm=21&gp=0.jpg"
    };

    private static final int UI_ERROR_CODE = 0x111;
    private ListView listView;
    private ViewPager pager;
    private ListAdapter listViewAdapter;

    DisplayImageOptions options;
    // 圆点图片集合
    private ImageView[] imageCircleViews = null;
    private ImageView imageView = null;
    private int pageIndex;
    private int slideImageCount;
    private View slideImageView = null;

    List<OnlineRecommendSongListDomain> onlineRecommendSongListDomain;
    List<OnlineRecommendAdPicDomain> onlineRecommendAdPicDomainList;

    private int getSlideImageCount() {
        return onlineRecommendAdPicDomainList.size();
    }

    /**
     * 生成圆点图片区域布局对象
     *
     * @param index
     * @return
     */
    public ImageView getCircleImageLayout(int index) {
        imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(8, 8));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setPadding(10, 0, 10, 0);
        imageCircleViews[index % slideImageCount] = imageView;

        if (index == 0) {
            //默认选中第一张图片
            imageCircleViews[index % slideImageCount].setBackgroundResource(R.drawable.recommend_dian_up);
        } else {
            imageCircleViews[index % slideImageCount].setBackgroundResource(R.drawable.recommend_dian_down);
        }

        return imageCircleViews[index % slideImageCount];
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_music_recommend_activity);

        onlineRecommendSongListDomain = new ArrayList<>();
        onlineRecommendAdPicDomainList = new ArrayList<>();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        slideImageView = layoutInflater.inflate(R.layout.online_music_recommend_slide_image, null);
        pager = (ViewPager) slideImageView.findViewById(R.id.pager);

        listView = (ListView) findViewById(R.id.recommend_song_list_view);
        listView.addHeaderView(slideImageView);

        listViewAdapter = new ListAdapter(OnlineMusicRecommendActivity.this, onlineRecommendSongListDomain, R.layout.list_item);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.setvi(pager);

        getRecommendSongsListInfo();
    }

    private Timer timer = null;
    private Handler timerHandler = null;
    private TimerTask timerTask = null;
    private int slideImageItem = 0;
    private final int INTERVAL_TIME = 5000;
    private boolean userNotTouchPager = true;

    private void createAdTimer() {
        timer = new Timer();
        timerHandler = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pager.setCurrentItem(msg.what);
            }

        };
        timerTask = new TimerTask() {

            public void run() {
                if (userNotTouchPager) {
                    if (slideImageCount != 0) {
                        if (slideImageItem == slideImageCount)
                            slideImageItem = 0;
                        else
                            slideImageItem++;

                        timerHandler.sendEmptyMessage(slideImageItem);
                    }
                }
            }
        };
    }

    private void startTimer() {
        if (timer == null || timerTask == null || timerHandler == null)
            createAdTimer();
        timer.schedule(timerTask, 0, INTERVAL_TIME);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stopTimer();
    }

    Handler getGetOnlineRecommendSongListInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UI_ERROR_CODE) {
                onlineRecommendSongListDomain.addAll((List<OnlineRecommendSongListDomain>) msg.obj);
                listViewAdapter.notifyDataSetChanged();

                getOnlineRecommendAdPics();
            }
        }
    };

    private void getRecommendSongsListInfo() {
        List<OnlineRecommendSongListDomain> onlineRecommendAdPicDomainsList = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            OnlineRecommendSongListDomain domain = new OnlineRecommendSongListDomain();
            onlineRecommendAdPicDomainsList.add(domain);
        }

        Message message = Message.obtain();
        message.what = UI_ERROR_CODE;
        message.obj = onlineRecommendAdPicDomainsList;
        message.setTarget(getGetOnlineRecommendSongListInfoHandler);
        message.sendToTarget();
    }

    Handler getOnlineRecommendAdPicsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UI_ERROR_CODE) {
                List<OnlineRecommendAdPicDomain> onlineRecommendAdPicDomainsList = (List<OnlineRecommendAdPicDomain>) msg.obj;

                setAd(onlineRecommendAdPicDomainsList);
            }
        }
    };

    private void setAd(List<OnlineRecommendAdPicDomain> onlineRecommendAdPicDomainsList) {
        onlineRecommendAdPicDomainList = onlineRecommendAdPicDomainsList;
        slideImageCount = getSlideImageCount();
        int pagerPosition = slideImageCount * 10000;
//                options = new DisplayImageOptions.Builder()
//                        .resetViewBeforeLoading()
//                        .cacheOnDisc()
//                        .imageScaleType(ImageScaleType.EXACTLY)
//                        .bitmapConfig(Bitmap.Config.RGB_565)
//                        .displayer(new FadeInBitmapDisplayer(300))
//                        .build();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.recommend_dian_down) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.recommend_dian_down)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.recommend_dian_down)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .build();//构建完成

        String[] imageUrls = new String[slideImageCount];
        for (int i = 0; i < slideImageCount; i++) {
            imageUrls[i] = onlineRecommendAdPicDomainList.get(i).getImgUrl();
        }
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(imageUrls);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(imagePagerAdapter);
        pager.setAdapter(wrappedAdapter);
        pager.setCurrentItem(pagerPosition);
        pager.setOnPageChangeListener(new ImagePageChangeListener());
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        userNotTouchPager = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        userNotTouchPager = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        userNotTouchPager = false;
                        break;
                }
                return false;
            }
        });

        imageCircleViews = new ImageView[slideImageCount];
        LinearLayout circleLayout = (LinearLayout) slideImageView.findViewById(R.id.circleimages);
        circleLayout.removeAllViews();
        for (int i = 0; i < slideImageCount; i++) {
            LinearLayout linerLayout = new LinearLayout(OnlineMusicRecommendActivity.this);
            LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
                    8,
                    8,
                    1);
            // 这里最好也自定义设置，有兴趣的自己设置。
            linerLayout.setPadding(6, 0, 6, 0);
            linerLayout.addView(getCircleImageLayout(i), linerLayoutParames);
            circleLayout.addView(linerLayout);
        }

    }

    private void getOnlineRecommendAdPics() {
        List<OnlineRecommendAdPicDomain> onlineRecommendAdPicDomainsList = new ArrayList<>();

        for (int x = 0; x < image1.length; x++) {
            OnlineRecommendAdPicDomain domain = new OnlineRecommendAdPicDomain();
            domain.setImgUrl(image1[x]);
            domain.setPosition(x);
            domain.setResId(x);
            domain.setResLink("http://www.baidu.com");
            domain.setResName("nnnnnnnn");
            domain.setResType("");
            onlineRecommendAdPicDomainsList.add(domain);
        }

        Message message = Message.obtain();
        message.what = UI_ERROR_CODE;
        message.obj = onlineRecommendAdPicDomainsList;
        message.setTarget(getOnlineRecommendAdPicsHandler);
        message.sendToTarget();
    }


    private void imageClickResLink() {
        if (onlineRecommendAdPicDomainList != null && pageIndex < onlineRecommendAdPicDomainList.size()) {
            String resType = onlineRecommendAdPicDomainList.get(pageIndex).getResType();
            String resLink = onlineRecommendAdPicDomainList.get(pageIndex).getResLink();
            long resId = onlineRecommendAdPicDomainList.get(pageIndex).getResId();
            String resName = onlineRecommendAdPicDomainList.get(pageIndex).getResName();
        }
    }

    private class ImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            imageClickResLink();
        }
    }

    // 滑动页面更改事件监听器
    private class ImagePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            pageIndex = index % slideImageCount;
            slideImageItem = pageIndex;
            if (imageCircleViews != null) {
                for (int i = 0; i < imageCircleViews.length; i++) {
                    if (imageCircleViews[index % slideImageCount] != null) {
                        imageCircleViews[index % slideImageCount].setBackgroundResource(R.drawable.recommend_dian_up);
                    }
                    if (index % slideImageCount != i) {
                        if (imageCircleViews[i] != null) {
                            imageCircleViews[i].setBackgroundResource(R.drawable.recommend_dian_down);
                        }
                    }
                }
            }
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private String[] images;
        private LayoutInflater inflater;

        ImagePagerAdapter(String[] images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(View view, int position) {

            View imageLayout = inflater.inflate(R.layout.item_pager_image, null);
            imageLayout.setOnClickListener(new ImageOnClickListener());
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            ImageLoader.getInstance().displayImage(images[position], imageView, options);

            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }


    }

    static class ViewHolder {
        public ImageView img;
        public ProgressBar pro;
    }
}