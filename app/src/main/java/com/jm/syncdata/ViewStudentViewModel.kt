package com.jm.syncdata

import androidx.lifecycle.ViewModel

class ViewStudentViewModel: ViewModel() {
  private var dataBaseInstance: AppDataBase? = null

  fun setInstanceOfDb(dataBaseInstance: AppDataBase) {
    this.dataBaseInstance = dataBaseInstance
  }

  fun getAllRecords(): List<Student> {
    val count = dataBaseInstance?.studentDao()?.getCount()
    println("countprint $count")
    val list = dataBaseInstance?.studentDao()?.getAllStudents()
    dataBaseInstance = null
    return list!!
  }
}