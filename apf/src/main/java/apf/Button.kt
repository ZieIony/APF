package apf

import android.content.Context
import android.util.AttributeSet

interface ButtonStyle : Style {
    var textProperty: String
}

open class ButtonStyleImpl(context: Context) : StyleImpl(context), ButtonStyle {
    override var textProperty: String = ""

    init {
        isClickableProperty = true
        backgroundProperty = context.resources.getDrawable(R.drawable.apf_background)
        layout = R.layout.apf_button
    }
}

open class Button : BaseView, ButtonStyle {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, ButtonStyleImpl(context))

    constructor(context: Context, style: ButtonStyleImpl = ButtonStyleImpl(context)) : super(context, null, style)

    private var textValue: String? = null
    override var textProperty: String
        get() {
            return textValue ?: (style as ButtonStyle).textProperty
        }
        set(value) {
            textValue = value
            notifyChange()
        }

}
