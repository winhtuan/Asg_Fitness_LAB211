package service;

import java.util.List;
import model.Coach;
import reponsitory.CoachReponsitory;

public class CoachService implements ICoachService {

    private List<Coach> listCoachs;

    public CoachService() {
        listCoachs = new CoachReponsitory().readFile();
    }
    
    public List<Coach> getAllCoach(){
        return listCoachs ;
    }
    @Override
    public void add(Coach e) {
        listCoachs.add(e);
        new CoachReponsitory().writeFile(listCoachs);
    }

    @Override
    public void update(Coach id) {
        for (int i = 0; i < listCoachs.size(); i++) {
            if(listCoachs.get(i).getId().equalsIgnoreCase(id.getId())){
                listCoachs.set(i,id);
                new CoachReponsitory().writeFile(listCoachs);
            }
        }    
    }
    @Override
    public void delete(String id) {
        listCoachs.removeIf(p -> p.getId().equalsIgnoreCase(id));
        new CoachReponsitory().writeFile(listCoachs);
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Coach findByID(String id) {
        return listCoachs.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

}
