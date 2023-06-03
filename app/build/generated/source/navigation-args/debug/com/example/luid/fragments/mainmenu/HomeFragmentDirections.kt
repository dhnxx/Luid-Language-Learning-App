package com.example.luid.fragments.mainmenu

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.luid.R

public class HomeFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeFragmentToTabPhaseReview(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeFragment_to_tabPhaseReview)
  }
}
