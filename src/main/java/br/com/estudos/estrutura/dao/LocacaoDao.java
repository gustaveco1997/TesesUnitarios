package br.com.estudos.estrutura.dao;

import br.com.estudos.estrutura.Ordem;

import java.util.List;

public interface LocacaoDao {
    public void salvar(Ordem ordem);

    List<Ordem> obterOrdensAtrasadas();
}
