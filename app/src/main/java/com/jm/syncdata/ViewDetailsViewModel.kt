package com.jm.syncdata

import androidx.lifecycle.ViewModel

class ViewDetailsViewModel : ViewModel() {
  private var dataBaseInstance: AppDataBase? = null

  fun setInstanceOfDb(dataBaseInstance: AppDataBase) {
    this.dataBaseInstance = dataBaseInstance
  }

  fun getAllRecords(): List<Temp> {
    val count = dataBaseInstance?.tempDao()?.getCount()
    println("countprint $count")
    val list = dataBaseInstance?.tempDao()?.getAllTemps()
    dataBaseInstance = null
    return list!!
  }
}