package com.jm.syncdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface TempDao {
  @Query("SELECT * from `temp` ORDER BY id ASC")
  fun getAllTemps(): List<Temp>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(temp: Temp): Completable

  @Query("DELETE FROM `temp`")
  suspend fun deleteAll()

  @Query("SELECT COUNT(${Temp.ID}) FROM `temp`")
  suspend fun getCount(): Int

  @Insert(onConflict = OnConflictStrategy.ABORT)
  fun insertFromWorker(temp: Temp)
}