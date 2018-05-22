package Account;

import java.util.List;

public class UserManager {
    private AuthenticationService _auth;
    private UserDatabase _db;

    public UserManager(UserDatabase db) {
        _db = db;
        _auth = new AuthenticationService(db);
    }
    public int getRegisteredUserCount(User requestingUser) {
        List<User> users = _db.getAll();
        return users.size();
    }
}
