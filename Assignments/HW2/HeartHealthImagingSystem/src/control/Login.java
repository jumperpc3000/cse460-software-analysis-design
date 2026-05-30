package control;

import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

/**
 * Control class for authentication and role-based access control.
 * Some build in users are established
 */
public class Login {
    private final List<User> demoUsers = List.of(
            new User("receptionist", "receptionist123", Role.RECEPTIONIST),
            new User("technician", "technician123", Role.CT_SCAN_TECHNICIAN),
            new User("doctor", "doctor123", Role.HEART_SPECIALIST),
            new User("patient", "patient123", Role.PATIENT)
    );

    public Optional<User> authenticateUser(String userID, String password) {
        return demoUsers.stream()
                .filter(user -> user.authenticate(userID, password))
                .findFirst();
    }

    public boolean manageAccessControl(User user, Role requiredRole) {
        return user != null && user.hasRole(requiredRole);
    }
}