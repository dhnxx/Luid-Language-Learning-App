package com.example.luid.fragments.mainmenu

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.luid.R
import kotlin.Int

public class TabPhaseReviewDirections private constructor() {
  private data class ActionTabPhaseReviewToStoryFragment(
    public val phase: Int,
    public val level: Int
  ) : NavDirections {
    public override val actionId: Int = R.id.action_tabPhaseReview_to_storyFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("phase", this.phase)
        result.putInt("level", this.level)
        return result
      }
  }

  public companion object {
    public fun actionTabPhaseReviewToStoryFragment(phase: Int, level: Int): NavDirections =
        ActionTabPhaseReviewToStoryFragment(phase, level)
  }
}
