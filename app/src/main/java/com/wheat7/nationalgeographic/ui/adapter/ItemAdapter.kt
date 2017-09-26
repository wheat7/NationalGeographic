package com.wheat7.nationalgeographic.ui.adapter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.WebRepository
import com.wheat7.nationalgeographic.data.model.Album
import com.wheat7.nationalgeographic.data.model.Item
import com.wheat7.nationalgeographic.databinding.ItemItemBinding
import com.wheat7.nationalgeographic.databinding.ViewEmptyBinding
import com.wheat7.nationalgeographic.databinding.ViewRecyclerLoadingBinding
import com.wheat7.nationalgeographic.ui.activity.DetailActivity

/**
 * Created by wheat7 on 2017/9/13.
 */
class ItemAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mData: MutableList<Album> = ArrayList<Album>()
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2
    //空的layout是为了防止getItemCount()+1后没有数据的时候返回一个空的itemView
    private val TYPE_EMPTY = 3
    private var mContext: Context? = null

    init {
        mContext = context;
    }

    fun getRealCount(): Int {
        return mData.size
    }

    fun setData(data: MutableList<Album>) {
        mData = data
        notifyDataSetChanged()
    }

    fun addData(newData: MutableList<Album>) {
        mData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (mData!!.size > 0) {
            if (position + 1 == itemCount) {
                return TYPE_FOOTER
            } else if (mData!!.size > 0) {
                return TYPE_ITEM
            }
        }
        return TYPE_EMPTY;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            if (mData.size != 0) {
                holder.binding!!.title = mData?.get(position)?.title
                Glide.with(mContext)
                        .load(mData?.get(position)?.url).crossFade()
                        .into(holder.binding?.imgItem)
                holder.binding!!.itemDaily.setOnClickListener { view ->
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener!!.onClick(this, position, view, holder, mData)
                    }
                }

            }
        }
    }

    var footerViewHolder: FooterViewHolder? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == TYPE_ITEM) {
            val binding = DataBindingUtil
                    .inflate<ItemItemBinding>(LayoutInflater.from(parent?.getContext()), R.layout.item_item,
                            parent, false)
            return ItemViewHolder(binding)
        } else if (viewType == TYPE_FOOTER) {
            val binding = DataBindingUtil
                    .inflate<ViewRecyclerLoadingBinding>(LayoutInflater.from(parent?.getContext()), R.layout.view_recycler_loading,
                            parent, false)
            footerViewHolder = FooterViewHolder(binding)
            return footerViewHolder
        }
        val binding = DataBindingUtil
                .inflate<ViewEmptyBinding>(LayoutInflater.from(parent?.getContext()), R.layout.view_empty,
                        parent, false)

        return EmptyViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }

    class EmptyViewHolder(binding: ViewEmptyBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ItemViewHolder(binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemItemBinding? = null

        init {
            this.binding = binding
        }
    }

    class FooterViewHolder(binding: ViewRecyclerLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ViewRecyclerLoadingBinding? = null

        init {
            this.binding = binding
        }
    }

    fun setIsLoading() {
        footerViewHolder?.binding?.textLoading?.setText("正在加载更多...")
        footerViewHolder?.binding?.progressLoading?.setVisibility(View.VISIBLE)
    }

    fun setOnNoLoadMore() {
        footerViewHolder?.binding?.textLoading?.setText("没有更多了")
        footerViewHolder?.binding?.progressLoading?.setVisibility(View.GONE)
    }

    fun setNetError() {
        Log.d("NationalGeographic", "setNetError")
        footerViewHolder?.binding?.textLoading?.setText("加载失败，点击重试")
        footerViewHolder?.binding?.viewLoading?.setOnClickListener(View.OnClickListener { if (mOnReloadClickListener != null) mOnReloadClickListener!!.onClick() })
        footerViewHolder?.binding?.progressLoading?.setVisibility(View.GONE)
    }

    //网络问题重新加载时点击回调
    private var mOnReloadClickListener: OnReloadClickListener? = null

    interface OnReloadClickListener {
        fun onClick()
    }

    fun setOnReloadClickListener(onReloadClickListener: OnReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener
    }

    //点击事件回调

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(adapter: ItemAdapter, position: Int, view: View, itemViewHolder: ItemViewHolder, data: MutableList<Album>)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }


}