// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.example.luid.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTabPhaseReviewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout frameLayout2;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final ViewPager2 viewPager2;

  private FragmentTabPhaseReviewBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout frameLayout2, @NonNull TabLayout tabLayout,
      @NonNull ViewPager2 viewPager2) {
    this.rootView = rootView;
    this.frameLayout2 = frameLayout2;
    this.tabLayout = tabLayout;
    this.viewPager2 = viewPager2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTabPhaseReviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTabPhaseReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_tab_phase_review, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTabPhaseReviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout frameLayout2 = (ConstraintLayout) rootView;

      id = R.id.tabLayout;
      TabLayout tabLayout = ViewBindings.findChildViewById(rootView, id);
      if (tabLayout == null) {
        break missingId;
      }

      id = R.id.viewPager2;
      ViewPager2 viewPager2 = ViewBindings.findChildViewById(rootView, id);
      if (viewPager2 == null) {
        break missingId;
      }

      return new FragmentTabPhaseReviewBinding((ConstraintLayout) rootView, frameLayout2, tabLayout,
          viewPager2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
