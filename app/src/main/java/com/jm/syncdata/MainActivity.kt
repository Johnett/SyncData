package com.jm.syncdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tvTitle.text = "DashBoard"

    btAdd.setOnClickListener {
      val addIntent = Intent(this,StudentActivity::class.java)
      startActivity(addIntent)
    }

    btView.setOnClickListener {
      val viewIntent = Intent(this,ViewDetailsActivity::class.java)
      startActivity(viewIntent)
    }

    btViewStudent.setOnClickListener {
      val studentIntent = Intent(this,ViewStudent::class.java)
      startActivity(studentIntent)
    }
  }
}
