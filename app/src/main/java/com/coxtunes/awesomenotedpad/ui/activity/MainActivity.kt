package com.coxtunes.awesomenotedpad.ui.activity

import android.os.Bundle
import com.coxtunes.awesomenotedpad.R
import com.coxtunes.awesomenotedpad.databinding.ActivityMainBinding
import com.coxtunes.iptv.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreated(instance: Bundle?) {

    }

    override fun backPressedAction() {
        onBackPressed()
    }
}