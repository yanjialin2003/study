package com.readboy.mycustomview.jetpack.view.model

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyObserver : LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun lifecycleOnCreate(){
        Log.e(TAG,"lifecycleOnCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun lifecycleOnStart(){
        Log.e(TAG,"lifecycleOnStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun lifecycleOnResume(){
        Log.e(TAG,"lifecycleOnResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun lifecycleOnPause(){
        Log.e(TAG,"lifecycleOnPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun lifecycleOnStop(){
        Log.e(TAG,"lifecycleOnStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifecycleOnDestroy(){
        Log.e(TAG,"lifecycleOnADestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun lifecycleOnAny(){
        Log.e(TAG,"lifecycleOnAny")
    }

    companion object{
        private const val TAG = "MyObserver"
    }
}