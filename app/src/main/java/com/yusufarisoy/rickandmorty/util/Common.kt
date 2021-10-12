package com.yusufarisoy.rickandmorty.util

import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.rickandmorty.R

fun getStatusResourceId(character: Character): Int = when (character.status) {
    "Alive" -> R.drawable.ic_character_status_alive
    "Dead" -> R.drawable.ic_character_status_dead
    else -> R.drawable.ic_character_status_unknown
}