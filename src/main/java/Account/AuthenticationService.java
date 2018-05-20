package Account;

public class AuthenticationService {
    UserDatabase _db;

    public AuthenticationService(UserDatabase db) {
        _db = db;
    }

    public User signUp(String username, String password, UserType type) {
        User newUser = new User(username, type);
        _db.add(username, password, newUser);
        return newUser;
    }

    public User login(String username, String password) {
       return _db.get(username, password);
    }
}
