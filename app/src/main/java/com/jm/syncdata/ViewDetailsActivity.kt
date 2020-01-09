package com.jm.syncdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_view.*


class ViewDetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view)

    tvTitle.text = "View Details"

    toolBarInitialization(toolbar)
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
