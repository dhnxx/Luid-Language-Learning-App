// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.luid.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ParentphasecardBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView childRecyclerView;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final MaterialCardView materialCardView3;

  @NonNull
  public final TextView parentTitle;

  private ParentphasecardBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView childRecyclerView, @NonNull ConstraintLayout constraintLayout,
      @NonNull ImageView imageView2, @NonNull MaterialCardView materialCardView3,
      @NonNull TextView parentTitle) {
    this.rootView = rootView;
    this.childRecyclerView = childRecyclerView;
    this.constraintLayout = constraintLayout;
    this.imageView2 = imageView2;
    this.materialCardView3 = materialCardView3;
    this.parentTitle = parentTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ParentphasecardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ParentphasecardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.parentphasecard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ParentphasecardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.childRecyclerView;
      RecyclerView childRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (childRecyclerView == null) {
        break missingId;
      }

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.materialCardView3;
      MaterialCardView materialCardView3 = ViewBindings.findChildViewById(rootView, id);
      if (materialCardView3 == null) {
        break missingId;
      }

      id = R.id.parentTitle;
      TextView parentTitle = ViewBindings.findChildViewById(rootView, id);
      if (parentTitle == null) {
        break missingId;
      }

      return new ParentphasecardBinding((ConstraintLayout) rootView, childRecyclerView,
          constraintLayout, imageView2, materialCardView3, parentTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
