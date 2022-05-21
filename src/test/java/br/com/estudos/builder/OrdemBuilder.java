package br.com.estudos.builder;

import br.com.estudos.estrutura.Locadora;
import br.com.estudos.estrutura.Ordem;

import java.time.LocalDate;

public class OrdemBuilder {
    private Ordem ordem;

    private Locadora locadora;

    private OrdemBuilder() {
        this.locadora = new Locadora();
    }

    public static OrdemBuilder builder() {
        OrdemBuilder builder = new OrdemBuilder();
        builder.ordem = new Ordem();
        return builder;
    }

    public OrdemBuilder ordemComum() {
        this.ordem.setCliente(ClienteBuilder.clienteNovo().get());
        this.ordem.setDataDevolucao(LocalDate.now().plusDays(3));
        this.ordem.addFilme(locadora.getAllFilmes().get(0));
        this.ordem.addFilme(locadora.getAllFilmes().get(1));
        return this;
    }

    public OrdemBuilder ordemAtrasada() {
        this.ordem.setCliente(ClienteBuilder.clienteNovo().get());
        this.ordem.setDataDevolucao(LocalDate.now().minusDays(5));
        this.ordem.addFilme(locadora.getAllFilmes().get(0));
        this.ordem.addFilme(locadora.getAllFilmes().get(1));
        return this;
    }

    public Ordem build(){
        return this.ordem;
    }
}
