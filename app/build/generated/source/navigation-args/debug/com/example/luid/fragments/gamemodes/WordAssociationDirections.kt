package com.example.luid.fragments.gamemodes

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.luid.R

public class WordAssociationDirections private constructor() {
  public companion object {
    public fun actionWordAssociation2ToResultFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_WordAssociation2_to_resultFragment)
  }
}
