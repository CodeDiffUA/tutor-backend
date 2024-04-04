package dev.backend.tutor.entities.auth;

public enum Role {
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_BANNED_STUDENT("ROLE_BANNED_STUDENT"),
    ROLE_UNACTIVATED_STUDENT("ROLE_UNACTIVATED_STUDENT"),
    ROLE_ACTIVATED_STUDENT("ROLE_ACTIVATED_STUDENT");



    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}