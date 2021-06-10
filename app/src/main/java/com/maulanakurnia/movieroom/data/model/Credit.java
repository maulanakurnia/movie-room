package com.maulanakurnia.movieroom.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Maulana Kurnia on 6/2/2021
 * Keep Coding & Stay Awesome!
 **/
public class Credit {

    private Integer id;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("cast")
    @Expose
    private ArrayList<Credit.Cast> casts;
    public ArrayList<Credit.Cast> getCasts() {
        return casts;
    }

    public static class Cast {
        private Integer id;

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        private String character, name, profile_path;

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
