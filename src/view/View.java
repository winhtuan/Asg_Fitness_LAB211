package view;

import java.time.format.DateTimeFormatter;

public interface View<T> {

    final String USER_REGEX = "CUS-\\d{4}";
    final String COURSE_REGEX = "COR-\\d{4}";
    final String COACH_REGEX = "COA-\\d{4}";
    final String SCHEDULE_REGEX = "SKD-\\d{4}";
    final String WORKOUT_REGEX = "WKT-\\d{4}";
    final String NUTITION_REGEX = "NUT-\\d{4}";
    final String TEXT_REGEX = "^([A-Za-z]+(?:\\s[A-Za-z]+)*)$";
    final String TEXTNORMAL_REGEX = "^[a-zA-Z0-9]+$\n";
    final String SSN_REGEX = "^\\d{12}$";
    final String PHONE_REGEX = "^0\\d{9}$";
    final String EMAIL_REGEX = "^[a-zA-Z0-9]{2,}@gmail\\.com$";
    final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final String ADDRESS_REGEX = "^([A-Za-z]+(?:\\s+[A-Za-z]+)*(?:,\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)*)*)$";

    public void display();
}
