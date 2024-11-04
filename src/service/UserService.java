package service;

import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import model.Address;
import model.Users;
import reponsitory.UsersRepository;
import util.Validation;
import view.View;

public class UserService implements IUserService {

    private List<Users> listUserses;
    private Validation check;
    private UsersRepository usersRepository;

    public UserService() {
        check = new Validation();
        usersRepository = new UsersRepository();
        listUserses = usersRepository.readFile();
    }

    public List<Users> getAllUsers() {
        return listUserses;
    }

    @Override
    public void addNewCustomer(Users users) {
        listUserses.add(users);
        usersRepository.writeFile(listUserses);
        JOptionPane.showMessageDialog(null, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void display() {
        System.out.printf("|%-10s|%-25s|%-15s|%-12s|%-8s|%-25s|%-15s|%-36s|%-8s|%-8s|\n",
                "User ID", "User Name", "SSN", "Phone", "Gender", "Email", "Birthday", "Address", "Height", "Weight");
        for (Users users : listUserses) {
            System.out.println(users);
        }
    }

    @Override
    public Users findByID(String id) {
        for (Users users : listUserses) {
            if (users.getId().trim().equalsIgnoreCase(id.trim())) {
                return users;
            }
        }
        return null;
    }

    public Users findByForgotPassword(String id, String email, String cmnd) {
        for (Users user : listUserses) {
            if (user.getId().trim().equalsIgnoreCase(id.trim())) {
                if (user.getEmail().equals(email) && user.getCmnd().equalsIgnoreCase(cmnd)) {
                    return user;
                } else {
                    JOptionPane.showMessageDialog(null, "Email or User SSN no duplicate!!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Account not found!!", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    public void update(Users id) {
        for (int i = 0; i < listUserses.size(); i++) {
            if (listUserses.get(i).getId().equalsIgnoreCase(id.getId())) {
                listUserses.set(i, id);
                new UsersRepository().writeFile(listUserses);
            }
        }
    }

    public void delete(String e) {
        listUserses.removeIf(p -> p.getId().equalsIgnoreCase(e));
        new UsersRepository().writeFile(listUserses);

    }
}
