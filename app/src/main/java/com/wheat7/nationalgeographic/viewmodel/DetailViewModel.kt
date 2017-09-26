package com.wheat7.nationalgeographic.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wheat7.nationalgeographic.data.Resource
import com.wheat7.nationalgeographic.data.WebRepository
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.data.model.Item

/**
 * Created by wheat7 on 2017/9/17.
 * https://github.com/wheat7
 */

class DetailViewModel() : ViewModel() {
    private var mWebRepository : WebRepository = WebRepository()

    constructor(repository : WebRepository) : this() {
        mWebRepository = repository
    }

    fun getDetail(id : String) : MutableLiveData<Resource<Detail?>>? {

        return mWebRepository?.getDetail(id)
    }

}