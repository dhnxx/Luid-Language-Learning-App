// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.luid.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AchvcardviewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialCardView achvcardview;

  @NonNull
  public final TextView achvcurrlvl;

  @NonNull
  public final TextView achvdesc;

  @NonNull
  public final ProgressBar achvprogressBar;

  @NonNull
  public final TextView achvprogresstxt;

  @NonNull
  public final TextView achvtitle;

  @NonNull
  public final ConstraintLayout frameLayout2;

  @NonNull
  public final ImageView imageView5;

  private AchvcardviewBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialCardView achvcardview, @NonNull TextView achvcurrlvl,
      @NonNull TextView achvdesc, @NonNull ProgressBar achvprogressBar,
      @NonNull TextView achvprogresstxt, @NonNull TextView achvtitle,
      @NonNull ConstraintLayout frameLayout2, @NonNull ImageView imageView5) {
    this.rootView = rootView;
    this.achvcardview = achvcardview;
    this.achvcurrlvl = achvcurrlvl;
    this.achvdesc = achvdesc;
    this.achvprogressBar = achvprogressBar;
    this.achvprogresstxt = achvprogresstxt;
    this.achvtitle = achvtitle;
    this.frameLayout2 = frameLayout2;
    this.imageView5 = imageView5;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AchvcardviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AchvcardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.achvcardview, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AchvcardviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.achvcardview;
      MaterialCardView achvcardview = ViewBindings.findChildViewById(rootView, id);
      if (achvcardview == null) {
        break missingId;
      }

      id = R.id.achvcurrlvl;
      TextView achvcurrlvl = ViewBindings.findChildViewById(rootView, id);
      if (achvcurrlvl == null) {
        break missingId;
      }

      id = R.id.achvdesc;
      TextView achvdesc = ViewBindings.findChildViewById(rootView, id);
      if (achvdesc == null) {
        break missingId;
      }

      id = R.id.achvprogressBar;
      ProgressBar achvprogressBar = ViewBindings.findChildViewById(rootView, id);
      if (achvprogressBar == null) {
        break missingId;
      }

      id = R.id.achvprogresstxt;
      TextView achvprogresstxt = ViewBindings.findChildViewById(rootView, id);
      if (achvprogresstxt == null) {
        break missingId;
      }

      id = R.id.achvtitle;
      TextView achvtitle = ViewBindings.findChildViewById(rootView, id);
      if (achvtitle == null) {
        break missingId;
      }

      ConstraintLayout frameLayout2 = (ConstraintLayout) rootView;

      id = R.id.imageView5;
      ImageView imageView5 = ViewBindings.findChildViewById(rootView, id);
      if (imageView5 == null) {
        break missingId;
      }

      return new AchvcardviewBinding((ConstraintLayout) rootView, achvcardview, achvcurrlvl,
          achvdesc, achvprogressBar, achvprogresstxt, achvtitle, frameLayout2, imageView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}