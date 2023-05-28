// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.luid.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAboutBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageView aboutLuid;

  @NonNull
  public final ImageView aboutQuinta;

  @NonNull
  public final MaterialCardView cardLuid;

  @NonNull
  public final MaterialCardView cardQuinta;

  private FragmentAboutBinding(@NonNull FrameLayout rootView, @NonNull ImageView aboutLuid,
      @NonNull ImageView aboutQuinta, @NonNull MaterialCardView cardLuid,
      @NonNull MaterialCardView cardQuinta) {
    this.rootView = rootView;
    this.aboutLuid = aboutLuid;
    this.aboutQuinta = aboutQuinta;
    this.cardLuid = cardLuid;
    this.cardQuinta = cardQuinta;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAboutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAboutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_about, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAboutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_Luid;
      ImageView aboutLuid = ViewBindings.findChildViewById(rootView, id);
      if (aboutLuid == null) {
        break missingId;
      }

      id = R.id.aboutQuinta;
      ImageView aboutQuinta = ViewBindings.findChildViewById(rootView, id);
      if (aboutQuinta == null) {
        break missingId;
      }

      id = R.id.card_luid;
      MaterialCardView cardLuid = ViewBindings.findChildViewById(rootView, id);
      if (cardLuid == null) {
        break missingId;
      }

      id = R.id.card_quinta;
      MaterialCardView cardQuinta = ViewBindings.findChildViewById(rootView, id);
      if (cardQuinta == null) {
        break missingId;
      }

      return new FragmentAboutBinding((FrameLayout) rootView, aboutLuid, aboutQuinta, cardLuid,
          cardQuinta);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
