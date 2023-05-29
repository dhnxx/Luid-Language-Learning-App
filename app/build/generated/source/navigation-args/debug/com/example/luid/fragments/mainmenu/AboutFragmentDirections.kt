package com.example.luid.fragments.mainmenu

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.luid.R

public class AboutFragmentDirections private constructor() {
  public companion object {
    public fun actionAboutFragmentToAboutLuid2(): NavDirections =
        ActionOnlyNavDirections(R.id.action_aboutFragment_to_about_luid2)

    public fun actionAboutFragmentToAboutQuinta(): NavDirections =
        ActionOnlyNavDirections(R.id.action_aboutFragment_to_about_quinta)
  }
}
