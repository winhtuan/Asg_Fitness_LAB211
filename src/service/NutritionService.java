
package service;

import java.util.List;
import model.Nutrition;
import reponsitory.NutritionRepository;

public class NutritionService implements INutritionService{
    private List<Nutrition> listNutrition ;

    public NutritionService() {
        listNutrition = new NutritionRepository().readFile();
    }
    
    @Override
    public void create(Nutrition e) {
        listNutrition.add(e);
        new NutritionRepository().writeFile(listNutrition);
    }

    @Override
    public List<Nutrition> getAllNutrition() {
        return listNutrition ;
    }

    @Override
    public void update(Nutrition e) {
        for (int i = 0; i < listNutrition.size(); i++) {
            if(listNutrition.get(i).getNutritionID().equalsIgnoreCase(e.getNutritionID())){
                listNutrition.set(i, e);
            }
        }
    }

    @Override
    public void delete(String e) {
        listNutrition.removeIf(p -> p.getNutritionID().equalsIgnoreCase(e));
        new NutritionRepository().writeFile(listNutrition);
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Nutrition findByID(String id) {
        return listNutrition.stream().filter(p -> p.getNutritionID().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    
}
