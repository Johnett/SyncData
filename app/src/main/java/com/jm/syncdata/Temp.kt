package com.jm.syncdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jm.syncdata.Temp.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Temp(
  @PrimaryKey
  @ColumnInfo(name = ID)
  var userID: Int? = null,
  @ColumnInfo(name = NAME)
  var nameFUll: String? = null,
  @ColumnInfo(name = AGE)
  var ageTotal: Int? = null
) {
  companion object {
    const val TABLE_NAME = "temp"
    const val ID = "id"
    const val NAME = "name"
    const val AGE = "age"
  }
}