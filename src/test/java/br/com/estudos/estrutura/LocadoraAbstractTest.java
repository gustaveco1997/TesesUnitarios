package br.com.estudos.estrutura;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class LocadoraAbstractTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected LocadoraService locadoraService;
    protected Locadora locadora;

    @Before
    public void before() {
        locadoraService = new LocadoraService();
        locadora = new Locadora();
    }

    protected Filme getFilme(String nome){
        return locadora.getByNome(nome).orElse(null);
    }
}
