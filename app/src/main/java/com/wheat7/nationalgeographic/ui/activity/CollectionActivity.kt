package com.wheat7.nationalgeographic.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.wheat7.nationalgeographic.databinding.ActivityCollectionBinding
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.Collects
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.ui.adapter.CollectionAdapter
import kotlinx.android.synthetic.main.activity_collection.*


/**
 * Created by wheat7 on 2017/9/26.
 * https://github.com/wheat7
 */

//列表可取消收藏功能出现错乱问题，暂时放弃，代码保留，下个版本解决
class CollectionActivity : BaseActivity<ActivityCollectionBinding>(),
//        CollectionAdapter.OnCollectClickListener,
        CollectionAdapter.OnItemClickListener {

    var mAdapter: CollectionAdapter = CollectionAdapter(this)
    var mData: Detail? = null
    private val REQUEST_INFO = 100

    override val layoutId: Int
        get() = R.layout.activity_collection

    override fun initView(savedInstanceState: Bundle?) {
        mData = this.intent.extras.getSerializable("DETAIL") as Detail;
        if (mData != null) {
            mAdapter.setData(mData!!)
        }
        recycler_collection.adapter = mAdapter
        recycler_collection.layoutManager = LinearLayoutManager(this)
//        mAdapter.setOnCollectClickListener(this)
        mAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionAdapter.CollectionViewHolder, data: Detail) {
        val intent: Intent = Intent(this@CollectionActivity, DetailActivity::class.java)
        intent.putExtra("DETAIL", Collects.getDetail())
        intent.putExtra("POS", position)
        this@CollectionActivity.startActivityForResult(intent, REQUEST_INFO)
    }

//    override fun onCollectClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionAdapter.CollectionViewHolder, data: Detail) {
//        Collects.deleteItem(data.picture!!.get(position).id)
//        mAdapter.removeItem(position)
//        if (mAdapter.itemCount == 0) {
//            this@CollectionActivity.finish()
//        }

//        val animatorSet = AnimatorSet()
//        val transY = ObjectAnimator.ofFloat(view, "translationY", 300f)
//        val alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
//        animatorSet.duration = 400
//        animatorSet.interpolator = DecelerateInterpolator()
//        animatorSet.play(transY).with(alpha)
//        animatorSet.start()
//        animatorSet.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animator: Animator) {
//
//            }
//
//            override fun onAnimationEnd(animator: Animator) {
//                Collects.deleteItem(data.picture!!.get(position).id)
//                mAdapter.removeItem(position)
//            }
//
//            override fun onAnimationCancel(animator: Animator) {
//
//            }
//
//            override fun onAnimationRepeat(animator: Animator) {
//
//            }
//        })
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_INFO && resultCode == Activity.RESULT_OK) {
            mAdapter.setData(Collects.getDetail())
            if (mAdapter.itemCount == 0) {
                this@CollectionActivity.finish()
            }
        }
    }
}