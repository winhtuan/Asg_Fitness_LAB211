package service;

import java.util.List;
import model.Nutrition;

public interface INutritionService extends Service<Nutrition> {

    public void create(Nutrition e);

    public List<Nutrition> getAllNutrition();

    public void update(Nutrition e);

    public void delete(String e);
}
