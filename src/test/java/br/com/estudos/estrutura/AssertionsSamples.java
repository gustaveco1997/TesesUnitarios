package br.com.estudos.estrutura;

import br.com.estudos.builder.ClienteBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AssertionsSamples {

    //Utilizado para prosseguir com a linha de teste, mesmo que falhe
    @Rule
    public final ErrorCollector error = new ErrorCollector();

    @Test
    public void tratamento_com_error_collector(){
        error.checkThat(12d, is(equalTo(12d)));
        error.checkThat("Vassoura", startsWith("Vass"));
        error.checkThat("Vassoura", endsWith("oura"));
    }

    @Test
    public void assertions() {
        assertEquals(12.52, 12.52, 0.01);
        assertNotEquals(12.70, 12.52, 0.01);
        assertTrue("Teste".equalsIgnoreCase("teste"));
        assertTrue("nasaq".equalsIgnoreCase("NASAQ"));

        Cliente c1 = ClienteBuilder.clienteNovo().nome("Nome").get();
        Cliente c2 = c1;
        assertSame(c1, c2); //valida instancias

        Cliente c3 = ClienteBuilder.clienteNovo().nome("Nome").get();
        Cliente c4 = ClienteBuilder.clienteNovo().nome("Paula").get();

        assertNotEquals(c3, c4);
        assertEquals(c3, c1);

        //Utiliza Matchers
        assertThat(12d, is(equalTo(12d)));
        assertThat("Vassoura", startsWith("Vass"));
    }
}
