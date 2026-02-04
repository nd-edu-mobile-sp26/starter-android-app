package edu.nd.pmcburne.hello

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * This class functions as a bridge between the application and the "Counter" table
 * in the application SQLite database.
 */
@Dao
interface CounterDao {
    /**
     * Get a Flow of all the counters in the database. This returns a Flow because we want
     * the output of this function to be notified if any of the counters are updated or the
     * list itself changes
     */
    @Query("SELECT * FROM counter")
    fun getAll(): Flow<List<Counter>>

    /**
     * Get a Flow of a single counter in the database via it's ID. This returns a Flow because
     * we want the output of this function to be notified if the counter is updated.
     */
    @Query("SELECT * FROM counter WHERE uid = :counterId")
    fun getById(counterId: Long): Flow<Counter?>

    /**
     * Inserts a new counter into the database. This will automatically assign the counter
     * a unique ID.
     */
    @Insert
    suspend fun insertCounter(counter: Counter)

    /**
     * Updates an existing counter in the database.
     */
    @Update
    suspend fun updateCounter(counter: Counter)

    /**
     * Deletes an existing counter from the database.
     */
    @Delete
    suspend fun deleteCounter(counter: Counter)
}