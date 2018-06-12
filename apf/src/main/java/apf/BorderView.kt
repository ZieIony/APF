package apf

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import apf.databinding.ApfViewBorderBinding

open class BorderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView<ApfViewBorderBinding>(context, attrs, defStyleAttr) {

    override val layout: Int?
        get() = R.layout.apf_view_border

    init {
        borderThickness = BorderThickness(1.0f, 2.0f, 3.0f, 4.0f)
        borderColor = ColorStateList.valueOf(Color.RED)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        borderColor?.let {
            paint.color = it.getColorForState(drawableState, it.defaultColor)
            canvas?.drawRect(0.0f, 0.0f, width.toFloat(), borderThickness.top, paint)
            canvas?.drawRect(0.0f, 0.0f, borderThickness.left, height.toFloat(), paint)
            canvas?.drawRect(width - borderThickness.right, 0.0f, width.toFloat(), height.toFloat(), paint)
            canvas?.drawRect(0.0f, height - borderThickness.bottom, width.toFloat(), height.toFloat(), paint)
        }
        super.dispatchDraw(canvas)
    }
}