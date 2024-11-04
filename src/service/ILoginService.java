package service;

import model.Login;

public interface ILoginService extends Service<Login>{
    void addAccount(Login login);
    void update(Login e , String password);
}
