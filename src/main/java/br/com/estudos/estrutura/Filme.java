package br.com.estudos.estrutura;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Filme {
    private String nome;
    private LocalDate lancamento;
    private int qntEstoque;
    private double valorAluguel;
}
