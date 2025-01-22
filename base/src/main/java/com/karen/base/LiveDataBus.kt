package com.karen.base

import androidx.lifecycle.MutableLiveData

class LiveDataBus private constructor() {
    private val bus: MutableMap<String, MutableLiveData<Any>>

    private object SingletonHolder {
        val DATA_BUS = LiveDataBus()
    }

    fun <T> getChannel(target: String, type: Class<T>?): MutableLiveData<T> {
        if (!bus.containsKey(target)) {
            bus[target] = MutableLiveData()
        }
        return bus[target] as MutableLiveData<T>
    }

    fun <T>  getChannel(target: String): MutableLiveData<Any> {
        return getChannel(target, Any::class.java)
    }

    companion object {
        fun get(): LiveDataBus {
            return SingletonHolder.DATA_BUS
        }
    }

    init {
        bus = HashMap()
    }
}