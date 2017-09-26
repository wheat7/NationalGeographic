package com.wheat7.nationalgeographic.ui.activity

import android.arch.lifecycle.LifecycleActivity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

/**
 * Created by wheat7 on 2017/9/12.
 */

abstract class BaseActivity<T : ViewDataBinding> : LifecycleActivity() {

    private var mainView: View? = null
    private var binding: ViewDataBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutId = layoutId
        super.onCreate(savedInstanceState)
        //        SwipeBackHelper.onCreate(this);
        //        SwipeBackHelper.getCurrentPage(this)
        //                .setSwipeBackEnable(true)
        //                .setSwipeSensitivity(0.5f)
        //                .setSwipeRelateEnable(true)
        //                .setSwipeRelateOffset(300)
        //                .setSwipeEdgePercent(0.1f);
        try {
            binding = DataBindingUtil.setContentView(this, layoutId)
            if (binding != null) {
                mainView = binding!!.root
            } else {
                mainView = LayoutInflater.from(this).inflate(layoutId, null)
                setContentView(mainView)
            }

        } catch (e: NoClassDefFoundError) {
            mainView = LayoutInflater.from(this).inflate(layoutId, null)
            setContentView(mainView)
        }

        initView(savedInstanceState)
    }

    //    @Override
    //    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
    //        super.onPostCreate(savedInstanceState, persistentState);
    //        SwipeBackHelper.onPostCreate(this);
    //    }

    override fun onDestroy() {
        super.onDestroy()
        //        SwipeBackHelper.onDestroy(this);
    }

    fun getBinding(): T {
        return binding as T
    }

    abstract val layoutId: Int

    abstract fun initView(savedInstanceState: Bundle?)

}
