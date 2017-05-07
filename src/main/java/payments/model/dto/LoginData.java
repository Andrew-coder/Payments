package payments.model.dto;

public class LoginData {
    protected String cellphone;
    protected String password;

    public String getCellphone() {
        return cellphone;
    }

    public String getPassword() {
        return password;
    }

    public LoginData() {}

    public LoginData(String cellphone, String password) {
        this.cellphone = cellphone;
        this.password = password;
    }
}
