package dev.redfox.abstractnotes.database

import androidx.room.*

@Dao
interface NotesDao {

    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(note: Notes)

    @Delete
    fun deleteNotes(note: Notes)
}