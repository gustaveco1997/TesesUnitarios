package br.com.estudos.estrutura;

import org.junit.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
        Filme[] filmes = {locadora.getByNome("Viúva Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));

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
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    //Tratando Exception
    @Test
    public void disponibilidade_filme_expected_excepion() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("O filme não possui estoque: Viúva Negra");

        Filme[] filmes = {locadora.getByNome("Viúva Negra").orElse(null), locadora.getByNome("Django Livre").orElse(null)};
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));
        Ordem ordem = locadoraService.alugar(cliente, filmes);
    }

    @Test
    public void devolucao_domingo(){

        //Se a condição for verdadeira, vai executar
        //Caso contrário, vai pular o teste
        //Vejo que não é uma boa prática neste que utilizei
        //mas pode ser útil, em algum momento, aque ainda não sei
        Assume.assumeTrue(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY);

        Cliente cliente = new Cliente("123", "Gustavo", LocalDate.now());
        Ordem ordem = locadoraService.alugar(cliente , getFilme("Django Livre"));
        assertEquals(ordem.getDataDevolucao().getDayOfWeek(), DayOfWeek.MONDAY);
    }

}