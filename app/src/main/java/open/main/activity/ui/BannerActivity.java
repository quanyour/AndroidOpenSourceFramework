package open.main.activity.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.config.UrlConfig;

/**
 * 轮播图
 * GitHub地址：https://github.com/youth5201314/banner
 */
public class BannerActivity extends BaseActivity {

    @InjectView(R.id.banner1)
    Banner banner1;
    @InjectView(R.id.banner2)
    Banner banner2;
    @InjectView(R.id.banner3)
    Banner banner3;
    @InjectView(R.id.banner4)
    Banner banner4;
    @InjectView(R.id.banner5)
    Banner banner5;
    @InjectView(R.id.banner6)
    Banner banner6;

    List<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.inject(this);

        initData();
        initBanner1();
        initBanner2();
        initBanner3();
        initBanner4();
        initBanner5();
        initBanner6();
    }

    public void initBanner1(){
        //设置banner样式
        banner1.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner1.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner1.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner1.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner1.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner1.isAutoPlay(true);
        //设置轮播时间
        banner1.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner1.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner1.start();
    }

    public void initBanner2(){
        //设置banner样式
        banner2.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner2.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner2.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner2.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        banner2.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner2.isAutoPlay(true);
        //设置轮播时间
        banner2.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner2.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner2.start();
    }

    public void initBanner3(){
        //设置banner样式
        banner3.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner3.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner3.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner3.setBannerAnimation(Transformer.BackgroundToForeground);
        //设置标题集合（当banner样式有显示title时）
        banner3.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner3.isAutoPlay(true);
        //设置轮播时间
        banner3.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner3.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner3.start();
    }

    public void initBanner4(){
        //设置banner样式
        banner4.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner4.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner4.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner4.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置标题集合（当banner样式有显示title时）
        banner4.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner4.isAutoPlay(true);
        //设置轮播时间
        banner4.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner4.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner4.start();
    }

    public void initBanner5(){
        //设置banner样式
        banner5.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner5.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner5.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner5.setBannerAnimation(Transformer.CubeIn);
        //设置标题集合（当banner样式有显示title时）
        banner5.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner5.isAutoPlay(true);
        //设置轮播时间
        banner5.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner5.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner5.start();
    }

    public void initBanner6(){
        //设置banner样式
        banner6.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner6.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner6.setImages(UrlConfig.getImageList());
        //设置banner动画效果
        banner6.setBannerAnimation(Transformer.CubeOut);
        //设置标题集合（当banner样式有显示title时）
        banner6.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner6.isAutoPlay(true);
        //设置轮播时间
        banner6.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner6.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner6.start();
    }


    public void initData() {
        titles.add("标题1");
        titles.add("标题2");
        titles.add("标题3");
        titles.add("标题4");
        titles.add("标题5");
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

//            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }
}
