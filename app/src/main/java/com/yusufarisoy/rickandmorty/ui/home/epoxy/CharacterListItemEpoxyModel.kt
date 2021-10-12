package com.yusufarisoy.rickandmorty.ui.home.epoxy

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.databinding.EpoxyModelCharacterListItemBinding
import com.yusufarisoy.rickandmorty.ui.home.CharacterOnClick

@EpoxyModelClass
abstract class CharacterListItemEpoxyModel : ViewBindingEpoxyModel<EpoxyModelCharacterListItemBinding>() {

    @EpoxyAttribute lateinit var character: Character

    @DrawableRes
    @EpoxyAttribute
    var characterStatus: Int? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var characterOnClick: CharacterOnClick

    override fun EpoxyModelCharacterListItemBinding.bind() {
        Glide.with(root).load(character.image).into(imageView)
        textViewName.text = character.name
        val statusAndSpecies = "${character.status} - ${character.species}"
        textViewStatusAndSpecies.text = statusAndSpecies
        textViewLastKnownLocation.text = character.location.name
        characterStatus?.let {
            viewStatus.setBackgroundResource(it)
        }
        itemLayout.setOnClickListener {
            characterOnClick.onClick(character.id)
        }
    }

    override fun EpoxyModelCharacterListItemBinding.unbind() {
        itemLayout.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_character_list_item
}