
package view;

import java.util.ArrayList;
import java.util.List;
import model.Nutrition;
import service.NutritionService;
import util.Validation;

public class NutritionView implements INutritrionView{
    private NutritionService nutritionService;
    private Validation input ;
    public NutritionView() {
        nutritionService = new NutritionService();
        input = new Validation();
    }
    public void createNutrition(){
        String nutritionID ; 
        while (true) {            
            nutritionID = input.getInputString("Enter nutritionID(format: NUT-YYYY) : ", NUTITION_REGEX, "Invalid nutrition ID. Format must be NUT-YYYY where Y is a digit.");
            if(nutritionService.findByID(nutritionID) == null){
                List<String> nutritionList = new ArrayList<>();
                String nutrition ;
                do {                    
                    nutrition = input.getInputString("Enter nutrition : ", TEXTNORMAL_REGEX, "");
                    nutritionList.add(nutrition);
                } while (input.continueConfirm("you want to add other nutrition "));
                nutritionService.create(new Nutrition(nutritionID, nutritionList));
            }            
        }
    }
    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
