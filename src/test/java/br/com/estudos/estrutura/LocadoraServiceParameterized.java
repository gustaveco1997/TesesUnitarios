package br.com.estudos.estrutura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class LocadoraServiceParameterized extends LocadoraAbstractTest {

    //DDT -> Data Driven Test
    //Consiste em :
    // Escreve o teste
    // Passa parâmetros via array


    private Cliente cliente;

    private static Filme filme1;
    private static Filme filme2;
    private static Filme filme3;
    private static Filme filme4;
    private static Filme filme5;
    private static Filme filme6;
    private static Filme filme7;

    //value = 0 (primeiro paramtro do array em @Parameters)
    @Parameterized.Parameter(value = 0)
    public List<Filme> filmes;
    //value = 1 (segundo paramtro do array em @Parameters)
    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String nomeMetodo;

    @Before
    public void before() {
        super.before();
        cliente = new Cliente("123", "Lorena", LocalDate.now());
    }

    //@Parameterized.Parameters(name="Teste {index} = {0} - {1}")
    @Parameterized.Parameters(name="{index} = {2}")
    public static Collection<Object[]> parametros() {

        filme1 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme2 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme3 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme4 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme5 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme6 = new Locadora().getByNome("Filme Genérico").orElse(null);
        filme7 = new Locadora().getByNome("Filme Genérico").orElse(null);

        double total_01 = valorComDesconto(filme1, 0);
        double total_02 = total_01 + valorComDesconto(filme2, 0);
        double total_03 = total_02 + valorComDesconto(filme3, 0.25);
        double total_04 = total_03 + valorComDesconto(filme4, 0.5);
        double total_05 = total_04 + valorComDesconto(filme5, 0.75);
        double total_06 = total_05 + valorComDesconto(filme6, 1.0);
        double total_07 = total_06 + valorComDesconto(filme7, 0);


        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2, filme3), total_03, "3 Filmes 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), total_04, "4 Filmes 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), total_05, "5 Filmes 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), total_06, "6 Filmes 100 %"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), total_07, "7 Filmes 0%"},
        });
    }

    @Test
    public void testaValorLocacao() {
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(valorLocacao, ordem.getValorTotal(), 0.01);
    }


}
