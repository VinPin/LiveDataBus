package com.vinpin.livedatabus.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.vinpin.livedatabus.LiveDataBus
import com.vinpin.livedatabus.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LiveDataBus.get<String>("test1")?.observe(this) {
            viewBinding.txtContent.text = it
        }

        viewBinding.txtStick.setOnClickListener {
            LiveDataBus.get<String>("test2")?.postValue("我是一条粘性事件")
        }

        viewBinding.txtTest.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}