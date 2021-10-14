package com.vinpin.livedatabus.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinpin.livedatabus.LiveDataBus
import com.vinpin.livedatabus.sample.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private val viewBinding: ActivityTestBinding = ActivityTestBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        LiveDataBus.get<String>("test2")?.observe(this) {
            viewBinding.txtContent.text = it
        }

        LiveDataBus.get<String>("test1")?.observe(this) {
            viewBinding.txtContent.text = it
        }

        viewBinding.txtPost.setOnClickListener {
            LiveDataBus.get<String>("test1")?.postValue("我是一条事件")
        }
    }
}