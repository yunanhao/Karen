package com.karen.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

open class BaseActivity : FragmentActivity(), View.OnClickListener {
    override fun setContentView(@LayoutRes layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null))
    }

    override fun setContentView(view: View) {
        setContentView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    @CallSuper
    override fun onUserInteraction() {

    }

    @CallSuper
    override fun onUserLeaveHint() {

    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("${componentName.className} ${hashCode()}","onCreate")
        super.onCreate(savedInstanceState)
    }
    
    @CallSuper
    override fun onRestart() {
        Log.v("${componentName.className} ${hashCode()}","onRestart")
        super.onRestart()
    }
    
    @CallSuper
    override fun onStart() {
        Log.v("${componentName.className} ${hashCode()}","onStart")
        super.onStart()
    }

    @CallSuper
    override fun onResume() {
        Log.v("${componentName.className} ${hashCode()}","onResume")
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        Log.v("${componentName.className} ${hashCode()}","onPause")
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        Log.v("${componentName.className} ${hashCode()}","onStop")
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        Log.v("${componentName.className} ${hashCode()}","onDestroy")
        super.onDestroy()
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    
    override fun onClick(v: View) {
    }

}