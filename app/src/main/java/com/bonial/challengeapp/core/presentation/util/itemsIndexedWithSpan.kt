package com.bonial.challengeapp.core.presentation.util

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable

inline fun <T> LazyGridScope.itemsIndexedWithSpan(
    items: List<T>,
    noinline key: ((index: Int, item: T) -> Any) = { index, _ -> index },
    noinline span: (index: Int, item: T) -> GridItemSpan = { _, _ -> GridItemSpan(1) },
    crossinline itemContent: @Composable (index: Int, item: T) -> Unit
) {
    items(
        count = items.size,
        key = { index -> key(index, items[index]) },
        span = { index -> span(index, items[index]) }
    ) { index ->
        itemContent(index, items[index])
    }
}