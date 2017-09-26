package com.wheat7.nationalgeographic.ui.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by wheat7 on 2017/9/13.
 */
abstract class LoadMoreRecyclerOnScrollListener(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var isLoading: Boolean = false
    //list到达 最后一个item的时候 触发加载
    private val visibleThreshold = 1
    // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0
    //默认第一页
    private var current_page = 1

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        if (mLayoutManager is LinearLayoutManager) {
            totalItemCount = mLayoutManager.itemCount
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        }
        if (mLayoutManager is StaggeredGridLayoutManager) {
            totalItemCount = mLayoutManager.itemCount
            val lastPositions = mLayoutManager
                    .findFirstVisibleItemPositions(IntArray(mLayoutManager
                            .spanCount))
            firstVisibleItem = getMinPositions(lastPositions)
        }

        //判断加载完成
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isLoading && totalItemCount > visibleItemCount && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            current_page++
            onLoadMore(current_page)
            isLoading = true
            //loadMore(current_page);
        }
    }

    /**
     * 获得当前展示最小的position

     * @param positions
     * *
     * @return
     */
    private fun getMinPositions(positions: IntArray): Int {
        val size = positions.size
        var minPosition = Integer.MAX_VALUE
        for (i in 0..size - 1) {
            minPosition = Math.min(minPosition, positions[i])
        }
        return minPosition
    }

    abstract fun onLoadMore(current_page: Int)
}
