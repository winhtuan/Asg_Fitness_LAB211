package reponsitory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Login;

public class LoginReponsitory implements Reponsitory<Login, Map<Login, String>>{
private final String PATH = new File("src\\data\\login.csv").getAbsolutePath();
    @Override
    public Map<Login, String> readFile() {
        Map<Login, String> loginMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    String account = values[0].trim();
                    String password = values[1].trim();
                    Login login = new Login(account, password);
                    loginMap.put(login, password);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return loginMap;
    }

    @Override
    public void writeFile(Map<Login, String> loginMap) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Map.Entry<Login, String> entry : loginMap.entrySet()) {
                Login login = entry.getKey();
                bw.write(login.getAccount() + "," + login.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }
}

