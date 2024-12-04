// Generated by view binder compiler. Do not edit!
package com.example.sportstrackerapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.sportstrackerapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogHelpBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView activityVersionNumber;

  @NonNull
  public final TextView authors;

  @NonNull
  public final TextView instructions;

  @NonNull
  public final TextView newsTabInstructions;

  @NonNull
  public final TextView profileTabInstructions;

  @NonNull
  public final TextView scoresTabInstructions;

  @NonNull
  public final TextView standingsTabInstructions;

  private DialogHelpBinding(@NonNull LinearLayout rootView, @NonNull TextView activityVersionNumber,
      @NonNull TextView authors, @NonNull TextView instructions,
      @NonNull TextView newsTabInstructions, @NonNull TextView profileTabInstructions,
      @NonNull TextView scoresTabInstructions, @NonNull TextView standingsTabInstructions) {
    this.rootView = rootView;
    this.activityVersionNumber = activityVersionNumber;
    this.authors = authors;
    this.instructions = instructions;
    this.newsTabInstructions = newsTabInstructions;
    this.profileTabInstructions = profileTabInstructions;
    this.scoresTabInstructions = scoresTabInstructions;
    this.standingsTabInstructions = standingsTabInstructions;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogHelpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogHelpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_help, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogHelpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_version_number;
      TextView activityVersionNumber = ViewBindings.findChildViewById(rootView, id);
      if (activityVersionNumber == null) {
        break missingId;
      }

      id = R.id.authors;
      TextView authors = ViewBindings.findChildViewById(rootView, id);
      if (authors == null) {
        break missingId;
      }

      id = R.id.instructions;
      TextView instructions = ViewBindings.findChildViewById(rootView, id);
      if (instructions == null) {
        break missingId;
      }

      id = R.id.news_tab_instructions;
      TextView newsTabInstructions = ViewBindings.findChildViewById(rootView, id);
      if (newsTabInstructions == null) {
        break missingId;
      }

      id = R.id.profile_tab_instructions;
      TextView profileTabInstructions = ViewBindings.findChildViewById(rootView, id);
      if (profileTabInstructions == null) {
        break missingId;
      }

      id = R.id.scores_tab_instructions;
      TextView scoresTabInstructions = ViewBindings.findChildViewById(rootView, id);
      if (scoresTabInstructions == null) {
        break missingId;
      }

      id = R.id.standings_tab_instructions;
      TextView standingsTabInstructions = ViewBindings.findChildViewById(rootView, id);
      if (standingsTabInstructions == null) {
        break missingId;
      }

      return new DialogHelpBinding((LinearLayout) rootView, activityVersionNumber, authors,
          instructions, newsTabInstructions, profileTabInstructions, scoresTabInstructions,
          standingsTabInstructions);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
