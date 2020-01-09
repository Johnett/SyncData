package com.jm.syncdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
  @Query("SELECT * from student ORDER BY id ASC")
  fun getSortedStudents(): List<Student>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(student: Student)

  @Query("DELETE FROM student")
  suspend fun deleteAll()
}