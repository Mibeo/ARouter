package com.zbj.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zbj.annotation.BindPath;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}
