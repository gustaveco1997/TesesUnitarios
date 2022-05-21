package br.com.estudos.estrutura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class LocadoraTest {

    private Locadora locadora;
    private List<Filme> filmes;

    @Before
    public void before() {
        locadora = new Locadora();
        filmes = new Locadora().getAllFilmes();
    }

    @Test
    public void locadora_possui_filmes() {
        Assert.assertNotNull(filmes);
        Assert.assertFalse(filmes.isEmpty());
    }

    @Test
    public void find_by_nome() {
        Optional<Filme> filme = locadora.getByNome("Django Livre");
        Assert.assertTrue(filme.isPresent());
    }

    @Test
    public void find_by_nome_camel_case() {
        Optional<Filme> filme = locadora.getByNome("django livre");
        Assert.assertTrue(filme.isPresent());
    }

}
