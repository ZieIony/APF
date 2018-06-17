package apf

import android.content.Context
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

interface Style {
    var backgroundProperty: Drawable?
    var borderColorProperty: ColorStateList?
    var borderThicknessProperty: BorderThickness
    var isClickableProperty: Boolean
}

open class StyleImpl(context: Context) : Style {
    override var backgroundProperty: Drawable? = null
    override var borderColorProperty: ColorStateList? = ColorStateList.valueOf(Color.GRAY)
    override var borderThicknessProperty: BorderThickness = BorderThickness(1.0f, 1.0f, 1.0f, 1.0f)
    override var isClickableProperty: Boolean = false
    var layout: Int = 0
        protected set
}

abstract class BaseView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, style: StyleImpl
) : FrameLayout(context, attrs), Observable, Style {

    var foreground: ColorStateList? = null

    private var borderColorValue: ColorStateList? = null
    override var borderColorProperty: ColorStateList?
        get() = borderColorValue ?: style.borderColorProperty
        set(value) {
            borderColorValue = value
        }

    private var borderThicknessValue: BorderThickness? = null
    override var borderThicknessProperty: BorderThickness
        get() = borderThicknessValue ?: style.borderThicknessProperty
        set(value) {
            borderThicknessValue = value
        }

    private var isClickableValue: Boolean? = null
    override var isClickableProperty: Boolean
        get() = isClickableValue ?: style.isClickableProperty
        set(value) {
            isClickableValue = value
        }

    protected var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var content: ViewDataBinding
    var style = style

    init {
        content = DataBindingUtil.inflate(LayoutInflater.from(context), style.layout, this, true)
        content.setVariable(BR.data, this)
        content.executePendingBindings()
    }

    private var backDrawableValue: Drawable? = null
    override var backgroundProperty: Drawable?
        get() = backDrawableValue ?: style.backgroundProperty
        set(value) {
            backDrawableValue = value
            notifyChange()
        }

    /* override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
         if (!changed)
             return
         (0 until childCount)
                 .map { getChildAt(it) }
                 .forEach { it.layout(0, 0, width, height) }
     }

     override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
         content.root.measure(widthMeasureSpec, heightMeasureSpec)
         setMeasuredDimension(content.root.measuredWidth, content.root.measuredHeight)
     }*/

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    fun addOnPropertyChangedCallback(callback: (View) -> Unit) {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                callback.invoke(this@BaseView)
            }
        })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }
}
