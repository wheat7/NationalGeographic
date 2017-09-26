package com.wheat7.nationalgeographic.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.data.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wheat7 on 2017/9/13.
 */

class WebRepository {

    var mWebservice : Webservice? = null

    fun getItem(index : Int) : MutableLiveData<Resource<Item?>>?{

        var data : MutableLiveData<Resource<Item?>> = MutableLiveData<Resource<Item?>>()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://dili.bdatu.com/jiekou/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mWebservice = retrofit.create(Webservice::class.java)

        mWebservice?.getItem(index)?.enqueue(object: retrofit2.Callback<Item?> {
            override fun onResponse(call: Call<Item?>?, response: Response<Item?>?) {
                 data.value = Resource.success(response!!.body())
            }
            override fun onFailure(call: Call<Item?>?, t: Throwable?) {
                data.value = Resource.error(t?.message)
                Log.d("NationalGeographic", "onFailure")
            }
        })
        return data
    }

    fun getDetail(id : String) : MutableLiveData<Resource<Detail?>>? {
        var data : MutableLiveData<Resource<Detail?>> = MutableLiveData<Resource<Detail?>>()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://dili.bdatu.com/jiekou/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mWebservice = retrofit.create(Webservice::class.java)

        mWebservice?.getDetail(id)?.enqueue(object: Callback<Detail?> {
            override fun onFailure(call: Call<Detail?>?, t: Throwable?) {
                data.value = Resource.error(t?.message)
            }

            override fun onResponse(call: Call<Detail?>?, response: Response<Detail?>?) {
                data.value = Resource.success(response?.body())
            }
        })
        return data
    }

}
