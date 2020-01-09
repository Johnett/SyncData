package com.jm.syncdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_student.*


class StudentActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_student)

    tvTitle.text = "Add Details"

    toolBarInitialization(toolbar)

    btSubmit.setOnClickListener {
      when {
        etName.text.isNullOrEmpty() -> {
          etName.error = "Please enter a valid name"
        }
        etAge.text.isNullOrEmpty() -> {
          etAge.error = "Please enter a valid age"
        }
        else -> {

        }
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
}
