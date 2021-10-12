package com.yusufarisoy.core.views.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.yusufarisoy.core.R
import com.yusufarisoy.core.databinding.EpoxyModelTitleBinding
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel

@EpoxyModelClass
abstract class TitleEpoxyModel : ViewBindingEpoxyModel<EpoxyModelTitleBinding>() {

    @EpoxyAttribute var text: String? = null

    override fun EpoxyModelTitleBinding.bind() {
        textViewTitle.text = text
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_title
}