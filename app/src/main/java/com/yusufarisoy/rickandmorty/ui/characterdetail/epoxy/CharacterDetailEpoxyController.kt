package com.yusufarisoy.rickandmorty.ui.characterdetail.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.rickandmorty.ui.characterdetail.CharacterDetailViewModel.CharacterDetailState
import com.yusufarisoy.rickandmorty.ui.characterdetail.LocationOnClick
import com.yusufarisoy.rickandmorty.util.getStatusResourceId

class CharacterDetailEpoxyController(
    private val _locationOnClick: LocationOnClick
) : TypedEpoxyController<CharacterDetailState>() {

    override fun buildModels(data: CharacterDetailState) {
        data.character?.let(::buildHeader)
    }

    private fun buildHeader(character: Character) {
        val locationOnClick = _locationOnClick
        characterDetailHeader {
            id("Character Detail Header")
            character(character)
            characterStatus(getStatusResourceId(character))
            locationOnClick(locationOnClick)
        }
    }
}