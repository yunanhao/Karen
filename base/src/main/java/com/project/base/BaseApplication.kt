package com.project.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

class BaseApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: BaseApplication
        @SuppressLint("StaticFieldLeak")
        var currentActivity: java.lang.ref.WeakReference<Activity>? = null

    }
    
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        //注册生命周期
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = java.lang.ref.WeakReference(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                
            }

            override fun onActivityStopped(activity: Activity) {
                
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (currentActivity?.get() === activity) currentActivity = null
            }

        })

    }



}