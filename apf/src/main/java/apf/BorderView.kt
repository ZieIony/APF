package apf

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

interface BorderStyle : Style

class BorderStyleImpl(context: Context) : StyleImpl(context), BorderStyle {
    init {
        layout = R.layout.apf_border
    }
}

open class BorderView : BaseView, BorderStyle {


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, BorderStyleImpl(context))

    constructor(context: Context, style: BorderStyleImpl = BorderStyleImpl(context)) : super(context, null, style)

    override fun dispatchDraw(canvas: Canvas) {
        borderColorProperty?.let {
            paint.color = it.getColorForState(drawableState, it.defaultColor)
            canvas.drawRect(0.0f, 0.0f, width.toFloat(), borderThicknessProperty.top, paint)
            canvas.drawRect(0.0f, 0.0f, borderThicknessProperty.left, height.toFloat(), paint)
            canvas.drawRect(width - borderThicknessProperty.right, 0.0f, width.toFloat(), height.toFloat(), paint)
            canvas.drawRect(0.0f, height - borderThicknessProperty.bottom, width.toFloat(), height.toFloat(), paint)
        }
        super.dispatchDraw(canvas)
    }
}
