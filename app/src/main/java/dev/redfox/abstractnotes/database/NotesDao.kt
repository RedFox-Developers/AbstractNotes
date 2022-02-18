package dev.redfox.abstractnotes.database

import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes() : List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Notes)

    @Delete
    suspend fun deleteNotes(note: Notes)
}