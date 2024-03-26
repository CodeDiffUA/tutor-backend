package dev.backend.tutor.entities.auth;

public enum Role {
    ROLE_STUDENT("ROLE_STUDENT");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}