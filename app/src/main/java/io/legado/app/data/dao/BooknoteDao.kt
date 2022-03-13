package io.legado.app.data.dao

import androidx.room.*
import io.legado.app.data.entities.Booknote


@Dao
interface BooknoteDao {

    @get:Query("select * from booknotes")
    val all: List<Booknote>

    @Query(
        """select * from booknotes 
        where bookName = :bookName and bookAuthor = :bookAuthor 
        order by chapterIndex"""
    )
    fun getByBook(bookName: String, bookAuthor: String): List<Booknote>

    @Query(
        """SELECT * FROM booknotes 
        where bookName = :bookName and bookAuthor = :bookAuthor 
        and chapterName like '%'||:key||'%' or content like '%'||:key||'%'
        order by chapterIndex"""
    )
    fun search(bookName: String, bookAuthor: String, key: String): List<Booknote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg booknote: Booknote)

    @Update
    fun update(booknote: Booknote)

    @Delete
    fun delete(vararg booknote: Booknote)

}