package io.legado.app.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "bookmarks",
    indices = [(Index(value = ["bookName", "bookAuthor"], unique = false))]
)
data class Bookmark(
    @PrimaryKey
    val time: Long = System.currentTimeMillis(),
    val bookName: String = "",
    val bookAuthor: String = "",
    var chapterIndex: Int = 0,//第几章节
    var chapterPos: Int = 0,//第几页
    var chapterName: String = "",
    var bookText: String = "",
    var content: String = ""
) : Parcelable

@Parcelize
@Entity(
    tableName = "booknotes",
    indices = [(Index(value = ["bookName", "bookAuthor"], unique = false))]
)
data class Booknote(
    @PrimaryKey
    val time: Long = System.currentTimeMillis(),
    val bookName: String = "",
    val bookAuthor: String = "",
    var chapterIndex: Int = 0,//第几章节
    var chapterPos: Int = 0,//第几页
    var chapterName: String = "",
    var bookText: String = "",
    var content: String = ""//笔记内容
) : Parcelable