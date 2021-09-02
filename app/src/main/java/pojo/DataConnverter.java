package pojo;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class DataConnverter implements Serializable {

    @TypeConverter
    public String fromSpecList(List<Specialization> specializationList){

        if(specializationList == null)
            return null;

        Type type = new TypeToken<List<Specialization>>(){}.getType();
        Gson gson = new Gson();

        String json  = gson.toJson(specializationList,type);
        return json;
    }


    @TypeConverter
    public List<Specialization> toSpecList(String sspecList){



        if(sspecList == null)
            return null;

        Type type = new TypeToken<List<Specialization>>(){}.getType();
        Gson gson = new Gson();

        List<Specialization> specializationList = gson.fromJson(sspecList,type);
        return specializationList;

    }



    @TypeConverter
    public String fromSickList(List<String> sickList){

        if(sickList == null)
            return null;

        Type type = new TypeToken<List<String>>(){}.getType();
        Gson gson = new Gson();

        String json  = gson.toJson(sickList,type);
        return json;
    }


    @TypeConverter
    public List<String> toSickList(String ssickList){



        if(ssickList == null)
            return null;

        Type type = new TypeToken<List<String>>(){}.getType();
        Gson gson = new Gson();

        List<String> sickList = gson.fromJson(ssickList,type);
        return sickList;

    }


    @TypeConverter
    public String fromDate(Date date){

        String sdate = date.toString();
        return sdate;
    }





}
