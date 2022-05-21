package br.com.estudos.estrutura;

import java.time.LocalDate;

public class Main {

    //FIRST
    // -> Fast
    // -> Inpedendent
    // -> Repeatable
    // -> Self-Verifying
    // -> Timely


    //Red Green Refactor (TDD)
    //teste falha, cria a funcionalidade, passar, caso não, refatora o código

    //Estrutura
    public void main() {
        //estrutura
        Filme filme = new Locadora().getByNome("Pantera Negra").orElse(null);
        Cliente cliente = new Cliente("12345678909", "Paula", LocalDate.of(1999, 3, 23));

        //ação
        Ordem ordem = new LocadoraService().alugar(cliente, filme);

        //Verificação
        //Assert.assertTrue(ordem.getDataDevolucao().compareTo(LocalDate.now().plusDays(3)) == 0)
    }
}
