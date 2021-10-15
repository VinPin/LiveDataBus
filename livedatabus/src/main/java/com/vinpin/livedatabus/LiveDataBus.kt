package com.vinpin.livedatabus

/**
 * author : VinPin
 * e-mail : hearzwp@163.com
 * time   : 2021/10/14 20:40
 * desc   : 基于LiveData实现的事件总线EventBus。
 */
object LiveDataBus {

    private val mBus: MutableMap<String, EventLiveData<*>> = mutableMapOf()

    /**
     * 根据[key]获取指定的[EventLiveData]，如果不存在则会自动创建。
     */
    fun <T> get(key: String): EventLiveData<T>? {
        synchronized(mBus) {
            if (!mBus.containsKey(key)) {
                mBus[key] = EventLiveData<T>()
            }
            return mBus[key] as? EventLiveData<T>
        }
    }

    /**
     * 获取当前事件的总数量
     */
    fun count(): Int {
        synchronized(mBus) {
            return mBus.size
        }
    }

    /**
     * 清除所有的事件
     */
    fun clear() {
        synchronized(mBus) {
            mBus.clear()
        }
    }

    /**
     * 清除[key]的事件
     */
    fun clear(key: String) {
        synchronized(mBus) {
            mBus.remove(key)
        }
    }
}