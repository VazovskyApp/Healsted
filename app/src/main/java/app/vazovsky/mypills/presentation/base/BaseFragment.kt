package app.vazovsky.mypills.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.core.view.doOnPreDraw
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import app.vazovsky.mypills.extensions.observeNavigationCommands
import app.vazovsky.mypills.extensions.showErrorSnackbar
import app.vazovsky.mypills.managers.BottomNavigationViewManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    open val showBottomNavigationView: Boolean
        get() = (parentFragment as? BaseFragment)?.showBottomNavigationView ?: false

    protected var bottomNavigationViewManager: BottomNavigationViewManager? = null

    override fun onAttach(context: Context) {
        if (context is BottomNavigationViewManager) {
            bottomNavigationViewManager = context
        }

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            callOperations()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupLayout(savedInstanceState)
        setupBottomNavigationVisibility()
        if (showBottomNavigationView) {
            bottomNavigationViewManager?.getNavigationView()?.apply {
                if (height > 0) {
                    applyBottomNavigationViewPadding(
                        view,
                        height + marginTop + marginBottom
                    )
                } else {
                    doOnPreDraw {
                        if (this@BaseFragment.view != null) {
                            applyBottomNavigationViewPadding(
                                view,
                                height + marginTop + marginBottom
                            )
                        }
                    }
                }
            }
        }
        onBindViewModel()
    }

    /**
     * Метод выставляет обработку отступа от нижнего меню, если оно есть
     * Необходимо переопределить во фрагменте для кастомного поведения
     */

    protected open fun applyBottomNavigationViewPadding(view: View, bottomNavigationViewHeight: Int) {
        view.updatePadding(bottom = view.paddingBottom + bottomNavigationViewHeight)
    }

    override fun onStart() {
        super.onStart()
        setupBottomNavigationVisibility()
    }

    private fun setupBottomNavigationVisibility() {
        bottomNavigationViewManager?.setNavigationViewVisibility(showBottomNavigationView)
    }

    abstract fun callOperations()

    abstract fun onSetupLayout(savedInstanceState: Bundle?)

    abstract fun onBindViewModel()

    fun BaseViewModel.observeNavigationCommands() {
        observeNavigationCommands(this)
    }

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@BaseFragment.viewLifecycleOwner) { t -> block.invoke(t) }
    }


    /**
     * Показываем Snackbar с ошибкой, если не передаем marginBottom, то при наличии нижнего меню, он автоматически поднимается
     */
    fun errorSnackbar(
        message: String,
        @Px marginBottom: Int = 0,
        actionText: String? = null,
        action: () -> Unit = {}
    ) {
        val realMarginBottom = marginBottom.takeIf { it > 0 } ?: run { getMenuMarginBottom() }
        showErrorSnackbar(message, actionText, marginBottom = realMarginBottom, action = action)
    }

    /**
     * Нижний отступ контента над нижнем меню
     */
    fun getMenuMarginBottom(): Int {
        return bottomNavigationViewManager?.getMenuMarginBottom() ?: 0
    }
}