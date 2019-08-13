package com.zbj.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zbj.annotation.BindPath;
import com.zbj.arouter2.ARouter;

@BindPath("login/login")
public class Login2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpActivity(View view) {
        ARouter.getInstance().jumpActivity("member/menber",null);
    }
}
