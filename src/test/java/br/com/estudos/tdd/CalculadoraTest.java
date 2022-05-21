package br.com.estudos.tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculadoraTest {
    private Calculadora calc;

    @Before
    public void before() {
        calc = new Calculadora();
    }

    @Test
    public void soma_valores() {
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int resultado = calc.somar(a, b);

        //verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void subtrair_valores() {
        //cenario
        int a = 5;
        int b = 3;


        //acao
        int resultado = calc.subtrair(a, b);

        //verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void dividir_valores() {
        //cenario
        int a = 10;
        int b = 5;


        //acao
        int resultado = calc.dividir(a, b);

        //verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test(expected = ArithmeticException.class)
    public void dividir_valores_por_zero() {
        //cenario
        int a = 50;
        int b = 0;


        //acao
        int resultado = calc.dividir(a, b);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void dividir_valores_por_zero_v2() {
        expectedException.expect(ArithmeticException.class);

        //cenario
        int a = 50;
        int b = 0;

        //acao
        int resultado = calc.dividir(a, b);

        //verificaao
    }

    @Test
    public void multiplicar_valores() {
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int resultado = calc.multiplicar(a, b);

        //verificacao
        Assert.assertEquals(15, resultado);
    }
}
