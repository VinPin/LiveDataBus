package com.vinpin.livedatabus

/**
 * author : VinPin
 * e-mail : hearzwp@163.com
 * time   : 2021/10/14 20:40
 * desc   : 基于LiveData实现的事件总线EventBus。
 */
object LiveDataBus {

    private val mBus: MutableMap<String, EventLiveData<*>> = mutableMapOf()

    fun <T> get(key: String): EventLiveData<T>? {
        synchronized(mBus) {
            if (!mBus.containsKey(key)) {
                mBus[key] = EventLiveData<T>()
            }
            return mBus[key] as? EventLiveData<T>
        }
    }

    fun count(): Int {
        synchronized(mBus) {
            return mBus.size
        }
    }

    fun clear() {
        synchronized(mBus) {
            mBus.clear()
        }
    }

    fun clear(key: String) {
        synchronized(mBus) {
            mBus.remove(key)
        }
    }
}