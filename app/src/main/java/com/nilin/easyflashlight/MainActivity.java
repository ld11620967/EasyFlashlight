package com.nilin.easyflashlight;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nilin.com.easyflashlight.R;

public class MainActivity extends Activity {
    public ImageButton  ibn;
    boolean isopent = false;
    Camera camera = null;
    public long mExitTime;
    private Toast mytoast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibn=(ImageButton) super.findViewById(R.id.ibn);
        ibn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isopent) {
                    camera = Camera.open();
                    Parameters params = camera.getParameters();
                    params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(params);
                    DisplayToast("打开了手电筒");
                    camera.startPreview(); // 开始亮灯
                    isopent = true;
                } else {
                    DisplayToast("关闭了手电筒");
                    camera.stopPreview(); // 关掉亮灯
                    camera.release();
                    isopent = false;
                }
            }
        } );
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                DisplayToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                mytoast.cancel();
                if (!isopent) {
                    finish();
                    isopent = true;
                } else {
                    camera.release();
                    finish();
                    isopent = false;
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void DisplayToast(String str)
    {
        if (mytoast == null)
        {
            mytoast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        }
        else
        {
            mytoast.setText(str);
        }
        mytoast.show();
    }
}