package com.yusufarisoy.core.utils

import android.view.View
import android.view.ViewParent
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
abstract class ViewBindingEpoxyModel<T : ViewBinding> : EpoxyModelWithHolder<ViewBindingEpoxyHolder>() {

    override fun bind(holder: ViewBindingEpoxyHolder) {
        val binding = holder.binding as T
        binding.bind()
    }

    override fun unbind(holder: ViewBindingEpoxyHolder) {
        val binding = holder.binding as T
        binding.unbind()
    }

    abstract fun T.bind()

    open fun T.unbind() = Unit

    override fun createNewHolder(parent: ViewParent): ViewBindingEpoxyHolder = ViewBindingEpoxyHolder(this::class.java)
}

class ViewBindingEpoxyHolder(private val epoxyModelClass: Class<*>) : EpoxyHolder() {

    private val bindingMethod by lazy { getBindMethodFrom(epoxyModelClass) }

    lateinit var binding: ViewBinding

    override fun bindView(itemView: View) {
        binding = bindingMethod.invoke(null, itemView) as ViewBinding
    }
}

private val BindingMethodByClass = ConcurrentHashMap<Class<*>, Method>()

@Suppress("UNCHECKED_CAST")
@Synchronized
internal fun getBindMethodFrom(javaClass: Class<*>) = BindingMethodByClass.getOrPut(javaClass) {
    val actualTypeOfThis = getSuperclassParameterizedType(javaClass)
    val viewBindingClass = actualTypeOfThis.actualTypeArguments[0] as Class<ViewBinding>
    viewBindingClass.getDeclaredMethod("bind", View::class.java)
}

private fun getSuperclassParameterizedType(klass: Class<*>): ParameterizedType {
    val genericSuperclass = klass.genericSuperclass
    val parameterizedType = genericSuperclass as? ParameterizedType
    return parameterizedType ?: getSuperclassParameterizedType(genericSuperclass as Class<*>)
}