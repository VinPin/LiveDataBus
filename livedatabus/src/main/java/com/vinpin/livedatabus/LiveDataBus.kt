package com.vinpin.livedatabus

import java.util.concurrent.ConcurrentHashMap

/**
 * author : VinPin
 * e-mail : hearzwp@163.com
 * time   : 2020/8/21 21:29
 * desc   : 基于LiveData实现的事件总线EventBus。
 */
object LiveDataBus {

    private val mBus = ConcurrentHashMap<String, EventLiveData<*>>()

    /**
     * 根据[key]获取指定的[EventLiveData]，如果不存在则会自动创建。
     */
    fun <T> get(key: String): EventLiveData<T>? {
        mBus.putIfAbsent(key, EventLiveData<T>())
        return mBus[key] as? EventLiveData<T>
    }

    /**
     * 获取当前事件的总数量
     */
    fun count(): Int {
        return mBus.size
    }

    /**
     * 清除所有的事件
     */
    fun clear() {
        mBus.clear()
    }

    /**
     * 清除[key]的事件
     */
    fun clear(key: String) {
        mBus.remove(key)
    }
}