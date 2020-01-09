package com.jm.syncdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jm.syncdata.Student.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Student(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = ID)
  var userID: Int? = null,
  @ColumnInfo(name = NAME)
  var nameFUll: String? = null,
  @ColumnInfo(name = AGE)
  var ageTotal: Int? = null
) {
  companion object {
    const val TABLE_NAME = "student"
    const val ID = "id"
    const val NAME = "name"
    const val AGE = "age"
  }
}