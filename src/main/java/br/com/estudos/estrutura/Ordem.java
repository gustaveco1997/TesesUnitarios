package br.com.estudos.estrutura;

import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ordem {
    private LocalDate dataDevolucao;
    @Setter(AccessLevel.NONE)
    private List<Filme> filmes;
    private Cliente cliente;
    private double valorTotal;
    private String email;

    public Ordem(Cliente cliente, List<Filme> filmes) {
        start(cliente, filmes);
    }

    public Ordem(Cliente cliente, Filme... filmes) {
        start(cliente, List.of(filmes));
    }

    public void addFilme(Filme filme) {
        if (filmes == null)
            filmes = new ArrayList<>();
        filmes.add(filme);

        calcularValorTotal();
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
        this.valorTotal = filmes.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Filme::getValorAluguel).sum();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ordem)) return false;
        Ordem ordem = (Ordem) o;
        return Double.compare(ordem.valorTotal, valorTotal) == 0 && Objects.equals(dataDevolucao, ordem.dataDevolucao) && Objects.equals(filmes, ordem.filmes) && Objects.equals(cliente, ordem.cliente) && Objects.equals(email, ordem.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataDevolucao, filmes, cliente, valorTotal, email);
    }
}
