package com.project.sidecarhealth.dto;

import java.util.Objects;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String apiKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getApiKey(), that.getApiKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName(), getEmail(), getApiKey());
    }
}
