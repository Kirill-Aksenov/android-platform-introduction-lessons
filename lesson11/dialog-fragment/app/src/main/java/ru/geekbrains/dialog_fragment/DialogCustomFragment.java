package ru.geekbrains.dialog_fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

public class DialogCustomFragment extends DialogFragment {
    private EditText editText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Подключаем макет
        View view = inflater.inflate(R.layout.dialogfragment_custom, null);
        editText = view.findViewById(R.id.editText);
        // Устанавливаем слушателя
        view.findViewById(R.id.button).setOnClickListener(listener);
        // Запретить выход из диалога, ничего не выбрав
        setCancelable(false);

        Button date = view.findViewById(R.id.button_date);
        date.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener d = ((view1, year, monthOfYear, dayOfMonth) -> {
                Toast.makeText(getContext(), String.format("%d-%d-%d", year, monthOfYear, dayOfMonth),
                        Toast.LENGTH_SHORT).show();
            });

            new DatePickerDialog(getContext(), d, 2021, 6, 30).show();
        });

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Закрываем диалог
            dismiss();
            // Передаём в activity информацию о нажатой кнопке
            String answer = editText.getText().toString();
            ((MainActivity) requireActivity()).onDialogResult(answer);
        }
    };
}