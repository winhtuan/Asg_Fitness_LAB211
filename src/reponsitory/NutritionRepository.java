package reponsitory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Nutrition;

public class NutritionRepository implements INutritionReponsitory {

    private List<Nutrition> nutritions;
    private final String PATH = new File("src\\data\\nutrition.csv").getAbsolutePath();

    public NutritionRepository() {
        nutritions = new ArrayList<>();
    }

    @Override
public List<Nutrition> readFile() {
    try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                String nutritionID = parts[0].trim();
                List<String> nutriList = Arrays.asList(parts[1].trim().split(";"));
                Nutrition nutrition = new Nutrition(nutritionID, nutriList);
                nutritions.add(nutrition);
            } else {
                System.out.println("Invalid line format: " + line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
    }
    return nutritions;
}


    @Override
    public void writeFile(List<Nutrition> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Nutrition nutrition : entities) {
                String line = nutrition.getNutritionID() + "," + String.join(";", nutrition.getNutriList());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }
}
