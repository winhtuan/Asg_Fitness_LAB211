package model;

import java.util.List;

public class Nutrition {

    private String nutritionID;
    private List<String> nutriList;

    public Nutrition(String nutritionID, List<String> nutriList) {
        this.nutritionID = nutritionID;
        this.nutriList = nutriList;
    }

    public String getNutritionID() {
        return nutritionID;
    }

    public List<String> getNutriList() {
        return nutriList;
    }

}
