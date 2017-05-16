package com.yu.pullrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    RefreshFrameLayout ptrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ptrFrameLayout = (RefreshFrameLayout) findViewById(R.id.refresh_frame_layout);
        ptrFrameLayout.setOnRefreshHandler(new OnRefreshHandler() {
            @Override
            public void refreshStart() {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },2000);
            }
        });
    }
}
