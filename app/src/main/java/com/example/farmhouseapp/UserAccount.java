package com.example.farmhouseapp;

import java.util.HashMap;
import java.util.Map;


public class UserAccount implements java.io.Serializable {

    protected String Uid;
    protected String email;
    protected String mobilenum;
    protected String name;
    protected String password;
    protected String role;


    public UserAccount setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserAccount setPhone(String phone) {
        this.mobilenum = phone;
        return this;
    }


    public String getRole() {
        return role;

    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserAccount setUid(String uid) {
        Uid = uid;
        return this;
    }

    public UserAccount setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserAccount setUserName(String userName) {
        this.name = userName;
        return this;
    }

    public String getPhone() {
        return mobilenum;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return Uid;
    }

    public String getUserName() {
        return name;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(6);
        map.put(Utils.UID, Uid);
        map.put(Utils.PHONE, mobilenum);
        map.put(Utils.PASSWORD, password);
        map.put(Utils.EMAIL, email);
        map.put(Utils.USER_NAME, name);
        return map;
    }

    public void clear() {
        Uid = null;
        password = null;
        email = null;

    }

    public String toString() {
        return "[phone " + mobilenum + "]\n" +
                "[password " + password + "]\n" + "[email " + email + "]\n" +
                "[username " + name + "]\n";
    }
}

