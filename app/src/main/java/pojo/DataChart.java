package pojo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class DataChart {


    @ColumnInfo(name = "analysesRate_id")
    int analysesRate_id;
    @ColumnInfo(name = "session_date")
    private String session_date;
    @ColumnInfo(name = "blood_pressure_low")
    private float blood_pressure_low;
    @ColumnInfo(name = "blood_pressure_high")
    private float blood_pressure_high;
    @ColumnInfo(name = "blood_glucose")
    private float blood_glocuse;
    @ColumnInfo(name = "oxgenation")
    private float oxygenation;

    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }

    public int getAnalysesRate_id() {
        return analysesRate_id;
    }

    public void setAnalysesRate_id(int analysesRate_id) {
        this.analysesRate_id = analysesRate_id;
    }

    public float getBlood_pressure_low() {
        return blood_pressure_low;
    }

    public void setBlood_pressure_low(float blood_pressure_low) {
        this.blood_pressure_low = blood_pressure_low;
    }

    public float getBlood_pressure_high() {
        return blood_pressure_high;
    }

    public void setBlood_pressure_high(float blood_pressure_high) {
        this.blood_pressure_high = blood_pressure_high;
    }

    public float getBlood_glocuse() {
        return blood_glocuse;
    }

    public void setBlood_glocuse(float blood_glocuse) {
        this.blood_glocuse = blood_glocuse;
    }

    public float getOxygenation() {
        return oxygenation;
    }

    public void setOxygenation(float oxygenation) {
        this.oxygenation = oxygenation;
    }
}
