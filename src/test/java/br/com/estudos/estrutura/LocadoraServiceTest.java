package br.com.estudos.estrutura;

import br.com.estudos.builder.ClienteBuilder;
import br.com.estudos.builder.OrdemBuilder;
import org.junit.*;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class LocadoraServiceTest extends LocadoraAbstractTest {

    //Caso precise utilizar em cada m�todo de teste (manter� o valor)
    //Op��o n�o recomendada, pois os testes devem ser independentes
    private static int contador = 0;

    //executa depois de cada m�todo
    @After
    public void after() {
    }

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Test
    public void data_devolucao() {
        Filme filme = locadora.getByNome("Pantera Negra").orElse(null);
        Cliente cliente = ClienteBuilder.clienteNovo().get();

        Ordem ordem = locadoraService.alugar(cliente, filme);

        assertEquals(0, ordem.getDataDevolucao().compareTo(LocalDate.now().plusDays(3)));
        assertNotNull(ordem.getFilmes());
        assertFalse(ordem.getFilmes().isEmpty());
        assertNotNull(ordem.getCliente());
    }

    //Tratando Exception
    @Test
    public void disponibilidade_filme() {
        Filme[] filmes = {locadora.getByNome("Vi�va Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();

        Ordem ordem = null;
        try {
            ordem = locadoraService.alugar(cliente, filmes);
            fail("O filme consta como indispon�vel, o aluguel n�o deve ser realizado");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), containsString("O filme n�o possui estoque: Vi�va Negra"));
        }
    }

    //Tratando Exception
    @Test(expected = IllegalStateException.class)
    public void disponibilidade_filme_elegante() {
        Filme[] filmes = {locadora.getByNome("Vi�va Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    //Tratando Exception
    @Test
    public void disponibilidade_filme_expected_excepion() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("O filme n�o possui estoque: Vi�va Negra");

        Filme[] filmes = {locadora.getByNome("Vi�va Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    @Test
    @Ignore
    public void devolucao_domingo() {

        //Se a condi��o for verdadeira, vai executar
        //Caso contr�rio, vai pular o teste
        //Vejo que n�o � uma boa pr�tica neste que utilizei
        //mas pode ser �til, em algum momento, aque ainda n�o sei
        Assume.assumeTrue(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY);

        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, getFilme("Django Livre"));
        assertEquals(ordem.getDataDevolucao().getDayOfWeek(), DayOfWeek.MONDAY);
    }

    @Test
    public void cliente_em_debito() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Cliente est� em d�bito com a locadora");

        Cliente cliente = ClienteBuilder.clienteNovo().nome("Gustavo").get();
        Cliente cliente2 = ClienteBuilder.clienteNovo().nome("asd").get();
        Mockito.when(situacao.isNegativado(cliente)).thenReturn(true);

        try {
            locadoraService.alugar(cliente, getFilme("Django Livre"));
        } catch (IllegalStateException e) {
            throw e;
        } finally {
            //verifica��o n�o necess�ria, pois lan�a a exception quando est� negativado
            Mockito.verify(situacao).isNegativado(cliente);
        }
    }

    @Test
    public void notificar_atraso() {
        //Cen�rio
        List<Ordem> ordens = Arrays.asList(
                OrdemBuilder.builder().ordemAtrasada().build(),
                OrdemBuilder.builder().ordemComum().build()
        );
        Mockito.when(dao.obterOrdensAtrasadas()).thenReturn(ordens);

        //acao
        locadoraService.notificarAtrasos();

        //Verifica��o

        //Quantiade de vezes chamada
        Mockito.verify(emailService, Mockito.times(1)).notificarAtraso(ordens.get(0));
        //se foi chamado
        Mockito.verify(emailService).notificarAtraso(ordens.get(0));
        //Certifica que nunca foi chamado
        Mockito.verify(emailService, Mockito.never()).notificarAtraso(ordens.get(1));
        //Certifica que n�o teve mais intera��es na interface
        Mockito.verifyNoMoreInteractions(emailService);

    }
}