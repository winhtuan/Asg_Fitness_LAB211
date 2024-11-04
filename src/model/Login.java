package model;

public class Login {

    protected String account;
    protected String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login(String account, String password) {
        this.account = account;
        this.password = password;
    }

}
