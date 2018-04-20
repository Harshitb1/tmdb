package com.example.sagar.moviestvshows;

import java.util.ArrayList;

/**
 * Created by SAGAR on 07-04-2018.
 */

public class MovieCastResponse {

//  class Cast{
//        int cast_id;
//        String character;
//        String credit_id;
//        int gender;
//        int id;
//        String name;
//        int order;
//        String profile_path;
//
//        public int getCast_id() {
//            return cast_id;
//        }
//
//        public void setCast_id(int cast_id) {
//            this.cast_id = cast_id;
//        }
//
//        public String getCharacter() {
//            return character;
//        }
//
//        public void setCharacter(String character) {
//            this.character = character;
//        }
//
//        public String getCredit_id() {
//            return credit_id;
//        }
//
//        public void setCredit_id(String credit_id) {
//            this.credit_id = credit_id;
//        }
//
//        public int getGender() {
//            return gender;
//        }
//
//        public void setGender(int gender) {
//            this.gender = gender;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getOrder() {
//            return order;
//        }
//
//        public void setOrder(int order) {
//            this.order = order;
//        }
//
//        public String getProfile_path() {
//            return profile_path;
//        }
//
//        public void setProfile_path(String profile_path) {
//            this.profile_path = profile_path;
//        }
//    }

    class Crew{
      String credit_id;
      String department;
      int gender;
      int id;
      String job;
      String name;
      String profile_path;
    }

    int id;
    ArrayList<Cast> cast;
    ArrayList<Crew> crew;

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}
