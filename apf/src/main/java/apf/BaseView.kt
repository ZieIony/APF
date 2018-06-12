package apf

import android.content.Context
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Paint
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

abstract class BaseView<T : ViewDataBinding> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var foreground: ColorStateList? = null
    var borderColor: ColorStateList? = null
    var borderThickness: BorderThickness = BorderThickness(0.0f, 0.0f, 0.0f, 0.0f)

    lateinit var content: T

    protected var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    @IdRes
    protected open val layout: Int? = 0

    init {
        layout?.let {
            DataBindingUtil.inflate<T>(LayoutInflater.from(context), it, this, true)
        }
    }

}
