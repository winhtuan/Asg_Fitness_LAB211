package service;

import model.Users;

public interface IUserService extends Service<Users> {

    void addNewCustomer(Users users);
    
}
