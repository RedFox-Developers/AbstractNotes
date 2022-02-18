package dev.redfox.abstractnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var notesDatabase: NotesDatabase? = null


        fun getDatabase(context: Context): NotesDatabase{
            val tempInstance = notesDatabase
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    "notes.db"
                ).build()
                notesDatabase = instance
                return instance
            }
        }
    }
    abstract fun notesDao(): NotesDao
}