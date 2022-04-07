package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.groep3.pathedeapp.R;

public class FilterOption extends DialogFragment {
    private Integer mode;
    private String input;

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
            case 4:
                inputBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                inputBox.setHint("8.5");
                addFilterButton.setText(R.string.confirm_rating_button);
                break;
            case 5:
                inputBox.setInputType(InputType.TYPE_CLASS_TEXT);
                inputBox.setHint("action");
                break;

        }
//        Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
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
                input = inputBox.getText().toString();
                switch (mode) {
                    case 0:
                        sendInput();
                        break;
                    case 1:
                        sendInput();
                        break;

                    case 2:
                        if (Integer.parseInt(input) > 10){
                            Toast.makeText(getContext(), "Number must be smaller than 11", Toast.LENGTH_SHORT).show();
                            break;
                        }else {
                            sendInput();
                        }
                    case 3:
                        if (input.matches(".*\\d.*")){
                            Toast.makeText(getContext(), "Language cannot contain numbers", Toast.LENGTH_SHORT).show();
                        }else {
                            sendInput();
                        }
                        break;
                    case 4:
                        sendInput();
                        break;
                    case 5:
                        if (input.matches(".*\\d.*")){
                            Toast.makeText(getContext(), "Genre cannot contain numbers", Toast.LENGTH_SHORT).show();
                        }else {
                            sendInput();
                        }
                        break;
                }





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

    private void sendInput(){
        onInputListener.sendInput(input);
        getDialog().dismiss();
    }

}
