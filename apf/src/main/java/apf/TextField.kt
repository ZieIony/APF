package apf

import android.content.Context
import android.util.AttributeSet

interface TextFieldStyle : Style {
    var textProperty: String
}

class TextFieldStyleImpl(context: Context) : StyleImpl(context), TextFieldStyle {
    override var textProperty: String = ""

    init {
        layout = R.layout.apf_text_field
    }
}

class TextField : BaseView, TextFieldStyle {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, TextFieldStyleImpl(context))

    constructor(context: Context, style: TextFieldStyleImpl = TextFieldStyleImpl(context)) : super(context, null, style)

    private var textValue: String? = null
    override var textProperty: String
        get() {
            return textValue ?: (style as TextFieldStyle).textProperty
        }
        set(value) {
            textValue = value
            notifyChange()
            invalidate()
        }

}
