package service;

import java.util.Map;
import javax.swing.JOptionPane;
import model.Login;
import reponsitory.LoginReponsitory;

public class LoginService implements ILoginService {

    private Map<Login, String> mapAccount;

    public LoginService() {
        mapAccount = new LoginReponsitory().readFile();
    }

    @Override
    public void addAccount(Login login) {
        mapAccount.put(login, login.getAccount());
        new LoginReponsitory().writeFile(mapAccount);
        JOptionPane.showMessageDialog(null, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void display() {
    }

    public Login findByID(String id, String pass) {
        for (Login x : mapAccount.keySet()) {
            if (x.getAccount().equals(id)) {
                if (x.getPassword().equals(pass)) {
                    return x;
                } 
                
            } 
        }
        return null;
    }

    @Override
    public Login findByID(String id) {
        for (Login x : mapAccount.keySet()) {
            if (x.getAccount().equals(id)) {
                return x;
            }
        }
        return null;
    }
    public void delete(String id) {
    Login loginToDelete = null;
    for (Login login : mapAccount.keySet()) {
        if (login.getAccount().equals(id)) {
            loginToDelete = login;
            break;
        }
    }
    if (loginToDelete != null) {
        mapAccount.remove(loginToDelete);
        new LoginReponsitory().writeFile(mapAccount);
    } 
}
    @Override
    public void update(Login e, String newPassword) {
        boolean accountFound = false;

        for (Login login : mapAccount.keySet()) {
            if (login.getAccount().equals(e.getAccount())) {
                login.setPassword(newPassword);
                mapAccount.put(login, login.getAccount());
                accountFound = true;
                break;
            }
        }
        if (accountFound) {
            new LoginReponsitory().writeFile(mapAccount);
            JOptionPane.showMessageDialog(null, "New password update sucessfully!!", "Sucessfully", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Account not exsit!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
