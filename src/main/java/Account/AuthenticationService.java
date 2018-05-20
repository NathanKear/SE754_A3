package Account;

import java.util.HashMap;
import java.util.UUID;

public class AuthenticationService {
    UserDatabase _db;
    HashMap<User, UUID> sessions = new HashMap<User, UUID>();

    public AuthenticationService(UserDatabase db) {
        _db = db;
    }

    public User signUp(String username, String password, UserType type) {
        User newUser = new User(username, type);
        sessions.put(newUser, UUID.randomUUID());
        _db.add(username, password, newUser);
        return newUser;
    }

    public User login(String username, String password) {
        User user = _db.get(username, password);
        sessions.put(user, UUID.randomUUID());
       return user;
    }

    public boolean logout(User user) {
        sessions.remove(user);
        return true;
    }
}
