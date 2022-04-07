package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.groep3.pathedeapp.R;

public class ChooseFilterDialog extends DialogFragment implements FilterOption.OnInputListener {
    private Integer mode;
    private final String TAG = MainActivity.class.getSimpleName();

    public ChooseFilterDialog(Integer mode) {
        this.mode = mode;
    }

    @Override
    public void sendInput(String input) {
    }


    public interface OnInputListenerDialog {
        void sendInput(Integer mode);
    }

    public ChooseFilterDialog.OnInputListenerDialog onInputListenerDialog;

    private TextView voteCountButton, releaseYearButton, voteAverageButton, withPeopleButton, clearFilterButton, doneButton, genreButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_filter_dialog, container, false);

        voteCountButton = view.findViewById(R.id.vote_count);
        releaseYearButton = view.findViewById(R.id.release_year);
        voteAverageButton = view.findViewById(R.id.vote_average);
        withPeopleButton = view.findViewById(R.id.original_language);
        doneButton = view.findViewById(R.id.add_button_filter_dialog);
        clearFilterButton = view.findViewById(R.id.clear_filters_button);
        genreButton = view.findViewById(R.id.genre_filter);

        voteCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = 0;
                onInputListenerDialog.sendInput(mode);
                DialogFragment newFragment = new FilterOption(mode);
                newFragment.show(getChildFragmentManager(), "filter");

            }
        });

        releaseYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = 1;
                onInputListenerDialog.sendInput(mode);
                DialogFragment newFragment = new FilterOption(mode);
                newFragment.show(getChildFragmentManager(), "filter");

            }
        });

        voteAverageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = 2;
                onInputListenerDialog.sendInput(mode);
                DialogFragment newFragment = new FilterOption(mode);
                newFragment.show(getChildFragmentManager(), "filter");

            }
        });

        withPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = 3;
                onInputListenerDialog.sendInput(mode);
                DialogFragment newFragment = new FilterOption(mode);
                newFragment.show(getChildFragmentManager(), "filter");

            }
        });

        genreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = 5;
                onInputListenerDialog.sendInput(mode);
                DialogFragment newFragment = new FilterOption(mode);
                newFragment.show(getChildFragmentManager(), "filter");

            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 9;
                onInputListenerDialog.sendInput(mode);

            }
        });
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListenerDialog = (ChooseFilterDialog.OnInputListenerDialog) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e.getMessage());
        }
    }


}
