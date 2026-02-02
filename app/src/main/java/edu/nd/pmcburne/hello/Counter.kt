package edu.nd.pmcburne.hello

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents the data of a single instance of a "Counter" in our application
 */

@Entity
data class Counter(
    /** text to identify what is being counted */
    val name: String,

    /** the current value of the counter */
    val value: Int = 0,

    /** the unique ID of the counter - generated automatically by the database */
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,
)