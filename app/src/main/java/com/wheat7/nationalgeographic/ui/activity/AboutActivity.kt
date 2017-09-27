package com.wheat7.nationalgeographic.ui.activity

import android.os.Bundle
import com.wheat7.nationalgeographic.databinding.ActivityAboutBinding
import com.wheat7.nationalgeographic.R
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Created by wheat7 on 2017/9/27.
 */
class AboutActivity : BaseActivity<ActivityAboutBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_about
    override fun initView(savedInstanceState: Bundle?) {
        about_back.setOnClickListener({
            onBackPressed()
        })
    }
}