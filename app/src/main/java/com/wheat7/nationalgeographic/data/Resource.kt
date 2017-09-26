package com.wheat7.nationalgeographic.data

import android.support.annotation.NonNull



/**
 * Created by wheat7 on 2017/9/14.
 */

//a generic class that describes a data with a status

class Resource<T>(){

    var mData : T? = null
    var mStatus : Int? = null
    var mMessage : String? = null

    private constructor(status : Int, data: T?, message : String?) : this() {
        this.mData = data
        this.mStatus = status
        this.mMessage = mMessage
    }

    companion object {
        private val SUCCESS : Int = 1
        private val ERROR : Int = 2
        private val LOADING : Int = 3

        fun <T> success(data: T): Resource<T>? {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }

}