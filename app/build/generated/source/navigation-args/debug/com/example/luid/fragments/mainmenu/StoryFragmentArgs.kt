package com.example.luid.fragments.mainmenu

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class StoryFragmentArgs(
  public val phase: Int,
  public val level: Int
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("phase", this.phase)
    result.putInt("level", this.level)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("phase", this.phase)
    result.set("level", this.level)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): StoryFragmentArgs {
      bundle.setClassLoader(StoryFragmentArgs::class.java.classLoader)
      val __phase : Int
      if (bundle.containsKey("phase")) {
        __phase = bundle.getInt("phase")
      } else {
        throw IllegalArgumentException("Required argument \"phase\" is missing and does not have an android:defaultValue")
      }
      val __level : Int
      if (bundle.containsKey("level")) {
        __level = bundle.getInt("level")
      } else {
        throw IllegalArgumentException("Required argument \"level\" is missing and does not have an android:defaultValue")
      }
      return StoryFragmentArgs(__phase, __level)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): StoryFragmentArgs {
      val __phase : Int?
      if (savedStateHandle.contains("phase")) {
        __phase = savedStateHandle["phase"]
        if (__phase == null) {
          throw IllegalArgumentException("Argument \"phase\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"phase\" is missing and does not have an android:defaultValue")
      }
      val __level : Int?
      if (savedStateHandle.contains("level")) {
        __level = savedStateHandle["level"]
        if (__level == null) {
          throw IllegalArgumentException("Argument \"level\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"level\" is missing and does not have an android:defaultValue")
      }
      return StoryFragmentArgs(__phase, __level)
    }
  }
}
