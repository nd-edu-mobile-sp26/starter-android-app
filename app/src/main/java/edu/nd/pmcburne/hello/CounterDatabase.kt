package edu.nd.pmcburne.hello

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Counter::class],
    version = 1
)
abstract class CounterDatabase: RoomDatabase() {
    abstract fun counterDao(): CounterDao

    /**
     * Below is a singleton design pattern to ensure only one instance of the database is created
     */
    companion object {
        private var instance: CounterDatabase? = null

        fun getDatabase(context: Context): CounterDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, // the application context
                    CounterDatabase::class.java, // the datatype of our database class
                    "counter_database.db" // the SQLite filename
                ).build().also { instance = it }
            }
        }
    }
}