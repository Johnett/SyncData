package com.jm.syncdata

import android.graphics.Typeface
import android.widget.TextView

fun TextView.setTypeface(){
  this.typeface = Typeface.createFromAsset(context.assets,"font/lato_black.xml")
}