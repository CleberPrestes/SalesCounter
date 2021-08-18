package br.eti.arthurgregorio.contadorvendas;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractBeanField<String, LocalDate> {

    @Override
    protected LocalDate convert(String value) {
        // TODO implementar a conversao de data em String para LocalDate

        final var convertedDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
          /*
        System.out.println("Antes de converter "+value+" - Convertido "+convertedDate);
        */
        return convertedDate;
    }
}
