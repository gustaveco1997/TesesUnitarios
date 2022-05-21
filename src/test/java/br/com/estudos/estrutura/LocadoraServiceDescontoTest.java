package br.com.estudos.estrutura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class LocadoraServiceDescontoTest extends LocadoraAbstractTest {

    private Cliente cliente;

    @Before
    public void before() {
        super.before();
        cliente = new Cliente("123", "Lorena", LocalDate.now());
    }

    @Test
    public void calcular_valor_total_1_filme() {
        Filme[] filmes = {getFilme("Django Livre")};

        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(1d, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void calcular_valor_total_2_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme[] filmes = {filme1, filme2};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel();
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void calcular_descontos_crescentes_3_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme filme3 = getFilme("Miranha 3");
        Filme[] filmes = {filme1, filme2, filme3};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel()
                + valorComDesconto(filme3, 0.25);
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void calcular_descontos_crescentes_4_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme filme3 = getFilme("Miranha 3");
        Filme filme4 = getFilme("Espetacular Miranha 1");
        Filme[] filmes = {filme1, filme2, filme3, filme4};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel()
                + valorComDesconto(filme3, 0.25)
                + valorComDesconto(filme4, 0.50);
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }


    @Test
    public void calcular_descontos_crescentes_5_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme filme3 = getFilme("Miranha 3");
        Filme filme4 = getFilme("Espetacular Miranha 1");
        Filme filme5 = getFilme("Espetacular Miranha 2");
        Filme[] filmes = {filme1, filme2, filme3, filme4, filme5};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel()
                + valorComDesconto(filme3, 0.25)
                + valorComDesconto(filme4, 0.50)
                + valorComDesconto(filme5, 0.75);
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void calcular_descontos_crescentes_6_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme filme3 = getFilme("Miranha 3");
        Filme filme4 = getFilme("Espetacular Miranha 1");
        Filme filme5 = getFilme("Espetacular Miranha 2");
        Filme filme6 = getFilme("Miranha Longe de casa");
        Filme[] filmes = {filme1, filme2, filme3, filme4, filme5, filme6};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel()
                + valorComDesconto(filme3, 0.25)
                + valorComDesconto(filme4, 0.50)
                + valorComDesconto(filme5, 0.75)
                + valorComDesconto(filme6, 1.0);
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void calcular_descontos_crescentes_mais_de_6_filmes() {
        Filme filme1 = getFilme("Miranha 1");
        Filme filme2 = getFilme("Miranha 2");
        Filme filme3 = getFilme("Miranha 3");
        Filme filme4 = getFilme("Espetacular Miranha 1");
        Filme filme5 = getFilme("Espetacular Miranha 2");
        Filme filme6 = getFilme("Miranha Longe de casa");
        Filme filme7 = getFilme("Miranha Sem volta pra casa");
        Filme[] filmes = {filme1, filme2, filme3, filme4, filme5, filme6, filme7};

        double totalEsperado = filme1.getValorAluguel()
                + filme2.getValorAluguel()
                + valorComDesconto(filme3, 0.25)
                + valorComDesconto(filme4, 0.50)
                + valorComDesconto(filme5, 0.75)
                + valorComDesconto(filme6, 1.0)
                + valorComDesconto(filme7, 0);
        Ordem ordem = locadoraService.alugar(cliente, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void nao_deve_alugar_sem_cliente() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Você não possui cadastro na locadora");

        Filme filme1 = getFilme("Miranha 1");
        Filme[] filmes = {filme1};

        double totalEsperado = filme1.getValorAluguel();
        Ordem ordem = locadoraService.alugar(null, filmes);
        Assert.assertEquals(totalEsperado, ordem.getValorTotal(), 0.01);
    }

    @Test
    public void nao_deve_alugar_com_filme_nulo() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("O filme escolhido é inválido");

        Filme[] filmes = {null};
        locadoraService.alugar(null, filmes);
    }

    @Test
    public void nao_deve_alugar_sem_filme_escolhido() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Escolha os filmes para alugar");

        locadoraService.alugar(cliente, null);
    }

    private double valorComDesconto(Filme filme, double percentualDesconto) {
        double desconto = filme.getValorAluguel() * percentualDesconto;
        return filme.getValorAluguel() - desconto;
    }
}
