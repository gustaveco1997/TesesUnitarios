package br.com.estudos.estrutura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ordem {
    private LocalDate dataDevolucao;
    private List<Filme> filmes;
    private Cliente cliente;
    private double valorTotal;

    public Ordem(Cliente cliente, List<Filme> filmes) {
        start(cliente, filmes);
    }

    public Ordem(Cliente cliente, Filme... filmes) {
        start(cliente, List.of(filmes));
    }

    private void start(Cliente cliente, List<Filme> filmes) {
        this.cliente = cliente;
        this.filmes = filmes;
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        calcularValorTotalComum();
        calcularValorTotalDescontoCrescente();
    }


    private void calcularValorTotalComum() {
        this.valorTotal = filmes.stream().mapToDouble(Filme::getValorAluguel).sum();
    }

    private void calcularValorTotalDescontoCrescente() {
        Map<Integer, Double> tabelaDesconto = new HashMap<>() {{
            put(3, 0.25);
            put(4, 0.5);
            put(5, 0.75);
            put(6, 1.0);
        }};

        double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++) {
            Double percentual = tabelaDesconto.get(i + 1);
            valorTotal += calcularComDesconto(filmes.get(i), percentual != null ? percentual : 0d);
        }

        this.valorTotal = valorTotal;
    }

    private double calcularComDesconto(Filme filme, double percentualDesconto) {
        return filme.getValorAluguel() - (filme.getValorAluguel() * percentualDesconto);
    }
}
