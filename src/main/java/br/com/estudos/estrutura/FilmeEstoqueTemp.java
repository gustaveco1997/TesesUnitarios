package br.com.estudos.estrutura;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class FilmeEstoqueTemp {
    private String nome;
    private int qntEstoque;
    private LocalDate lancamento;

    public FilmeEstoqueTemp(Filme filme) {
        this.nome = filme.getNome();
        this.qntEstoque = filme.getQntEstoque();
        this.lancamento=filme.getLancamento();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmeEstoqueTemp)) return false;
        FilmeEstoqueTemp that = (FilmeEstoqueTemp) o;
        return qntEstoque == that.qntEstoque && Objects.equals(nome, that.nome) && Objects.equals(lancamento, that.lancamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, qntEstoque, lancamento);
    }
}
