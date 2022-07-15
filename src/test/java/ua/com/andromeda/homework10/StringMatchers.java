package ua.com.andromeda.homework10;

import org.mockito.ArgumentMatcher;

public class StringMatchers implements ArgumentMatcher<String> {
    private String string;

    public StringMatchers(String string) {
        this.string = string;
    }

    @Override
    public boolean matches(String string) {
        return this.string.equals(string);
    }
}
