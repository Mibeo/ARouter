package com.zbj.arouter;

import android.app.Application;

import com.zbj.arouter2.ARouter;

/**
 * Created by bingjia.zheng on 2019/8/13.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
