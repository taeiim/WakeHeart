package com.dsm.wakeheart.Model;

/**
 * Created by parktaeim on 2017. 10. 25..
 */

public class LoginObjectModel {
    private String id;
    private String pw;

    public LoginObjectModel(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
