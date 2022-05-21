package br.com.estudos.estrutura;

import br.com.estudos.builder.ClienteBuilder;
import br.com.estudos.matchers.AllMatchers;
import org.junit.Assume;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertThat;

public class MacherEspecifico extends LocadoraAbstractTest {
    @Test
    public void devolucao_3_dias() {
        Assume.assumeTrue(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY);

        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, getFilme("Django Livre"));
        LocalDate diaDevolucao = LocalDate.now().plusDays(3);
        //Matcher simples (isolado)
        //assertThat(ordem.getDataDevolucao(), new DiaSemanaMatcher(diaDevolucao.getDayOfWeek()));

        //Criei a classe AllMatchers, que encapsula todos os Matcher, similar a classe CoreMatchers
        assertThat(ordem.getDataDevolucao(), AllMatchers.diaSemana(diaDevolucao.getDayOfWeek()));
        assertThat(ordem.getDataDevolucao(), AllMatchers.caiNaTerca());
    }
}
