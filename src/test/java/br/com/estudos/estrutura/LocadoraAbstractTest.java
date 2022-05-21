package br.com.estudos.estrutura;

import br.com.estudos.estrutura.dao.LocacaoDao;
import br.com.estudos.estrutura.services.EmailService;
import br.com.estudos.estrutura.services.LocadoraService;
import br.com.estudos.estrutura.services.SituacaoClienteService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public abstract class LocadoraAbstractTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected LocadoraService locadoraService;
    protected Locadora locadora;

    protected LocacaoDao dao;
    protected SituacaoClienteService situacao;
    protected EmailService emailService;

    @Before
    public void before() {
        dao = Mockito.mock(LocacaoDao.class);
        situacao = Mockito.mock(SituacaoClienteService.class);
        emailService = Mockito.mock(EmailService.class);
        locadoraService = new LocadoraService(dao, situacao, emailService);
        locadora = new Locadora();
    }

    protected Filme getFilme(String nome) {
        return locadora.getByNome(nome).orElse(null);
    }

    protected static double valorComDesconto(Filme filme, double percentualDesconto) {
        if (filme == null) return 0d;
        double desconto = filme.getValorAluguel() * percentualDesconto;
        return filme.getValorAluguel() - desconto;
    }
}
