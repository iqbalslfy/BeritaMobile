package com.samsung.muhammad.polisi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yusuf on 25/09/17.
 */

public class LoginResponse {
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("kd_polsek")
    @Expose
    public String kd_polsek;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKd_polsek() {
        return kd_polsek;
    }

    public void setKd_polsek(String kd_polsek) {
        this.kd_polsek = kd_polsek;
    }
}
