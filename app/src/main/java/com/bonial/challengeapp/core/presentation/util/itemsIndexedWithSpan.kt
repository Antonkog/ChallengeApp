package com.bonial.challengeapp.core.presentation.util

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable

/**
 * Extension function for LazyGridScope that adds items with custom span sizes to a LazyGrid.
 * This function provides access to both the index and item when defining keys, spans, and content.
 *
 * @param T The type of items in the list.
 * @param items The list of items to display in the grid.
 * @param key A function that returns a unique key for an item at the given index. Default is the index itself.
 * @param span A function that returns the span size for an item at the given index. Default is 1.
 * @param itemContent A composable function that defines the content for each item.
 */
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