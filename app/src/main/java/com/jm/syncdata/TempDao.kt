package com.jm.syncdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TempDao {
  @Query("SELECT * from `temp` ORDER BY id ASC")
  fun getSortedTemps(): List<Temp>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(temp: Temp)

  @Query("DELETE FROM `temp`")
  suspend fun deleteAll()
}