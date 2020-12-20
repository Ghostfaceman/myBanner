package com.example.mybanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kelin.banner.BannerEntry;
import com.kelin.banner.transformer.GalleryTransformer;
import com.kelin.banner.view.BannerView;
import com.kelin.recycleradapter.ItemAdapter;
import com.kelin.recycleradapter.MultiTypeAdapter;
import com.kelin.recycleradapter.interfaces.EventBindInterceptor;
import com.kelin.recycleradapter.interfaces.LayoutItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EventBindInterceptor mEventInterceptor;
    private BannerView bannerView;
    private BannerView.OnPageClickListener mOnBannerEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerView = findViewById(R.id.vp_view_pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerView.setShowLeftAndRightPage(30, true, new GalleryTransformer());
        } else {
            bannerView.setShowLeftAndRightPage(20);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MultiTypeAdapter adapter = new MultiTypeAdapter(recyclerView);
        ItemAdapter<List<TitleImageBannerEntry>> banner1Adapter = new ItemAdapter<>(BannerHolder.class, getTitleImageBannerEntries());
        ItemAdapter<List<TitleImageBannerEntry>> banner2Adapter = new ItemAdapter<>(BannerHolder2.class, getTitleImageBannerEntries());
        ItemAdapter<List<TitleImageBannerEntry>> banner3Adapter = new ItemAdapter<>(BannerHolder3.class, getTitleImageBannerEntry());
        banner1Adapter.setEventInterceptor(getItemEventInterceptor());
        banner2Adapter.setEventInterceptor(getItemEventInterceptor());
        banner3Adapter.setEventInterceptor(getItemEventInterceptor());
        adapter.addAdapter(banner1Adapter, banner2Adapter, banner3Adapter, new ItemAdapter<>(getStringList(), ItemHolder.class));
        recyclerView.setAdapter(adapter);

    }
    public List<String> getStringList() {
        List<String> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add("我是条目" + i);
        }
        return list;
    }
    @NonNull
    private List<TitleImageBannerEntry> getTitleImageBannerEntry() {
        List<TitleImageBannerEntry> items = new ArrayList<>();
        items.add(new TitleImageBannerEntry("中国新歌声：E神赞藏语Rap", "更新至10集", R.drawable.img_banner01, "我是第一页"));
//        items.add(new TitleImageBannerEntry("中国有嘻哈:热狗公演霸气嗨唱", "更新至11集", R.drawable.img_banner02, "我是第二页"));
//        items.add(new TitleImageBannerEntry("爱笑会议室：三生三世虐恋情缘", "更新至12集", R.drawable.img_banner03, "我是第三页"));
//        items.add(new TitleImageBannerEntry("开心剧乐部：吴京上演战狼故事", "更新至13集", R.drawable.img_banner04, "我是第四页"));
        return items;
    }

    @NonNull
    private List<TitleImageBannerEntry> getTitleImageBannerEntries() {
        List<TitleImageBannerEntry> items = new ArrayList<>();
        items.add(new TitleImageBannerEntry("中国新歌声：E神赞藏语Rap", "更新至10集", R.drawable.img_banner01, "我是第一页"));
//        items.add(new TitleImageBannerEntry("中国有嘻哈:热狗公演霸气嗨唱", "更新至11集", R.drawable.img_banner02, "我是第二页"));
//        items.add(new TitleImageBannerEntry("爱笑会议室：三生三世虐恋情缘", "更新至12集", R.drawable.img_banner03, "我是第三页"));
//        items.add(new TitleImageBannerEntry("开心剧乐部：吴京上演战狼故事", "更新至13集", R.drawable.img_banner04, "我是第四页"));
        return items;
    }


    private EventBindInterceptor getItemEventInterceptor() {
        if (mEventInterceptor == null) {
            mEventInterceptor = new EventBindInterceptor() {
                @Override
                public boolean onInterceptor(View view, LayoutItem layoutItem) {
                    if (view.getId() == R.id.vp_view_pager) {
                        BannerView bannerView = (BannerView) view;
                        bannerView.setOnPageClickListener(new BannerView.OnPageClickListener() {
                            @Override
                            public void onPageClick(BannerEntry entry, int index) {
                                //某个页面被单击后执行，entry就是这个页面的数据模型。index是页面索引，从0开始。
                                Toast.makeText(MainActivity.this, "单击：" + entry.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        bannerView.setOnPageLongClickListener(new BannerView.OnPageLongClickListener() {
                            @Override
                            public void onPageLongClick(BannerEntry entry, int index) {
                                //某个页面被长按后执行，entry就是这个页面的数据模型。index是页面索引，从0开始。
                                Toast.makeText(MainActivity.this, "长按：" + entry.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        bannerView.setOnPageChangedListener(new BannerView.OnPageChangeListener() {
                            @Override
                            public void onPageSelected(BannerEntry entry, int index) {
                                //某个页面被选中后执行，entry就是这个页面的数据模型。index是页面索引，从0开始。
                            }

                            @Override
                            public void onPageScrolled(int index, float positionOffset, int positionOffsetPixels) {
                                //页面滑动中执行，这个与ViewPage的回调一致。
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                                //页面滑动的状态被改变时执行，也是与ViewPager的回调一致。
                            }
                        });
                        return true;
                    }
                    return false;
                }
            };
        }
        return mEventInterceptor;
    }

    @NonNull
    private BannerView.OnPageClickListener getBannerEventListener() {
        if (mOnBannerEventListener == null) {
            mOnBannerEventListener = new BannerView.OnPageClickListener() {
                @Override
                public void onPageClick(BannerEntry entry, int index) {
                    if (entry instanceof ImageBannerEntry) {
                        SubActivity.start(MainActivity.this, ((ImageBannerEntry) entry).getValue());
                    } else if (entry instanceof TitleImageBannerEntry) {
                        SubActivity.start(MainActivity.this, ((TitleImageBannerEntry) entry).getValue());
                    }
                }
            };
        }
        return mOnBannerEventListener;
    }


    @Override
    protected void onStart() {
        super.onStart();
        bannerView.setEntries(getImageBannerEntries());
        bannerView.selectCenterPage(2);
    }

    /**
     * 获取推荐页对象
     */
    private List<ImageBannerEntry> getImageBannerEntries(){
        List<ImageBannerEntry> items = new ArrayList<>();
        items.add(new ImageBannerEntry("高冷女神:“狂三”","001","http://io26547704.wicp.vip/myWeb/image1.png"));
        items.add(new ImageBannerEntry("这个妹子:“我爱了”","002","http://io26547704.wicp.vip/myWeb/image2.png"));
        items.add(new ImageBannerEntry("不搞hs:“真的”","003","http://io26547704.wicp.vip/myWeb/image3.png"));
        items.add(new ImageBannerEntry("我很单纯:“0.0”","004","http://io26547704.wicp.vip/myWeb/image4.png"));
        items.add(new ImageBannerEntry("牛逼:“666666”","005","http://io26547704.wicp.vip/myWeb/image5.png"));
        return items;
    }




}