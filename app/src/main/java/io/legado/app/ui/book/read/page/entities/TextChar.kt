package io.legado.app.ui.book.read.page.entities

/**
 * 每个字实体类
 */
data class TextChar(
    val charData: String,
    var start: Float,
    var end: Float,
    var selected: Boolean = false,
    var lineSelected:Boolean = false,
    var isImage: Boolean = false,
    var isSearchResult: Boolean = false
) {

    fun isTouch(x: Float): Boolean {
        return x > start && x < end
    }

}