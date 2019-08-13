package com.zbj.arouter2;

/**
 * Created by bingjia.zheng on 2019/8/13.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * 中间人
 */
public class ARouter {
    //装载所有Activity的类对象的容器
    private Map<String, Class<? extends Activity>> activityMap;
    private Context context;
    private static ARouter aRouter;

    private ARouter() {
        activityMap = new HashMap<>();
    }

    public static ARouter getInstance() {
        if (aRouter == null) {
            synchronized (ARouter.class) {
                if (aRouter == null) {
                    aRouter = new ARouter();
                }
            }
        }
        return aRouter;
    }

    public void init(Application application) {
        this.context = application;
        //去执行生成的文件的方法
        List<String> classNames = getClassName("com.netease.util.");
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                //判断这个类是否是IRouter这个接口的实现类
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getClassName(String packageName) {
        //创建一个class对象的集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //通过包管理器，获取到应用信息类然后获取到APK的完整路径
            path = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), 0).sourceDir;
            //根据APK的完整路径获取到编译后的dex文件
            DexFile dexFile = new DexFile(path);
            //获得编译后的dex文件中的所有class
            Enumeration entries = dexFile.entries();
            //然后进行遍历
            while (entries.hasMoreElements()) {
                //通过遍历所有的class的包名
                String name = (String) entries.nextElement();
                //判断类的包名是否符合
                if (name.contains(packageName)) {
                    //如果符合，就添加到集合中
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 将Activity的类对象加入到容器中
     *
     * @param path
     * @param clazz
     */
    public void putActivity(String path, Class<? extends Activity> clazz) {
        if (path != null && clazz != null) {
            activityMap.put(path, clazz);
        }
    }

    /**
     * 跳转
     *
     * @param path
     * @param bundle
     */
    public void jumpActivity(String path, Bundle bundle) {
        Class<? extends Activity> aClass = activityMap.get(path);
        if (aClass == null) {
            return;
        }
        Intent intent = new Intent(context, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
