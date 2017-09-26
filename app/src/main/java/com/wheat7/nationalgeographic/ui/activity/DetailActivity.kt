package com.wheat7.nationalgeographic.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.*
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.Collects
import com.wheat7.nationalgeographic.data.DatabaseHelper
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.data.model.Picture
import com.wheat7.nationalgeographic.databinding.ActivityDetailBinding
import com.wheat7.nationalgeographic.ui.adapter.DetailPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

/**
 * Created by wheat7 on 2017/9/16.
 * https://github.com/wheat7
 */
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    var mPictures: List<Picture>? = null

    var mCurrentPos: Int = 0

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    private val REQUEST_PERMISSIONS = 1

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
        Collects.getCollect(this)
        val detailPagerAdapter = DetailPagerAdapter()
        val data: Detail = this.intent.extras.getSerializable("DETAIL") as Detail;
        detailPagerAdapter.setData(data)
        mPictures = data.picture
        getBinding().saveProgressVisible = false
        getBinding().data = mPictures!!.get(0)
        getBinding().total = data.counttotal
        getBinding().pos = 1
        getBinding().titleAndContentVisible = true
        getBinding().isCollect = false
        if (Collects.isCollect(mPictures!!.get(0))) {
            getBinding().isCollect = true
        }
        view_pager.adapter = detailPagerAdapter
        view_pager.setCurrentItem(0)
        val firstPos = this.intent.extras.getInt("POS")
        if (firstPos != 0) {
            view_pager.currentItem = firstPos
            getBinding().pos = firstPos + 1
        }
        view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                if (mPictures != null) {
                    getBinding().data = mPictures!!.get(position)
                    getBinding().pos = position + 1
                    mCurrentPos = position
                    getBinding().isCollect = false
                    if (Collects.isCollect(mPictures!!.get(position))) {
                        getBinding().isCollect = true
                    }
                }
            }
        })

        detailPagerAdapter.setonPageClickListener(object : DetailPagerAdapter.OnPageClickListener {
            override fun onClick() {
                if (getBinding().titleAndContentVisible == true) {
                    val animatorSet = AnimatorSet()
                    val scaleX = ObjectAnimator.ofFloat(tool_bar, "alpha", 0F)
                    val scaleY = ObjectAnimator.ofFloat(ll_des, "alpha", 0F)
                    animatorSet.duration = 600
                    animatorSet.interpolator = DecelerateInterpolator()
                    animatorSet.play(scaleX).with(scaleY)
                    animatorSet.start()
                    animatorSet.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {
                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            getBinding().titleAndContentVisible = false
                        }

                        override fun onAnimationCancel(p0: Animator?) {
                        }

                        override fun onAnimationStart(p0: Animator?) {
                        }
                    })
                } else {
                    tool_bar.animate().alpha(1f).duration = 400
                    ll_des.animate().alpha(1f).duration = 400
                    getBinding().titleAndContentVisible = true
                }
            }
        })
        checkPermission()
        initClick()
        setResult(Activity.RESULT_OK)
    }

    /**
     * 请求权限
     */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSIONS)
        } else {
//            getPic()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                run {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        getPic()
                    }
                }
                return
            }
        }
    }

    private fun initClick() {
        detail_back.setOnClickListener({
            this@DetailActivity.finish()
        })

        detail_collect.setOnClickListener({
            if (Collects.isCollect(mPictures!!.get(mCurrentPos))) {
                Collects.deleteItem(mPictures!!.get(mCurrentPos).id)
                getBinding().isCollect = false
                Toast.makeText(this@DetailActivity, "取消收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                Collects.collectItem(mPictures!!.get(mCurrentPos))
                getBinding().isCollect = true
                Toast.makeText(this@DetailActivity, "收藏成功", Toast.LENGTH_SHORT).show()
            }
        })

        detail_share.setOnClickListener({
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TEXT, mPictures?.get(mCurrentPos)?.url + " -来自国家地理")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "分享"))
        })

        detail_save.setOnClickListener({
            val handler: Handler = Handler()
            getBinding().saveProgressVisible = true
            Glide.with(this)
                    .load(mPictures!!.get(mCurrentPos).url).asBitmap().listener(object : RequestListener<String?, Bitmap?> {
                override fun onException(e: Exception?, model: String?, target: Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
                    handler.post({
                        Toast.makeText(this@DetailActivity, "下载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                        getBinding().saveProgressVisible = false
                    })
                    return true
                }

                override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap?>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    val fileDirPath: File = File(Environment.getExternalStorageDirectory().path + "/NationalGeographic/" + "/image/")
                    if (!fileDirPath.exists()) {
                        fileDirPath.mkdirs()
                    }
                    val fileName = mPictures!!.get(mCurrentPos).id + ".jpeg"

                    val filePath: File = File(fileDirPath, fileName)

                    if (filePath.exists()) {
                        handler.post({
                            Toast.makeText(this@DetailActivity, "此照片已保存过", Toast.LENGTH_SHORT).show()
                            getBinding().saveProgressVisible = false
                        })
                    } else {
                        try {
                            var fileOutputStream: FileOutputStream = FileOutputStream(filePath)
                            Log.d("NationalGeographic", resource.toString())
                            resource?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                            fileOutputStream.flush()
                            fileOutputStream.close()
                            handler.post({
                                Toast.makeText(this@DetailActivity, "保存成功", Toast.LENGTH_SHORT).show()
                                getBinding().saveProgressVisible = false
                            })
                        } catch (e: IOException) {
                            handler.post({
                                Toast.makeText(this@DetailActivity, "保存失败", Toast.LENGTH_SHORT).show()
                                getBinding().saveProgressVisible = false
                            })
                        }
                    }
                    return true
                }
            }).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        })
    }
}