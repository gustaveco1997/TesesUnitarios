package br.com.estudos.estrutura.services;

import br.com.estudos.estrutura.Cliente;
import br.com.estudos.estrutura.Filme;
import br.com.estudos.estrutura.Ordem;
import br.com.estudos.estrutura.dao.LocacaoDao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LocadoraService {
    private final int DIAS_ALUGUEL_FILME = 3;
    private LocacaoDao dao;
    private SituacaoClienteService situacaoService;
    private EmailService emailService;

    public LocadoraService(LocacaoDao dao, SituacaoClienteService situacaoService, EmailService emailService) {
        this.dao = dao;
        this.situacaoService = situacaoService;
        this.emailService = emailService;
    }

    public Ordem alugar(Cliente cliente, Filme... filmes) {
        //List<Filme> filmesList = filmes == null ? Collections.emptyList() : List.of(filmes);
        return alugar(cliente, Arrays.stream(filmes).collect(Collectors.toList()));
    }

    public Ordem alugar(Cliente cliente, List<Filme> filmes) {
        filmes = filmes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Ordem ordem = new Ordem(cliente, filmes);
        ordem.setDataDevolucao(LocalDate.now().plusDays(DIAS_ALUGUEL_FILME));

        validarCliente(cliente);
        validarFilmes(filmes);
        validarEstoque(filmes);

        darBaixaEstoque(filmes);

        dao.salvar(ordem);
        return ordem;
    }

    public void notificarAtrasos() {
        List<Ordem> ordens = dao.obterOrdensAtrasadas();
        ordens.forEach(ordem -> {
            if (ordem.getDataDevolucao().isBefore(LocalDate.now()))
                emailService.notificarAtraso(ordem);
        });
    }

    private void validarFilmes(List<Filme> filmes) {
        if (filmes == null || filmes.isEmpty())
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

        if (situacaoService.isNegativado(cliente))
            throw new IllegalStateException("Cliente está em débito com a locadora");
    }

    private void validarEstoque(List<Filme> filmes) throws IllegalStateException {
        //todo: tratar itens repetidos na lista
        for (Filme filme : filmes) {
            int qntEstoque = filme.getQntEstoque();
            if (qntEstoque < 1)
                throw new IllegalStateException("O filme não possui estoque: " + filme.getNome());
        }
    }

    private void darBaixaEstoque(List<Filme> filmes) {
        for (Filme filme : filmes) {
            int qntEstoque = filme.getQntEstoque();
            filme.setQntEstoque(qntEstoque - 1);
        }
    }
}
