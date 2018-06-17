package apf.sample

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import apf.Button
import apf.ButtonStyleImpl

class ButtonWithImageStyleImpl(context: Context) : ButtonStyleImpl(context) {
    init {
        layout = R.layout.apf_button_image
    }
}

class ButtonWithImage : Button {

    constructor(context: Context, attrs: AttributeSet) : super(context, ButtonWithImageStyleImpl(context))

    constructor(context: Context, style: ButtonWithImageStyleImpl = ButtonWithImageStyleImpl(context)) : super(context, style)

    var image: Drawable = context.resources.getDrawable(R.mipmap.ic_launcher)

}
