package com.coxtunes.awesomenotedpad.common.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.coxtunes.iptv.common.base.BaseActivity

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    private var _binding: V? = null
    val binding get() = _binding!!
    lateinit var navController: NavController

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<V>(inflater, layoutRes, container, false).also {
            it.lifecycleOwner = this.viewLifecycleOwner
        }
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
    }

    fun showToastMessage(message: String, isPositive: Boolean = false) {
        (this.activity as BaseActivity<*>).showToastMessage(message, isPositive)
    }

    protected abstract fun onCreated(savedInstance: Bundle?)

    abstract fun backPressedAction()

    fun <T> goToActivity(destination: Class<T>) {
        val intent = Intent(activity, destination)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.activity?.startActivity(intent)
        this.activity?.finish()
    }

    fun hideKeyboardFrom() {
        val imm: InputMethodManager? =
            activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    fun <T> goToActivityWithOutClearStack(destination: Class<T>) {
        val intent = Intent(activity, destination)
        this.activity?.startActivity(intent)
    }

    protected fun showLoading(loading: Boolean) {
        activity?.let { a -> (a as BaseActivity<*>).showLoading(loading) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}