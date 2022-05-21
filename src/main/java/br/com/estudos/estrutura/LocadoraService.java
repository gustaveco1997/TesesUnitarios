package br.com.estudos.estrutura;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LocadoraService {
    private final int DIAS_ALUGUEL_FILME = 3;

    public Ordem alugar(Cliente cliente, Filme... filmes) {
        return alugar(cliente, List.of(filmes));
    }

    public Ordem alugar(Cliente cliente, List<Filme> filmes) {
        Ordem ordem = new Ordem(cliente, filmes);
        ordem.setDataDevolucao(LocalDate.now().plusDays(DIAS_ALUGUEL_FILME));

        validarCliente(cliente);
        validarFilmes(filmes);

        for (Filme filme : filmes) {
            int qntEstoque = filme.getQntEstoque();
            if (qntEstoque < 1)
                throw new IllegalStateException("O filme não possui estoque: " + filme.getNome());
            filme.setQntEstoque(qntEstoque - 1);
        }
        return ordem;
    }

    private void validarFilmes(List<Filme> filmes) {
        if (filmes == null)
            throw new IllegalStateException("Escolha os filmes para alugar");

        Iterator<Filme> it = filmes.iterator();
        while (it.hasNext()) {
            Filme filme = it.next();
            if (filme == null)
                throw new IllegalStateException("O filme escolhido é inválido");
        }

    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null)
            throw new IllegalStateException("Você não possui cadastro na locadora");
    }
}
