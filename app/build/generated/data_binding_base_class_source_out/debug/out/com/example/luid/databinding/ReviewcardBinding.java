// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.luid.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ReviewcardBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final MaterialCardView card;

  @NonNull
  public final TextView engWord;

  @NonNull
  public final ImageView indicator;

  @NonNull
  public final TextView kapWord;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView tagWord;

  @NonNull
  public final LinearLayout translation;

  @NonNull
  public final View view;

  private ReviewcardBinding(@NonNull MaterialCardView rootView, @NonNull MaterialCardView card,
      @NonNull TextView engWord, @NonNull ImageView indicator, @NonNull TextView kapWord,
      @NonNull LinearLayout linearLayout, @NonNull TextView tagWord,
      @NonNull LinearLayout translation, @NonNull View view) {
    this.rootView = rootView;
    this.card = card;
    this.engWord = engWord;
    this.indicator = indicator;
    this.kapWord = kapWord;
    this.linearLayout = linearLayout;
    this.tagWord = tagWord;
    this.translation = translation;
    this.view = view;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ReviewcardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ReviewcardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.reviewcard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ReviewcardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      MaterialCardView card = (MaterialCardView) rootView;

      id = R.id.engWord;
      TextView engWord = ViewBindings.findChildViewById(rootView, id);
      if (engWord == null) {
        break missingId;
      }

      id = R.id.indicator;
      ImageView indicator = ViewBindings.findChildViewById(rootView, id);
      if (indicator == null) {
        break missingId;
      }

      id = R.id.kapWord;
      TextView kapWord = ViewBindings.findChildViewById(rootView, id);
      if (kapWord == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.tagWord;
      TextView tagWord = ViewBindings.findChildViewById(rootView, id);
      if (tagWord == null) {
        break missingId;
      }

      id = R.id.translation;
      LinearLayout translation = ViewBindings.findChildViewById(rootView, id);
      if (translation == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      return new ReviewcardBinding((MaterialCardView) rootView, card, engWord, indicator, kapWord,
          linearLayout, tagWord, translation, view);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}