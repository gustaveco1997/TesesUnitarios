package br.com.estudos.estrutura.services;

import br.com.estudos.estrutura.Ordem;

public interface EmailService {
    void notificarAtraso(Ordem ordem);
}
