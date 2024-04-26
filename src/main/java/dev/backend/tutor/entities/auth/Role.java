package dev.backend.tutor.entities.auth;

public enum Role {
    ROLE_STUDENT("ROLE_STUDENT"),

    ROLE_BANNED("ROLE_BANNED"),
    ROLE_UNACTIVATED("ROLE_UNACTIVATED"),

    ROLE_LOGOUT("ROLE_LOGOUT"),
    ROLE_JWT_REFRESH("ROLE_JWT_REFRESH");




    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}