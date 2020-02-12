package com.jm.syncdata

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class SyncData(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
  private val appContext = applicationContext
  private val dataBaseInstance = AppDataBase.getDatabaseInstance(appContext)
  private val compositeDisposable = CompositeDisposable()
  lateinit var viewModel:StudentViewModel
  override fun doWork(): Result {
    TriggerNotification(appContext, "Sync Data", "${inputData.getString("name")}'s details pushed to server successfully.")
    return try {
      println(
        "wholeParameterTrail ${inputData.getString("name")}  ${inputData.getInt("age",0)}"
      )
      val arg = Temp(
        nameFUll = inputData.getString("name"),
        ageTotal = inputData.getInt("age", 0)
      )
      dataBaseInstance.tempDao().insertFromWorker(arg)
      println("resultStatus true")
      Result.success()
    } catch (throwable: Throwable) {
      println("resultStatus false")
      Result.failure()
    }
  }

  private fun saveDataIntoTempDb(data: Temp) {
    CoroutineScope(Dispatchers.Main).launch {
      dataBaseInstance.tempDao().insert(data)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
        }, {
          println("normalDataPrint throw ${it.localizedMessage}")
        }).let {
          println("normalDataPrint dispo ${it.isDisposed}")
          compositeDisposable.add(it)
        }
    }
  }
}