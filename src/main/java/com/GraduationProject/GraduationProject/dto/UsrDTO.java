package com.GraduationProject.GraduationProject.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsrDTO {

    private String name;
    private String lastName;
    private String username;
    private String password;

    @JsonProperty("isConsentChecked")
    private boolean isConsentChecked;


}