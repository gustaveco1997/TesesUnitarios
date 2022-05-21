package br.com.estudos.builder;

import br.com.estudos.estrutura.Cliente;

import java.time.LocalDate;

public class ClienteBuilder {

    private Cliente cliente;

    private ClienteBuilder() {

    }

    public static ClienteBuilder clienteNovo() {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new Cliente("123718237", "Joaquina", LocalDate.now(), "email@gmail.com");
        return builder;
    }

    public ClienteBuilder maiorIdade() {
        LocalDate now = LocalDate.now();
        cliente.setDataNascimento(LocalDate.of(now.getYear() - 18, now.getMonth(), now.getDayOfMonth() - 1));
        return this;
    }

    public ClienteBuilder menorIdade() {
        LocalDate now = LocalDate.now();
        cliente.setDataNascimento(LocalDate.of(now.getYear() - 13, now.getMonth(), now.getDayOfMonth() - 1));
        return this;
    }

    public ClienteBuilder nome(String nome){
        this.cliente.setNome(nome);
        return this;
    }
    public Cliente get() {
        return this.cliente;
    }
}
