package com.vnco.fusiontech.common.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public static Month getMonth(String date) {
        var inputDate = convertToDate(date);
        return inputDate.getMonth();
    }

    public static void main(String[] args) {
        System.out.println("month: "+ getMonth("2023-08-08").getValue());
        System.out.println(convertToDate("2023-08-08"));
    }
}
