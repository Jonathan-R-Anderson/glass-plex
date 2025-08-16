package com.example.glassplex.util

import android.view.KeyEvent

object GlassInput {
  fun isDpadLeft(keyCode: Int) = keyCode == KeyEvent.KEYCODE_DPAD_LEFT
  fun isDpadRight(keyCode: Int) = keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
  fun isDpadUp(keyCode: Int) = keyCode == KeyEvent.KEYCODE_DPAD_UP
  fun isDpadDown(keyCode: Int) = keyCode == KeyEvent.KEYCODE_DPAD_DOWN
  fun isSelect(keyCode: Int) = keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER
}
