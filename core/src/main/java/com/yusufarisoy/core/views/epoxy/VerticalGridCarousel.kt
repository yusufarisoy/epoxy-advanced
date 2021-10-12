package com.yusufarisoy.core.views.epoxy

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
class VerticalGridCarousel(context: Context) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager =
        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
}