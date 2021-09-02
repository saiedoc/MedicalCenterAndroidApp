package pojo;

import com.google.gson.annotations.SerializedName;

public class UserAccount {


    private int account_id;
    private String password;
    private String username;
    private int role;

    public UserAccount(int userid, String password, String username, int role) {
        this.account_id = userid;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public int getUserid() {
        return account_id;
    }

    public void setUserid(int userid) {
        this.account_id = account_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
