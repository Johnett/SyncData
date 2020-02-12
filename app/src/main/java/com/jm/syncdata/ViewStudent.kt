package com.jm.syncdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewStudent : AppCompatActivity() {

  private val viewModel by viewModels<ViewStudentViewModel>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_student)

    tvTitle.text = "View Temp Details"

    toolBarInitialization(toolbar)

    val dataBaseInstance = AppDataBase.getDatabaseInstance(this)
    CoroutineScope(Dispatchers.Default).launch {
      viewModel.setInstanceOfDb(dataBaseInstance)
      val list = viewModel.getAllRecords()
      addViewToScroll(list)
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

  private fun addViewToScroll(list: List<Student>?) {
//    scrollerContainer.removeAllViews()
    CoroutineScope(Dispatchers.Main).launch {
      var counter = 0
      for (item in list!!) {
        val frame = layoutInflater.inflate(R.layout.item_layout, scrollerContainer, false)
        frame.tvName.text = item.nameFUll
        frame.tvID.text = item.userID.toString()
        frame.tvAge.text = item.ageTotal.toString()
        scrollerContainer.addView(frame)
        counter += 1
        tvCounter.text = counter.toString()
        loadProgress.progress = ((counter.toDouble() / list.size) * 100).toInt()
        delay(200)
      }
    }
  }
}
