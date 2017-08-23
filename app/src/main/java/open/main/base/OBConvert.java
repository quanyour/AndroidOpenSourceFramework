package open.main.base;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import open.main.bean.GitBean;
import open.main.utils.GsonUtil;

/**
 * Created by Cain on 2017/8/23.
 *
 * 自定义OkGo数据转换器
 */

public class OBConvert<T> extends AbsCallback<T> {
    Class<T> cls;

    public OBConvert(Class<T> cls){
        this.cls=cls;
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        T bean= GsonUtil.parseJsonWithGson(response.body().string(),cls);
        return bean;
    }
}
