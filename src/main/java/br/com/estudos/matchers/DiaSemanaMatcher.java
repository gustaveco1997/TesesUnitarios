package br.com.estudos.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DiaSemanaMatcher extends TypeSafeMatcher<LocalDate> {
    private final DayOfWeek dayOfWeek;

    public DiaSemanaMatcher(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(dayOfWeek.name());
    }

    @Override
    protected boolean matchesSafely(LocalDate localDate) {
        return localDate.getDayOfWeek() == dayOfWeek;
    }


}
