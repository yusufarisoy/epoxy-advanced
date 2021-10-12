package com.yusufarisoy.core.utils

import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener : RecyclerView.OnScrollListener() {

    private var loading = false

    abstract val hasNextPage: Boolean

    fun setLoading(isLoading: Boolean) {
        this.loading = isLoading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (canLoadMore(recyclerView)) {
            loading = true
            loadMoreItems()
        }
    }

    private fun canLoadMore(recyclerView: RecyclerView): Boolean = hasNextPage && loading.not() &&
            !recyclerView.canScrollVertically(1)

    protected abstract fun loadMoreItems()
}