package com.example.farm.login;


public class RoleChangeRequest {
    private String role;

    public RoleChangeRequest() {}

    public RoleChangeRequest(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
