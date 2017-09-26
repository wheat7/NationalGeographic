package com.wheat7.nationalgeographic.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.wheat7.nationalgeographic.databinding.ActivityInfoBinding
import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.data.Collects
import kotlinx.android.synthetic.main.activity_info.*


/**
 * Created by wheat7 on 2017/9/24.
 * https://github.com/wheat7
 */

class InfoActivity : BaseActivity<ActivityInfoBinding>() {

    private val REQUEST_INFO = 100
    override val layoutId: Int
        get() = R.layout.activity_info


    override fun initView(savedInstanceState: Bundle?) {
        initClick()
        Collects.getCollect(this)
    }

    private fun initClick() {
        info_back.setOnClickListener({
            onBackPressed()
        })

        info_disclaimer.setOnClickListener( {
            val dialogBuild : AlertDialog? = AlertDialog.Builder(this@InfoActivity)
                    .setTitle("免责申明")
                    .setMessage("应用中的所有数据来源于网络，所有内容版权归原创者或所有方所有，应用仅作学习交流之用，严禁用于商业用途，代码遵守GPL3.0协议，请勿违反")
                    .setPositiveButton("确定" , null)
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