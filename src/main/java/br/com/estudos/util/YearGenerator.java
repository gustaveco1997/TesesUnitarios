package br.com.estudos.util;

import java.time.LocalDate;
import java.util.Random;

public class YearGenerator {
    public int generate(){
        return new Random().ints(1, 1900, LocalDate.now().getYear()).findFirst().getAsInt();
    }

}


