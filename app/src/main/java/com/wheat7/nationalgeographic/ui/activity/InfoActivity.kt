package com.wheat7.nationalgeographic.ui.activity

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.wheat7.nationalgeographic.databinding.ActivityInfoBinding
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.Collects
import com.wheat7.nationalgeographic.utlis.GlideCacheUtil
import com.wheat7.nationalgeographic.viewmodel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*


/**
 * Created by wheat7 on 2017/9/24.
 * https://github.com/wheat7
 */

class InfoActivity : BaseActivity<ActivityInfoBinding>() {

    var mInfoViewModel: InfoViewModel? = null


    override val layoutId: Int
        get() = R.layout.activity_info


    override fun initView(savedInstanceState: Bundle?) {
        mInfoViewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
        mInfoViewModel?.getImageCache(this)!!.observe(this, object : Observer<String?> {
            override fun onChanged(t: String?) {
                getBinding().cache = t
            }
        })
        initClick()
        Collects.getCollect(this)
    }

    private fun initClick() {
        info_back.setOnClickListener({
            onBackPressed()
        })

        info_about.setOnClickListener({
            val intent: Intent = Intent(this@InfoActivity, AboutActivity::class.java)
            this@InfoActivity.startActivity(intent)
        })

        info_checkupdate.setOnClickListener({
            val intent: Intent = Intent(this@InfoActivity, WebActivity::class.java)
            intent.putExtra("URL", "https://github.com/wheat7/NationalGeographic")
            this@InfoActivity.startActivity(intent)
        })

        info_share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享国家地理")
            intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/wheat7/NationalGeographic" + " -国家地理")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "分享国家地理"))
        }

        info_cache.setOnClickListener({
            GlideCacheUtil.getInstance().clearImageAllCache(this@InfoActivity)
            getBinding().cache = "0KB"
            Toast.makeText(this@InfoActivity, "清除成功", Toast.LENGTH_SHORT).show()
        })

        info_feedback.setOnClickListener {
            val intent: Intent = Intent(this@InfoActivity, WebActivity::class.java)
            intent.putExtra("URL", "https://github.com/wheat7/NationalGeographic/issues")
            this@InfoActivity.startActivity(intent)
        }

        info_disclaimer.setOnClickListener({
            val dialogBuild: AlertDialog? = AlertDialog.Builder(this@InfoActivity)
                    .setTitle("免责申明")
                    .setMessage("应用使用GPL3.0作为开源许可协议，并且应用中的所有数据以及资源来源于网络，所有内容和商标的版权归原创者或所有方所有，应用仅作学习交流之用，严禁用于商业用途，违反申明所引发的一切问题由使用者承担")
                    .setPositiveButton("确定", null)
                    .show()
        })

        info_collect.setOnClickListener({
            if (!Collects.getDetail().counttotal.equals("0")) {
                val intent: Intent = Intent(this@InfoActivity, CollectionActivity::class.java)
                intent.putExtra("DETAIL", Collects.getDetail())
                this@InfoActivity.startActivity(intent)
            } else {
                Toast.makeText(this@InfoActivity, "收藏为空", Toast.LENGTH_SHORT).show()
            }
        })
    }
}