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

    //Caso precise utilizar em cada método de teste (manterá o valor)
    //Opção não recomendada, pois os testes devem ser independentes
    private static int contador = 0;

    //executa depois de cada método
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
        Filme[] filmes = {locadora.getByNome("Viúva Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();

        Ordem ordem = null;
        try {
            ordem = locadoraService.alugar(cliente, filmes);
            fail("O filme consta como indisponível, o aluguel não deve ser realizado");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), containsString("O filme não possui estoque: Viúva Negra"));
        }
    }

    //Tratando Exception
    @Test(expected = IllegalStateException.class)
    public void disponibilidade_filme_elegante() {
        Filme[] filmes = {locadora.getByNome("Viúva Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    //Tratando Exception
    @Test
    public void disponibilidade_filme_expected_excepion() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("O filme não possui estoque: Viúva Negra");

        Filme[] filmes = {locadora.getByNome("Viúva Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    @Test
    @Ignore
    public void devolucao_domingo() {

        //Se a condição for verdadeira, vai executar
        //Caso contrário, vai pular o teste
        //Vejo que não é uma boa prática neste que utilizei
        //mas pode ser útil, em algum momento, aque ainda não sei
        Assume.assumeTrue(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY);

        Cliente cliente = ClienteBuilder.clienteNovo().get();
        Ordem ordem = locadoraService.alugar(cliente, getFilme("Django Livre"));
        assertEquals(ordem.getDataDevolucao().getDayOfWeek(), DayOfWeek.MONDAY);
    }

    @Test
    public void cliente_em_debito() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Cliente está em débito com a locadora");

        Cliente cliente = ClienteBuilder.clienteNovo().nome("Gustavo").get();
        Cliente cliente2 = ClienteBuilder.clienteNovo().nome("asd").get();
        Mockito.when(situacao.isNegativado(cliente)).thenReturn(true);

        try {
            locadoraService.alugar(cliente, getFilme("Django Livre"));
        } catch (IllegalStateException e) {
            throw e;
        } finally {
            //verificação não necessária, pois lança a exception quando está negativado
            Mockito.verify(situacao).isNegativado(cliente);
        }
    }

    @Test
    public void notificar_atraso() {
        //Cenário
        List<Ordem> ordens = Arrays.asList(
                OrdemBuilder.builder().ordemAtrasada().build(),
                OrdemBuilder.builder().ordemComum().build()
        );
        Mockito.when(dao.obterOrdensAtrasadas()).thenReturn(ordens);

        //acao
        locadoraService.notificarAtrasos();

        //Verificação

        //Quantiade de vezes chamada
        Mockito.verify(emailService, Mockito.times(1)).notificarAtraso(ordens.get(0));
        //se foi chamado
        Mockito.verify(emailService).notificarAtraso(ordens.get(0));
        //Certifica que nunca foi chamado
        Mockito.verify(emailService, Mockito.never()).notificarAtraso(ordens.get(1));
        //Certifica que não teve mais interações na interface
        Mockito.verifyNoMoreInteractions(emailService);

    }
}