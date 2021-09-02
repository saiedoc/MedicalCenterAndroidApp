package settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.clinic.MainActivity;
import com.example.clinic.R;

public class ChangeContactInfo extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_contact_info, null);
        EditText newPhoneNumber = (EditText) dialogView.findViewById(R.id.newPhoneNumber);
        EditText newEmailAddress = (EditText) dialogView.findViewById(R.id.newEmailAddress);
        Button saveButton = (Button) dialogView.findViewById(R.id.saveProfileInfoButton);
        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelProfileInfoButton);
        builder.setView(dialogView).setMessage("Update Contact Info");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dismiss();
            }
        });

        //((MainActivity)getActivity()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return builder.create();
    }
}
