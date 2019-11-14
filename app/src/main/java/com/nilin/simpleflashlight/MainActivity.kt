package com.nilin.simpleflashlight

import android.app.Activity
import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    internal var isopen = 1
    var camera: Camera? = null
    private var manager: CameraManager? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        @RequiresApi(api = Build.VERSION_CODES.M)
        if (isM) {
            try {
                manager!!.setTorchMode("0", true)    // 开始亮灯
                flashlight_ibn.setBackgroundResource(R.drawable.button_on)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } else {
            camera = Camera.open()
            val params = camera!!.getParameters()
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
            camera!!.setParameters(params)
            camera!!.startPreview()  // 开始亮灯
            }
            flashlight_ibn.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onClick(v: View) {
                if (isM) {
                    if (isopen == 0) {
                        try {
                            manager!!.setTorchMode("0", true)    // 开始亮灯
                            flashlight_ibn.setBackgroundResource(R.drawable.button_on)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }

                        isopen = 1
                    } else {
                        try {
                            manager!!.setTorchMode("0", false)   // 关掉亮灯
                            flashlight_ibn.setBackgroundResource(R.drawable.button_off)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }

                        isopen = 0
                    }
                } else {
                    if (isopen == 0) {
                        camera = Camera.open()
                        val params = camera!!.getParameters()
                        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                        camera!!.setParameters(params)
                        camera!!.startPreview()  // 开始亮灯
                        flashlight_ibn.setBackgroundResource(R.drawable.button_on)
                        isopen = 1
                    } else {
                        camera!!.stopPreview()   // 关掉亮灯
                        flashlight_ibn.setBackgroundResource(R.drawable.button_off)
                        camera!!.release()
                        isopen = 0
                    }
                }
            }
        })
    }

    private val isM: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return true
            } else {
                return false
            }
        }
}