package br.com.estudos.estrutura;

import org.junit.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
        System.out.println("Teste iniciado");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Teste Finalizado");
    }

    @Test
    public void data_devolucao() {
        Filme filme = locadora.getByNome("Pantera Negra").orElse(null);
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));

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
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));

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
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    //Tratando Exception
    @Test
    public void disponibilidade_filme_expected_excepion() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("O filme n�o possui estoque: Vi�va Negra");

        Filme[] filmes = {locadora.getByNome("Vi�va Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    @Test
    public void devolucao_domingo(){

        //Se a condi��o for verdadeira, vai executar
        //Caso contr�rio, vai pular o teste
        //Vejo que n�o � uma boa pr�tica neste que utilizei
        //mas pode ser �til, em algum momento, aque ainda n�o sei
        Assume.assumeTrue(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY);

        Cliente cliente = new Cliente("123", "Gustavo", LocalDate.now());
        Ordem ordem = locadoraService.alugar(cliente , getFilme("Django Livre"));
        assertEquals(ordem.getDataDevolucao().getDayOfWeek(), DayOfWeek.MONDAY);
    }

}