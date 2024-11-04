package util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Validation {

    private final String DATE_REGEX = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(\\d{4})$";
    private final String TIME_24_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private Scanner sc = new Scanner(System.in);

    public void getMsg(String s) {
        System.out.println(s);
    }

    public <T> T getValue(String msg, Class<T> type) {
        while (true) {
            try {
                System.out.print(msg);
                String input = sc.nextLine().trim();

                double numberValue = Double.parseDouble(input);
                if (numberValue <= 0) {
                    throw new IllegalArgumentException("Error: Please enter a positive number");
                }
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input can't be empty!");
                }
                if (input.matches(".*[a-zA-Z].*")) {
                    throw new IllegalArgumentException("Error: Input can't contain characters!");
                }
                if (!input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                    throw new IllegalArgumentException("Error: Input must be a valid number!");
                }

                if (type == Integer.class) {
                    int intValue = (int) numberValue;
                    if (numberValue != intValue) {
                        throw new IllegalArgumentException("Error: Please enter an integer number!");
                    }
                    return type.cast(intValue);

                } else if (type == Double.class) {
                    return type.cast(numberValue);

                } else {
                    throw new IllegalArgumentException("Error: Unsupported type!");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getInputString(String msg, String regex, String inputName) {

        while (true) {
            try {
                System.out.print(msg);
                String value = sc.nextLine().trim();
                if (value.isEmpty()) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't be null or empty!");
                }
                if (value.matches(regex)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Error: " + inputName + " contains invalid characters!");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double checkDoubleInputFromJFRAM(Component parentComponent, String input, String inputName) {
        try {
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, inputName + " can't be null.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (input.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(parentComponent, inputName + " can't be contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            double value = Double.parseDouble(input);
            return value;
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println(e);
        }
        return 0;
    }
public int checkIntInputFromJFRAM(Component parentComponent, String input, String inputName) {
        try {
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, inputName + " can't be null.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (input.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(parentComponent, inputName + " can't be contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            int value = Integer.parseInt(input);
            return value;
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println(e);
        }
        return 0;
    }
    public String checkStringInputFormJFRAME(Component parentComponent, String input, String regex) {
        if (input.matches(regex)) {
            return input;
        } else {
            return null;
        }
    }

    public boolean getBoolean(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String input = sc.nextLine().trim().toLowerCase();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input can't be null!");
                }
                if (input.equals("true") || input.equals("false")) {
                    return Boolean.parseBoolean(input);
                } else {
                    throw new IllegalArgumentException("Error: Please enter 'true' or 'false'!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalTime getTime(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String time = sc.nextLine().trim();
                if (time.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input can't be null!");
                }
                if (!time.matches(TIME_24_REGEX)) {
                    throw new IllegalArgumentException("Error: Invalid time format! Please re-enter time (HH:mm).");
                }
                return convertStringToLocalTime(time);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalTime convertStringToLocalTime(String time) {
        String[] patterns = {"HH:mm", "H:mm", "HH:mm:ss", "H:mm:ss"};
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalTime.parse(time, formatter);
            } catch (DateTimeParseException e) {
            }
        }
        throw new IllegalArgumentException("Invalid time format: " + time);
    }

    public LocalDate getDate(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String day = sc.nextLine().trim();
                if (day.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input can't be null!");
                }
                if (!day.matches(DATE_REGEX)) {
                    throw new IllegalArgumentException("Error: Invalid date format! Please re-enter date (dd/MM/yyyy).");
                }
                LocalDate date = convertStringToLocalDate(day);
                return date;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalDate convertStringToLocalDate(String day) {
        String[] patterns = {"d/M/yyyy", "dd/M/yyyy", "d/MM/yyyy", "dd/MM/yyyy"};
        DateTimeFormatter formatter ;
        for (String pattern : patterns) {
            try {
                 formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(day, formatter);

          } catch (DateTimeParseException | IllegalArgumentException e ) {

            }
    }
        return null;
    }

    public String getTwoDecimalPlaces(double number) {
        number = Math.round(number * 100) / 100;
        return String.format("%.2f", number);
    }

    public boolean continueConfirm(String msg) {
        while (true) {
            System.out.print(msg + " (y/n): ");
            try {
                String choice = sc.nextLine().toLowerCase();

                if (choice.equals("y") || choice.equals("yes")) {
                    return true;
                } else if (choice.equals("n") || choice.equals("no")) {
                    return false;
                } else {
                    System.out.println("Please enter y or n!!");
                }
            } catch (Exception e) {
                System.out.println("Please enter y or n!!");
            }
        }
    }
}
