package br.com.estudos.suite;

import br.com.estudos.estrutura.LocadoraTest;
import br.com.estudos.estrutura.OrdemTest;
import br.com.estudos.tdd.CalculadoraTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        OrdemTest.class,
        LocadoraTest.class
})
public class SuiteTestes {
    //ùtil para quando uma bateria precisa de uma configuração antes
    //Acho melhor utilizar herança nestes casos
    //Pois aqui, vai repetir os testes assim que subir a aplicação, ou rodar pelo maven ...


    @BeforeClass
    public static void before() {
        System.out.println("init -- Bateria de Testes específicos");
    }

    @AfterClass
    public static void after() {
        System.out.println("end -- Bateria de Testes específicos");
    }
}
