// Generated by view binder compiler. Do not edit!
package com.example.luid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.luid.R;
import com.google.android.flexbox.FlexboxLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SentencecontructionViewBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final EditText answerLabel;

  @NonNull
  public final Button clearButton;

  @NonNull
  public final ConstraintLayout constraintLayout4;

  @NonNull
  public final FlexboxLayout flexboxLayout;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView question;

  @NonNull
  public final ImageView questionImage;

  @NonNull
  public final Button submitButton;

  @NonNull
  public final LinearLayout toolbar;

  private SentencecontructionViewBinding(@NonNull ScrollView rootView,
      @NonNull EditText answerLabel, @NonNull Button clearButton,
      @NonNull ConstraintLayout constraintLayout4, @NonNull FlexboxLayout flexboxLayout,
      @NonNull ProgressBar progressBar, @NonNull TextView question,
      @NonNull ImageView questionImage, @NonNull Button submitButton,
      @NonNull LinearLayout toolbar) {
    this.rootView = rootView;
    this.answerLabel = answerLabel;
    this.clearButton = clearButton;
    this.constraintLayout4 = constraintLayout4;
    this.flexboxLayout = flexboxLayout;
    this.progressBar = progressBar;
    this.question = question;
    this.questionImage = questionImage;
    this.submitButton = submitButton;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static SentencecontructionViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SentencecontructionViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.sentencecontruction_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SentencecontructionViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.answerLabel;
      EditText answerLabel = ViewBindings.findChildViewById(rootView, id);
      if (answerLabel == null) {
        break missingId;
      }

      id = R.id.clearButton;
      Button clearButton = ViewBindings.findChildViewById(rootView, id);
      if (clearButton == null) {
        break missingId;
      }

      id = R.id.constraintLayout4;
      ConstraintLayout constraintLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout4 == null) {
        break missingId;
      }

      id = R.id.flexboxLayout;
      FlexboxLayout flexboxLayout = ViewBindings.findChildViewById(rootView, id);
      if (flexboxLayout == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.question;
      TextView question = ViewBindings.findChildViewById(rootView, id);
      if (question == null) {
        break missingId;
      }

      id = R.id.questionImage;
      ImageView questionImage = ViewBindings.findChildViewById(rootView, id);
      if (questionImage == null) {
        break missingId;
      }

      id = R.id.submitButton;
      Button submitButton = ViewBindings.findChildViewById(rootView, id);
      if (submitButton == null) {
        break missingId;
      }

      id = R.id.toolbar;
      LinearLayout toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new SentencecontructionViewBinding((ScrollView) rootView, answerLabel, clearButton,
          constraintLayout4, flexboxLayout, progressBar, question, questionImage, submitButton,
          toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
