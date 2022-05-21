package br.com.estudos.estrutura;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Locadora {
    private List<Filme> filmes = new ArrayList<>() {{
        add(new Filme("Pantera Negra", LocalDate.of(2018, 10, 10), 2, 12.5));
        add(new Filme("Django Livre", LocalDate.of(2015, 10, 10), 1, 1d));
        add(new Filme("Viúva Negra", LocalDate.of(2021, 10, 10), 0, 72d));
        add(new Filme("A volta dos que não foram", LocalDate.of(1997, 8, 15), 1, 100d));
        add(new Filme("Miranha 1", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Miranha 2", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Miranha 3", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Espetacular Miranha 1", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Espetacular Miranha 2", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Miranha Longe de casa", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Miranha Sem volta pra casa", LocalDate.of(1997, 8, 15), 1, 30));
        add(new Filme("Filme Genérico", LocalDate.of(1997, 8, 15), 50, 30));
    }};

    public List<Filme> getAllFilmes() {
        return filmes;
    }

    public Optional<Filme> getByNome(String nome) {
        return filmes.stream().filter(filme -> filme.getNome().equalsIgnoreCase(nome.trim())).findFirst();
    }

    private LocalDate dataAleatoria() {
        return LocalDate.ofYearDay(new Random().nextInt(), 23);
    }
}
