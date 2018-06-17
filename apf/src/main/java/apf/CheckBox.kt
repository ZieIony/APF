package apf

import android.content.Context
import android.util.AttributeSet

class CheckBox : Button {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, style: ButtonStyleImpl) : super(context, style)

    var isChecked:Boolean = false

    override fun performClick(): Boolean {
        isChecked = !isChecked
        return super.performClick()
    }
}
