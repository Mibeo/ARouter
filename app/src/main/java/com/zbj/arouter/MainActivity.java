package com.zbj.arouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zbj.annotation.BindPath;
import com.zbj.arouter2.ARouter;
import com.zbj.arouter.R;

@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpActivity(View view) {
        ARouter.getInstance().jumpActivity("login/login", null);
    }
}
