package patient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;

public class SicknessesDialog extends DialogFragment {

    private List<String> items;
    private View dialogView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.sicknesses, null);

        Button okBtn = dialogView.findViewById(R.id.okSicknessBtn);
      /*  TextView sickness1 = dialogView.findViewById(R.id.patientSickness1);
        TextView sickness2 = dialogView.findViewById(R.id.patientSickness2);
        TextView sickness3 = dialogView.findViewById(R.id.patientSickness3);
        TextView sickness4 = dialogView.findViewById(R.id.patientSickness4);
        TextView sickness5 = dialogView.findViewById(R.id.patientSickness5);

*/

        if (items !=null){
            for (int i = 0; i < items.size() ; i++) {
                System.out.println(items.get(i)+"  sickness ");
                getTextViewById(i+1).setText(items.get(i));
            }

            for (int i = items.size()+1; i <= 5 ; i++) {

                getTextViewById(i).setVisibility(View.GONE);
            }
        }


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        builder.setView(dialogView).setMessage("Sicknesses");

        return builder.create();
    }

    private TextView getTextViewById(int id){
        switch (id){
            case 1:return  dialogView.findViewById(R.id.patientSickness1);
            case 2:return  dialogView.findViewById(R.id.patientSickness2);
            case 3:return  dialogView.findViewById(R.id.patientSickness3);
            case 4:return  dialogView.findViewById(R.id.patientSickness4);
            case 5:return  dialogView.findViewById(R.id.patientSickness5);
        }
        return null;
    }

    public void setListItems(List<String> items){
        this.items = items;
    }
}
