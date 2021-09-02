package pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Sickness {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "sickness_id")
    private int sickness_id;
    @ColumnInfo(name = "name")
    private String name;

    public int getSickness_id() {
        return sickness_id;
    }

    public void setSickness_id(int sickness_id) {
        this.sickness_id = sickness_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
