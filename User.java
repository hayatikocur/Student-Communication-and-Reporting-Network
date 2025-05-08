
import java.util.Objects;

public abstract class User {
    protected int userId;
    protected String userName;
    protected String userSurname;
    protected String email;
    protected String password;

    public User(int userId, String userName, String userSurname, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.email = email;
        this.password = password;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeName(String newName) {
        this.userName = newName;
    }

    public void changeSurname(String newSurname) {
        this.userSurname = newSurname;
    }

    public abstract String getUserType();

    // Getters & Setters
    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFullName() { return userName + " " + userSurname; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User other)) return false;
        return this.userId == other.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
