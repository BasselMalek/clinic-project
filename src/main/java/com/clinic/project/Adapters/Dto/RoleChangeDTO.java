package com.clinic.project.Adapters.Dto;


public class RoleChangeDTO {

   
    private String role;


    public RoleChangeDTO() {}

    public RoleChangeDTO(String role) {
        this.role = role;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
