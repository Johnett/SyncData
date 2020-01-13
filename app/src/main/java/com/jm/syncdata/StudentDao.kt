package com.jm.syncdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface StudentDao {
  @Query("SELECT * from student ORDER BY id ASC")
  suspend fun getAllStudents(): List<Student>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(student: Student): Completable

  @Query("DELETE FROM student")
  suspend fun deleteAll()

  @Insert(onConflict = OnConflictStrategy.ABORT)
  fun insertFromWorker(student: Student)
}