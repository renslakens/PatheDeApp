package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.groep3.pathedeapp.R;

public class FilterOption extends DialogFragment {
    private Integer mode;

    public FilterOption(Integer mode) {
        this.mode = mode;
    }

    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;
    private EditText inputBox;
    private TextView cancelFilterButton, addFilterButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_box, container, false);

        addFilterButton = view.findViewById(R.id.add_filter_button);
        cancelFilterButton = view.findViewById(R.id.cancel_filter_button);
        inputBox = view.findViewById(R.id.input_box);

        switch (mode) {
            case 0:
                inputBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputBox.setHint("5000");
                break;
            case 1:
                inputBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputBox.setHint("2003");
                break;
            case 2:
                inputBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputBox.setHint("9");
                break;
            case 3:
                inputBox.setInputType(InputType.TYPE_CLASS_TEXT);
                inputBox.setHint("nl");
                break;
        }

        cancelFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "onClick: closing dialog");
                getDialog().dismiss();
            }
        });


        addFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = inputBox.getText().toString();

                onInputListener.sendInput(input);
                getDialog().dismiss();

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("gamermoment", "onAttach: " + e.getMessage());
        }
    }

}
