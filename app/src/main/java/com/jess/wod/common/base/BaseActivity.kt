package com.jess.wod.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jess.wod.BR
import com.jess.wod.common.extention.createViewModel
import kotlin.reflect.KClass

/**
 * @author jess
 * @since 2020.06.12
 */
abstract class BaseActivity<VD : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    // ViewDataBinding
    private lateinit var binding: VD

    // 레이아웃 ID
    protected abstract val layoutRes: Int

    // ViewModel Class
    protected abstract val viewModelClass: KClass<VM>

    // AAC ViewModel
    protected val vm by lazy {
        createViewModel(viewModelClass)
    }

    // 레이아웃 초기화
    abstract fun initLayout()

    // onCreate 완료
    abstract fun onCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initLayout()
        onCreated(savedInstanceState)
    }

    /**
     * 데이터 바인딩 초기화
     */
    protected open fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.run {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, vm)
        }
    }
}
