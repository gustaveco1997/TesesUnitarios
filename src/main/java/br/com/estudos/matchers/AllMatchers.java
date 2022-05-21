package br.com.estudos.matchers;

import java.time.DayOfWeek;

public class AllMatchers {
    public static DiaSemanaMatcher diaSemana(DayOfWeek dayOfWeek) {
        return new DiaSemanaMatcher(dayOfWeek);
    }

    public static DiaSemanaMatcher caiNaTerca() {
        return new DiaSemanaMatcher(DayOfWeek.TUESDAY);
    }
}
