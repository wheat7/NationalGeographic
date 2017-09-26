package com.wheat7.nationalgeographic.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.model.Album
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.databinding.ItemCollectionBinding
import com.wheat7.nationalgeographic.databinding.ItemItemBinding

/**
 * Created by wheat7 on 2017/9/26.
 * https://github.com/wheat7
 */
class CollectionAdapter(context : Context) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    var mData : Detail? = null
    private var mContext: Context? = null

    init {
        mContext = context;
    }

    fun setData(data: Detail) {
        mData = data
        notifyDataSetChanged()
    }

    fun removeItem(pos : Int) {
        if (mData != null) {
            mData!!.picture!!.removeAt(pos)
            notifyItemRemoved(pos)
            notifyItemRangeChanged(pos,mData!!.picture!!.size - pos)
        }
        //            notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        if (mData != null) {
            Log.d("NationalGeographic", mData!!.picture!!.size.toString())
            return mData!!.picture!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CollectionViewHolder?, position: Int) {
        Glide.with(mContext).load(mData?.picture!!.get(position).url).into(holder?.binding?.imgCollection)
        holder?.binding!!.itemCollection.setOnClickListener({ view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(this, position, view, holder, mData!!)
            }
        })
//        holder?.binding!!.collect.setOnClickListener({ view ->
//            if (mOnCollectClickListener != null) {
//                mOnCollectClickListener!!.onCollectClick(this, position, holder!!.binding!!.collect, holder, mData!!)
//            }
//        })
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : CollectionViewHolder {
        val binding = DataBindingUtil
                .inflate<ItemCollectionBinding>(LayoutInflater.from(parent?.getContext()), R.layout.item_collection,
                        parent, false)
        return CollectionAdapter.CollectionViewHolder(binding)
    }


    class CollectionViewHolder (binding: ItemCollectionBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCollectionBinding? = null

        init {
            this.binding = binding
        }
    }

    /**
     * Item click callback
     */
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionViewHolder, data: Detail)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    /**
     * Collect click callback
     */
//    private var mOnCollectClickListener : OnCollectClickListener? = null
//
//    interface OnCollectClickListener {
//        fun onCollectClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionViewHolder, data: Detail)
//    }
//    fun setOnCollectClickListener(onCollectClickListener: OnCollectClickListener) {
//        mOnCollectClickListener = onCollectClickListener
//    }

}