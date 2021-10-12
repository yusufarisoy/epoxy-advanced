package com.yusufarisoy.rickandmorty.ui.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.location.Location
import com.yusufarisoy.core.utils.SimplifiedTextWatcher
import com.yusufarisoy.core.views.epoxy.*
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.ui.home.CharacterOnClick
import com.yusufarisoy.rickandmorty.ui.home.HomeViewModel.HomeState
import com.yusufarisoy.rickandmorty.util.getStatusResourceId

class HomeEpoxyController(
    private val _textWatcher: SimplifiedTextWatcher,
    private val _characterOnClick: CharacterOnClick
) : TypedEpoxyController<HomeState>() {

    override fun buildModels(data: HomeState) {
        data.locations?.let {
            buildSearchView(data.searchText)// What's the best way?
            //buildTitle("", "")
            //buildLocations(it)
        }
        data.locations?.let(::buildLocations)
        data.characters?.let(::buildCharacters)
    }

    private fun buildTitle(key: String, text: String) {
        title {
            id(key)
            text(text)
        }
    }

    private fun buildSearchView(searchText: String?) {
        val textWatcher = _textWatcher
        textInput {
            id("Search View")
            hint(R.string.search)
            text(searchText)
            textWatcher(textWatcher)
        }
    }

    private fun buildLocations(locations: List<Location>) {
        buildTitle("Locations Title", "Locations")
        val locationModels = locations.map {
            LocationListItemEpoxyModel_()
                .id("location_id_${it.id}")
                .location(it)
        }
        horizontalCarousel {
            id("id_locationsCarousel")
            models(locationModels)
        }
    }

    private fun buildCharacters(characters: List<Character>) {
        buildTitle("Characters Title", "Characters")
        verticalGridCarousel {
            id("id_charactersCarousel")
            models(characters.map {
                CharacterListItemEpoxyModel_()
                    .id("character_id_${it.id}")
                    .character(it)
                    .characterStatus(getStatusResourceId(it))
                    .characterOnClick(this@HomeEpoxyController._characterOnClick)
            })
        }
    }
}