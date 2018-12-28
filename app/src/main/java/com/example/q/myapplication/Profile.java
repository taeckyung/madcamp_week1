package com.example.q.myapplication;

public class Profile {
    private String _name;
    private String _phone;
    private String _email;

    public Profile(String name, String phone, String email) {
        _name = name;
        _phone = phone;
        _email = email;
    }

    public String getName() {
        return _name;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }
}
