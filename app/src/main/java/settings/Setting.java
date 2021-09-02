package settings;

public class Setting {

    String setting;
    String description;

    public Setting(String setting, String description){
        this.setting = setting;
        this.description = description;
    }

    public String getSetting(){
        return this.setting;
    }

    public String getDescription(){
        return this.description;
    }
}
