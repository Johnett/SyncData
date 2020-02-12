package com.jm.syncdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class StudentViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  private var dataBaseInstance: AppDataBase? = null
  val studentList = MutableLiveData<List<Student>>()
  val tempList = MutableLiveData<List<Temp>>()

  fun setInstanceOfDb(dataBaseInstance: AppDataBase) {
    this.dataBaseInstance = dataBaseInstance
  }

  fun saveDataIntoDb(student: Student): Student {
    CoroutineScope(Dispatchers.Default).launch {
      dataBaseInstance?.studentDao()?.insert(student)
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe({
          println("init_log action successful")
        }, {
          println("init_log action throw error  ")
        })?.let {
          compositeDisposable.add(it)
          dataBaseInstance = null
          println("init_log action is disposed ${it.isDisposed}")
        }
    }
    return student
  }

  fun saveDataIntoTempDb(temp: Temp) {
    CoroutineScope(Dispatchers.Default).launch {
      dataBaseInstance?.tempDao()?.insert(temp)
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe({
          println("init_log action successful")
        }, {
          println("init_log action throw error  ")
        })?.let {
          compositeDisposable.add(it)
          dataBaseInstance = null
          println("init_log action is disposed ${it.isDisposed}")
        }
    }
  }

  suspend fun getCount(): Int? = withContext(Dispatchers.Default) {
    try {
      dataBaseInstance?.tempDao()?.getCount() //slow
    } catch (e: java.lang.Exception) {
      null
    }
  }

  fun getAllStudentDetails() {
    CoroutineScope(Dispatchers.Default).launch {
      studentList.postValue(dataBaseInstance?.studentDao()?.getAllStudents())
      dataBaseInstance = null
    }
  }

  fun getAllTempDetails() {
    CoroutineScope(Dispatchers.Default).launch {
      tempList.postValue(dataBaseInstance?.tempDao()?.getAllTemps())
      dataBaseInstance = null
    }
  }

}