package com.maulanakurnia.movieroom.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fullname;
    private String nickname;
    private String username;
    private String password;
    private String image_path;

    public boolean isPasswordLengthGreaterThan3() {
        return getPassword().length() > 3;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
