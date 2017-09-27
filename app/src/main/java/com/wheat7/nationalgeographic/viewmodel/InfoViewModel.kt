package com.wheat7.nationalgeographic.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.wheat7.nationalgeographic.data.Resource
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.utlis.GlideCacheUtil

/**
 * Created by wheat7 on 2017/9/27.
 */

class InfoViewModel : ViewModel() {

    var mImageCache : MutableLiveData<String> = MutableLiveData<String>()

    fun getImageCache(context: Context) : MutableLiveData<String> {

        mImageCache.value = GlideCacheUtil.getInstance().getCacheSize(context)
        return mImageCache
    }
}