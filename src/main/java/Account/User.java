package Account;

public class User {
    String _username;
    UserType _type;

    public User(String username, UserType type) {
        _username = username;
        _type = type;
    }

    public String getUsername() {
        return _username;
    }

    @Override
    public boolean equals(Object obj) {
        User other = (User)obj;
        return _username.equals(other.getUsername());
    }

    public UserType getType() {
        return _type;
    }
}
