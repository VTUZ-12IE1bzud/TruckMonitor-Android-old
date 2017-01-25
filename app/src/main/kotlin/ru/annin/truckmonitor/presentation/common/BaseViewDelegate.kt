package ru.annin.truckmonitor.presentation.common

import android.content.Context
import android.content.res.Resources
import android.support.annotation.ColorRes
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Базовый ViewDelegate.
 *
 * @author Pavel Annin.
 */
abstract class BaseViewDelegate(val vRoot: View) {

    protected val context: Context
        get() = vRoot.context

    protected val resource: Resources
        get() = vRoot.resources

    inline fun <reified T : View> findView(@IdRes id: Int, root: View = vRoot) = lazy { root.findViewById(id) as T }

    protected fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}