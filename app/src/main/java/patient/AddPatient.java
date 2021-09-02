package patient;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clinic.MainActivity;
import com.example.clinic.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPatient extends AppCompatActivity implements Animation.AnimationListener {

    Spinner dayOfBirth;
    Spinner monthOfBirth;
    Spinner yearOfBirth;
    Animation topToBottom;

    @Override
    public void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.add_patient);
        topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
        topToBottom.setDuration(2000);
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.startAnimation(topToBottom);
        RadioGroup newPatientGender = (RadioGroup) findViewById(R.id.newPatientGender);
        dayOfBirth = findViewById(R.id.newPatientDayOfBirth);
        monthOfBirth = findViewById(R.id.newPatientMonthOfBirth);
        yearOfBirth = findViewById(R.id.newPatientYearOfBirth);
        Spinner newPatientSickness = findViewById(R.id.newPatientSickness);
        FloatingActionButton btnSaveNewPatient = (FloatingActionButton) findViewById(R.id.btnSaveNewPatient);
        Integer[] days = new Integer[31];
        for(int i = 0; i < days.length; i++){
            days[i] = i + 1;
        }
        Integer[] years = new Integer[102];
        for(int i = 0; i < years.length; i++){
            years[i] = i + 1920;
        }

        ArrayAdapter<Integer> daysAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, days);
        dayOfBirth.setAdapter(daysAdapter);

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.months));
        monthOfBirth.setAdapter(monthsAdapter);

        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearOfBirth.setAdapter(yearsAdapter);

        ArrayAdapter<String> sicknessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sicknesses));
        newPatientSickness.setAdapter(sicknessAdapter);

        btnSaveNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPatient.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}