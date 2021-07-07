package ru.geekbrains.dialog_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogBuilderFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_dialog)
                .setView(view)
                .setPositiveButton(R.string.done, (d, i) -> {
                    EditText editText = view.findViewById(R.id.editText);
                    String answer = editText.getText().toString();
                    dismiss();
                    ((MainActivity) requireActivity()).onDialogResult(answer);
                });
        return builder.create();
    }
}
