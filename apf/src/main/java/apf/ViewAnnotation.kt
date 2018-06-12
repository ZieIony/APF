package apf

import android.support.annotation.IdRes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ViewAnnotation(@IdRes val layout: Int=0)
