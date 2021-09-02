package pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attachments {

    @PrimaryKey
    @ColumnInfo(name = "attachment_id")
    private int attachment_id;
    @ColumnInfo(name = "name")
    private String name ;
    @ColumnInfo(name = "sessionID")
    private int sessionID;

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(int attachment_id) {
        this.attachment_id = attachment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
