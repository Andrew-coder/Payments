package payments.model.dto;

public class LoginData {
    protected String email;
    protected String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginData() {}

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
