package com.yusufarisoy.core.views.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide
import com.yusufarisoy.core.R
import com.yusufarisoy.core.data.entity.location.Location
import com.yusufarisoy.core.databinding.EpoxyModelLocationListItemBinding
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel

@EpoxyModelClass
abstract class LocationListItemEpoxyModel : ViewBindingEpoxyModel<EpoxyModelLocationListItemBinding>() {

    @EpoxyAttribute lateinit var location: Location

    override fun EpoxyModelLocationListItemBinding.bind() {
        Glide.with(root).load("https://www.wallpaperbetter.com/wallpaper/65/775/705/space-planet-1080P-wallpaper.jpg").into(imageView)
        val nameAndDimension = "${location.name} - ${location.dimension}"
        textViewName.text = nameAndDimension
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_location_list_item
}