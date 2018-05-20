package Account;

public interface UserDatabase {
    boolean add(String username, String password, User user);

    User get(String username, String password);
}
