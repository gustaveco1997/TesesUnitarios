package br.com.estudos.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class YearGeneratorTest {
    private YearGenerator generator;

    @Before
    public void before() {
        generator = new YearGenerator();
    }

    @Test
    public void range() {
        for (int i = 0; i < 100; i++) {
            int valor = generator.generate();
            Assert.assertTrue("Valor: " + valor, valor >= 1900 && valor <= LocalDate.now().getYear());
        }
    }
}
