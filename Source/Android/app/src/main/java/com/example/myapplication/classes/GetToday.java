package com.example.myapplication.classes;



import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;


public class GetToday {

    public String getToday() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }
    public long calculateDaysBetween(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        long daysBetween = ChronoUnit.DAYS.between(localDate1, localDate2);
        return daysBetween + 1;
    }
}
