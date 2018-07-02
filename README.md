# AndroidOpenSourceFramework
android快速开发框架，集成Zxing，数据库，网络请求，常用工具类，常用UI框架等，让我们快速完成开发任务<br>
做这个框架的目的是为了总结常用知识点，便于以后开发，节省时间。

# 更新日志
2017-6-5-01.29，添加Zxing，扫码（条形码，二维码），生成二维码<br>
2017-6-5-12.03，添加OKhttp3请求，Gson解析<br>
2017-6-5-19.43，封装BaseActivity，添加activity侧滑删除，沉浸式状态栏，添加短视频录制功能，添加请求进度条<br>
2017-6-5-21.47，添加时间选择控件，添加WheelView<br>
2017-6-6-16.51，添加List分割工具类，集成greenDao数据库<br>
2017-6-15-23.06，添加仿ios时间选择器<br>
2017-6-19-00.21，照片选择(拍照，相册(适配4.4)),图片压缩，Glide<br>
2017-6-30-18.30，RecycleView的BaseAdapter，BaseViewHolder添加，图表MPAndroidChart常见图表集成<br>
2017-7-1-10.47，添加图片轮播<br>
2017-7-3-23.16，添加SwipeRefreshView控件(只支持ListView),集成SwipeToLoadLayout(支持所有View的刷新)<br>
2017-7-4-18.15，Prism(未使用)、Colorful框架日夜间切换<br>
2017-7-1-10.47，XWalk简单集成使用(后续还会继续挖掘其使用方法)<br>
2017-7-7-18.35，添加适配7.0拍照的工具类CameraUtil,添加ToastUtil工具类,添加bugly异常上报，更换视频拍摄第三方库<br>
2017-8-22-15.37，android-openCV集成与使用<br>
2017-8-23-17.10，OkGo网络请求框架集成与使用，自定义Gson转换器<br>

# 未完成
EventBus<br>
百度地图<br>
动画<br>
Rx+OKhttp3+Retrofit封装<br>
bottomsheet<br>
MD相关效果实现<br>
android项目的重新设计(剥离非业务代码到lib，让所有功能代码直接引用即可)<br>
Vitamin视频框架集成<br>
视频加水印<br>

# 项目中遇到的问题及解决
1.之前视频的第三方库报出了java.lang.UnsatisfiedLinkE异常，判断应该是.so或者.so文件使用上的问题，
从github上将.so文件拉下来重新配置gradle.build问题解决。<br>
2.XWALK提示CPU架构不兼容提示框，最后检查是其中一个.so文件没有数据，重新下载官网的.so文件替换即可.<br>
3.Execution failed for task ':app:transformClassesWithDexForDebug'.<br>
  > com.android.build.api.transform.TransformException: com.android.ide.common.process.ProcessException: <br>
  java.util.concurrent.ExecutionException: com.android.dex.DexIndexOverflowException:<br>
  Cannot merge new index 66617 into a non-jumbo instruction!<br>
  解决：http://blog.csdn.net/huningjun/article/details/52821829




