package model;

import java.util.Objects;

/**
 * Represents a role and its permission description.
 */
public class Role {
    public static final Role RECEPTIONIST = new Role("RECEPTIONIST", "Patient intake and appointment scheduling");
    public static final Role CT_SCAN_TECHNICIAN = new Role("CT_SCAN_TECHNICIAN", "CT scan result entry");
    public static final Role HEART_SPECIALIST = new Role("HEART_SPECIALIST", "Result review and risk assessment");
    public static final Role PATIENT = new Role("PATIENT", "View own CT scan results");

    private final String roleName;
    private final String permissions;

    public Role(String roleName, String permissions) {
        this.roleName = requireText(roleName, "roleName");
        this.permissions = requireText(permissions, "permissions");
    }

    public String getRoleName() {
        return roleName;
    }

    public String getPermissions() {
        return permissions;
    }

    public boolean isNamed(String expectedRoleName) {
        return roleName.equalsIgnoreCase(expectedRoleName);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Role role)) {
            return false;
        }
        return roleName.equalsIgnoreCase(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName.toUpperCase());
    }
}