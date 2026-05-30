package model;

import java.util.Objects;

/**
 * Aggregates common user authentication data and role information.
 */
public class User {
    private final String userID;
    private final String password;
    private final Role role;

    public User(String userID, String password, Role role) {
        this.userID = requireText(userID, "userID");
        this.password = requireText(password, "password");
        this.role = Objects.requireNonNull(role, "role is required.");
    }

    public String getUserID() {
        return userID;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Authenticates this user against supplied credentials.
     */
    public boolean authenticate(String suppliedUserID, String suppliedPassword) {
        return userID.equalsIgnoreCase(nullSafe(suppliedUserID))
                && password.equals(nullSafe(suppliedPassword));
    }

    public boolean hasRole(Role expectedRole) {
        return role.equals(expectedRole);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }

    private static String nullSafe(String value) {
        return value == null ? "" : value.trim();
    }
}