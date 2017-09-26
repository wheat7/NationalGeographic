package com.wheat7.nationalgeographic.ui.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.Resource
import com.wheat7.nationalgeographic.data.model.Album
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.data.model.Item
import com.wheat7.nationalgeographic.databinding.ActivityMainBinding
import com.wheat7.nationalgeographic.ui.adapter.ItemAdapter
import com.wheat7.nationalgeographic.ui.listener.LoadMoreRecyclerOnScrollListener
import com.wheat7.nationalgeographic.viewmodel.DetailViewModel
import com.wheat7.nationalgeographic.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener, ItemAdapter.OnItemClickListener {

    var mItemViewModel: ItemViewModel? = null
    var mAdapter: ItemAdapter? = ItemAdapter(this)
    private var mCurrentPage: Int = 1

    private val SUCCESS: Int = 1
    private val ERROR: Int = 2
    //private val LOADING : Int = 3

    var mProgressDialog: ProgressDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setMessage("正在加载...")
        mProgressDialog?.setCanceledOnTouchOutside(false)
        recycler.adapter = mAdapter
        mAdapter?.setOnItemClickListener(this)
        val layoutManager = LinearLayoutManager(this)
        recycler.setLayoutManager(layoutManager)
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        swipe_refresh.setOnRefreshListener(this)
        onRefresh()

        recycler.setOnScrollListener(object : LoadMoreRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                mCurrentPage = currentPage
                getMoreItem()
            }
        })

        var clickTime: Long = 0
        title_main.setOnClickListener({
            if (System.currentTimeMillis() - clickTime > 2000) {
                Toast.makeText(this, "再按一次回到顶部", Toast.LENGTH_SHORT).show()
                clickTime = System.currentTimeMillis()
            } else {
                recycler.smoothScrollToPosition(0)
            }
        })

        ic_more.setOnClickListener({ view ->
            val intent : Intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        })
    }

    private fun getMoreItem() {
        mItemViewModel?.getItem(mCurrentPage)?.observe(this@MainActivity, object : Observer<Resource<Item?>?> {
            override fun onChanged(t: Resource<Item?>?) {
                Log.d("NationalGeographic", "onChanged")
                mAdapter?.setIsLoading()
                if (t!!.mStatus == SUCCESS) {
                    mAdapter?.addData(t!!.mData!!.album)
                } else {
                    mAdapter?.setNetError()
                    mAdapter?.setOnReloadClickListener(object : ItemAdapter.OnReloadClickListener {
                        override fun onClick() {
                            Toast.makeText(this@MainActivity, "加载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                            getMoreItem()
                        }
                    })
                }
            }
        })
    }

    //在点击的时候进行网络请求，成功后传递数据到DetailActivity，避免了在DetailActivity进行网络请求没有请求到时没有数据填充
    var mDetailViewModel: DetailViewModel? = null
    override fun onClick(adapter: ItemAdapter, position: Int, view: View, itemViewHolder: ItemAdapter.ItemViewHolder, data: MutableList<Album>) {
        mProgressDialog?.show()
        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        mDetailViewModel!!.getDetail(data.get(position).id!!)!!.observe(this, object : Observer<Resource<Detail?>?> {
            override fun onChanged(t: Resource<Detail?>?) {
                if (t!!.mStatus == SUCCESS) {
                    val intent: Intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("DETAIL", t!!.mData!!)
                    this@MainActivity.startActivity(intent)
                    mProgressDialog?.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "加载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                    mProgressDialog?.dismiss()
                }
            }
        })
    }

    override fun onRefresh() {
        swipe_refresh.isRefreshing = true
        mItemViewModel?.getItem(1)?.observe(this, object : Observer<Resource<Item?>?> {
            override fun onChanged(t: Resource<Item?>?) {
                if (t!!.mStatus == SUCCESS) {
                    view_error.visibility = View.GONE
                    swipe_refresh.isRefreshing = false
                    mAdapter?.setData(t!!.mData!!.album)
                }
                if (t!!.mStatus == ERROR) {
                    swipe_refresh.isRefreshing = false
                    if (mAdapter?.getRealCount() == 0) {
                        view_error.visibility = View.VISIBLE
                        view_error.setOnClickListener(View.OnClickListener {
                            onRefresh()
                        })
                    }
                    Toast.makeText(this@MainActivity, " 加载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    var exitTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }
}
