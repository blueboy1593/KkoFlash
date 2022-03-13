package com.kkobook.flashkko

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private var powerState: Boolean = false
    private var powerCount: Int = 0

    private lateinit var textSecond: TextView
    private lateinit var flashButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecond = findViewById(R.id.textSecond)
        flashButton = findViewById(R.id.flashImage)
        val buttonFive: Button = findViewById(R.id.buttonFive)
        val buttonFifteen: Button = findViewById(R.id.buttonFifteen)
        val buttonSixty: Button = findViewById(R.id.buttonSixty)

        flashButton.setOnClickListener {
            if (powerState) {
                turnOffFlash()
            } else {
                turnOnFlash()
            }
        }

        buttonFive.setOnClickListener { appendFiveSeconds() }
        buttonFifteen.setOnClickListener { appendFifteenSeconds() }
        buttonSixty.setOnClickListener { appendSixtySeconds() }
    }

    private fun startCoroutine() {
        coroutineScope.launch {
            while (powerCount > 0) {
                delay(1000)
                powerCount -= 1
                textSecond.text = powerCount.toString()
            }
            turnOffFlash()
        }
    }

    private fun appendFiveSeconds() {
        if (powerCount > 0) {
            powerCount += 5
            textSecond.text = powerCount.toString()
        } else {
            turnOnFlash()
            powerCount = 5
            textSecond.text = powerCount.toString()
            startCoroutine()
        }
    }

    private fun appendFifteenSeconds() {
        if (powerCount > 0) {
            powerCount += 15
            textSecond.text = powerCount.toString()
        } else {
            turnOnFlash()
            powerCount = 15
            textSecond.text = powerCount.toString()
            startCoroutine()
        }
    }

    private fun appendSixtySeconds() {
        if (powerCount > 0) {
            powerCount += 60
            textSecond.text = powerCount.toString()
        } else {
            turnOnFlash()
            powerCount = 60
            textSecond.text = powerCount.toString()
            startCoroutine()
        }
    }

    private fun turnOnFlash() {
        powerState = true
        flashButton.setImageResource(R.drawable.flash_on_removebg_preview)
        controlFlash(powerState)
    }

    private fun turnOffFlash() {
        powerState = false
        flashButton.setImageResource(R.drawable.flash_off_removebg_preview)
        controlFlash(powerState)
        powerCount = 0
        textSecond.text = powerCount.toString()
    }

    private fun controlFlash(powerState: Boolean) {
        val cameraM = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraListId = cameraM.cameraIdList[0]

        try {
            cameraM.setTorchMode(cameraListId, powerState)
        } catch (e: Exception) {
            val toast = Toast.makeText(this, "Camera Flash Error", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}