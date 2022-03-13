package com.kkobook.flashkko

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var powerState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: ImageButton = findViewById(R.id.flashImage)
        button.setOnClickListener {
            if (powerState) {
                powerState = false
                button.setImageResource(R.drawable.flash_off_removebg_preview)
                controlFlash(powerState)
            } else {
                powerState = true
                button.setImageResource(R.drawable.flash_on_removebg_preview)
                controlFlash(powerState)
            }
        }

    }

    fun controlFlash(mode: Boolean) {
        var cameraM = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraListId = cameraM.cameraIdList[0]

        try {
            cameraM.setTorchMode(cameraListId, mode)
        } catch (e: Exception) {
            val toast = Toast.makeText(this, "Camera Flash Error", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}