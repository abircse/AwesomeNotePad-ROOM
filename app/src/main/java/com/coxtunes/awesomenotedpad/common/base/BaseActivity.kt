package com.coxtunes.iptv.common.base

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.ui.R
import com.coxtunes.awesomenotedpad.common.utility.LoadingDialog


abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: V
    internal lateinit var navController: NavController


    @get:LayoutRes
    protected abstract val layoutRes: Int

    private val loadingDialog: LoadingDialog by lazy(mode = LazyThreadSafetyMode.NONE) {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        onCreated(savedInstanceState)
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog.let {
            if (isLoading && !loadingDialog.isShowing)
                loadingDialog.show()
            else if (!isLoading && loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }


    fun startFragment(id: Int, data: Bundle? = null, noHistory: Boolean = false) {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        builder.setPopUpTo(id, noHistory)
        navController.navigate(
            id,
            data,
            builder.build()
        )
    }

    fun <T> goToActivity(destination: Class<T>) {
        val intent = Intent(this, destination)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    open fun hideKeyboardFrom() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


    fun <T> goToActivityWithOutClearStack(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
    }

    protected abstract fun onCreated(instance: Bundle?)

    abstract fun backPressedAction()

    fun showToastMessage(message: String, isPositive: Boolean = false) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}