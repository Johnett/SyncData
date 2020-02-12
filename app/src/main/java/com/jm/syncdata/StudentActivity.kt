package com.jm.syncdata

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class StudentActivity : AppCompatActivity() {

  private val viewModel by viewModels<StudentViewModel>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_student)

    tvTitle.text = "Add Details"

//    viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
    val dataBaseInstance = AppDataBase.getDatabaseInstance(this)

    viewModel.studentList.observe(this, Observer {
      if (!it.isNullOrEmpty()) {
        println("status_meta not even here ${it.size}")
        it.forEach { student ->
          println(
            """
                     |::Test scenario running below::
                     |student ID -> ${student.userID.toString()}
                     |student Name -> ${student.nameFUll}
                     |student Age -> ${student.ageTotal.toString()}
                  """.trimMargin()
          )
        }
      }
    })

    viewModel.tempList.observe(this, Observer {
      if (!it.isNullOrEmpty()) {
        println("status_meta not even here ${it.size}")
        it.forEach { student ->
          println(
            """
                     |::Test scenario running for temp below::
                     |student ID -> ${student.userID.toString()}
                     |student Name -> ${student.nameFUll}
                     |student Age -> ${student.ageTotal.toString()}
                  """.trimMargin()
          )
        }
      }
    })

    toolBarInitialization(toolbar)

    btSubmit.setOnClickListener {

      CoroutineScope(Dispatchers.Main).launch {
        when {
          etName.text.isNullOrEmpty() -> {
            etName.error = "Please enter a valid name"
          }
          etAge.text.isNullOrEmpty() -> {
            etAge.error = "Please enter a valid age"
          }
          else -> {
            val student =
              Student(nameFUll = etName?.text.toString(), ageTotal = etAge?.text.toString().toInt())
            viewModel.setInstanceOfDb(dataBaseInstance)
            val count = viewModel.getCount()?.plus(1)
            val studentDetails = workDataOf(
              "name" to student.nameFUll,
              "age" to student.ageTotal
            )
            println("total_count ${viewModel.getCount()}")
            if (isInternetAvailable(baseContext)) {
              viewModel.saveDataIntoDb(student)
              val arg = Temp(
                nameFUll = studentDetails.getString("name"),
                ageTotal = studentDetails.getInt("age", 0)
              )
              viewModel.saveDataIntoTempDb(arg)
              tvAlert.text = getString(R.string.api_success)
              tvAlert.setTextColor(Color.parseColor("#20A222"))
              clear()
            } else {
              val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
              val syncWork = OneTimeWorkRequest.Builder(SyncData::class.java)
                .setConstraints(constraints)
                .setInputData(studentDetails)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 20, TimeUnit.SECONDS)
                .build()

              WorkManager.getInstance(baseContext).enqueue(syncWork)
              clear()
              tvAlert.text = getString(R.string.api_failed)
              tvAlert.setTextColor(Color.parseColor("#FF5252"))
            }
          }
        }
      }
    }

    btDebug.setOnClickListener {
      CoroutineScope(Dispatchers.Main).launch {
        //        viewModel?.setInstanceOfDb(dataBaseInstance)
//        viewModel?.getAllStudentDetails()
        viewModel.setInstanceOfDb(dataBaseInstance)
        viewModel.getAllTempDetails()
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  private fun toolBarInitialization(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeButtonEnabled(true)
    toolbar.setNavigationIcon(R.drawable.ic_back)
    toolbar.setNavigationOnClickListener {
      this.finish()
    }
  }

  private fun clear() {
    etName.text.clear()
    etAge.text.clear()
    etName.requestFocus()
  }

  private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val networkCapabilities = connectivityManager.activeNetwork ?: return false
      val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
      result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
      }
    } else {
      connectivityManager.run {
        connectivityManager.activeNetworkInfo?.run {
          result = when (type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            ConnectivityManager.TYPE_ETHERNET -> true
            else -> false
          }
        }
      }
    }

    return result
  }
}
