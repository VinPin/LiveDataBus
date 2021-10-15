package com.vinpin.livedatabus.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinpin.livedatabus.LiveDataBus
import com.vinpin.livedatabus.sample.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        LiveDataBus.get<String>("test2")?.observeSticky(this) {
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