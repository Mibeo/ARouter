package com.zbj.login;

import com.zbj.arouter2.ARouter;
import com.zbj.arouter2.IRouter;

public class ActivityUtil implements IRouter {
    @Override
    public void putActivity() {
        ARouter.getInstance().putActivity("login/login", LoginActivity.class);
        ARouter.getInstance().putActivity("login/login", LoginActivity.class);
        ARouter.getInstance().putActivity("login/login", LoginActivity.class);
        ARouter.getInstance().putActivity("login/login", LoginActivity.class);
        ARouter.getInstance().putActivity("login/login", LoginActivity.class);
    }
}
