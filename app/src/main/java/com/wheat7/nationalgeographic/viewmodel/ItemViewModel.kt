package com.wheat7.nationalgeographic.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.wheat7.nationalgeographic.data.Resource
import com.wheat7.nationalgeographic.data.WebRepository
import com.wheat7.nationalgeographic.data.model.Item

/**
 * Created by wheat7 on 2017/9/13.
 */

class ItemViewModel() : ViewModel() {

    private var mWebRepository : WebRepository = WebRepository()

//    private var mData : MutableLiveData<Resource<Item?>>? = null
//    private var mNewData : MutableLiveData<Resource<Item?>>? = null

    constructor(repository : WebRepository) : this() {
        mWebRepository = repository
    }

    fun getItem(index : Int) : MutableLiveData<Resource<Item?>>? {
//        mData = mWebRepository?.getItem(index)
//        return mData
        return mWebRepository?.getItem(index)
    }

//    fun getMoreItem(index: Int) : MutableLiveData<Resource<Item?>>? {
//        mNewData = mWebRepository?.getItem(index)
//        return mNewData
//    }

//    fun init() {
//        if (this.mData != null) {
//            return
//        }
//        mData = mWebRepository?.getItem(1)
//    }
}