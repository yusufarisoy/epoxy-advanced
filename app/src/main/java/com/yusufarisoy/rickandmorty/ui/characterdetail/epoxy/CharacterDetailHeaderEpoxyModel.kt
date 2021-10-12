package com.yusufarisoy.rickandmorty.ui.characterdetail.epoxy

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.databinding.EpoxyModelCharacterDetailHeaderBinding
import com.yusufarisoy.rickandmorty.ui.characterdetail.LocationOnClick

@EpoxyModelClass
abstract class CharacterDetailHeaderEpoxyModel : ViewBindingEpoxyModel<EpoxyModelCharacterDetailHeaderBinding>() {

    @EpoxyAttribute lateinit var character: Character

    @DrawableRes
    @EpoxyAttribute
    var characterStatus: Int? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var locationOnClick: LocationOnClick

    override fun EpoxyModelCharacterDetailHeaderBinding.bind() {
        characterStatus?.let {
            viewStatus.setBackgroundResource(it)
        }
        textViewStatus.text = character.status
        textViewSpecies.text = character.species
        textViewGender.text = character.gender
        textViewLocation.text = character.location.name
        textViewLocation.setOnClickListener {
            locationOnClick.onClick(character.location.url)
        }
    }

    override fun EpoxyModelCharacterDetailHeaderBinding.unbind() {
        textViewLocation.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_character_detail_header
}