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
    //�til para quando uma bateria precisa de uma configura��o antes
    //Acho melhor utilizar heran�a nestes casos
    //Pois aqui, vai repetir os testes assim que subir a aplica��o, ou rodar pelo maven ...


    @BeforeClass
    public static void before() {
        System.out.println("init -- Bateria de Testes espec�ficos");
    }

    @AfterClass
    public static void after() {
        System.out.println("end -- Bateria de Testes espec�ficos");
    }
}
