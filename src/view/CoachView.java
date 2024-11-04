package view;

import java.awt.Component;
import javax.swing.JTextField;
import model.Coach;
import service.CoachService;
import util.Validation;

public class CoachView  implements ICoachView  {

    private CoachService coach;
    private Validation input ;
    public CoachView() {
        coach = new CoachService();
        input = new Validation();
    }

    @Override
    public void display() {
        if (coach.getAllCoach().isEmpty()) {
            System.out.println("coach is empty !!");
        } else {
            System.out.println("List coach : ");
            for (Coach x : coach.getAllCoach()) {
                System.out.println(x);
            }
        }
    }

    public void displayCoachName() {
        if (coach.getAllCoach().isEmpty()) {
            System.out.println("coach is empty !!");
        } else {
            System.out.println("List coach : ");
            for (Coach x : coach.getAllCoach()) {
                System.out.println("CoachID: " + x.getId() + "Coach : " + x.getName() + " - " + x.getSpecialization());
            }
        }
    }

}
