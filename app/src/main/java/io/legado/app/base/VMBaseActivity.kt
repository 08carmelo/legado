package io.legado.app.base

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.legado.app.constant.Theme

abstract class VMBaseActivity<VB : ViewBinding, VM : ViewModel>(
    fullScreen: Boolean = true,
    theme: Theme = Theme.Light,
    toolBarTheme: Theme = Theme.Light,
    transparent: Boolean = false,
    imageBg: Boolean = true
) : BaseActivity<VB>(fullScreen, theme, toolBarTheme, transparent, imageBg) {

    protected abstract val viewModel: VM

}