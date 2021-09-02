package off_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pojo.Appointment;
import pojo.Attachment;
import pojo.ClinicInfo;
import pojo.Doctor;
import pojo.Patient;
import pojo.Session;
import pojo.Sickness;

@Database(entities = { Doctor.class, Appointment.class, ClinicInfo.class , Patient.class}
        ,exportSchema = false,version = 1)
public abstract class AdminRoom extends RoomDatabase {


    private static final String DB_NAME = "AdminRoom";
    private static AdminRoom instance;

    public static synchronized AdminRoom getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AdminRoom.class,
                    DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract AdminDao adminDao();
}
