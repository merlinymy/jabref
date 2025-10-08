package org.jabref.logic.layout.format;

import org.jabref.logic.layout.LayoutFormatter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorAndsCommaReplacerTest {

    /**
     * Test method for {@link org.jabref.logic.layout.format.AuthorAndsCommaReplacer#format(java.lang.String)}.
     */
    @ParameterizedTest
    @CsvSource({
            // Empty case
            "'', ''",

            // Single Names don't change
            "'Someone, Van Something', 'Someone, Van Something'",

            // Two names just an &
            "'John von Neumann & Peter Black Brown', 'John von Neumann and Peter Black Brown'",

            // Three names put a comma:
            "'von Neumann, John, Smith, John & Black Brown, Peter', 'von Neumann, John and Smith, John and Black Brown, Peter'"
    })
    void format(String expected, String input) {
        LayoutFormatter formatter = new AuthorAndsCommaReplacer();
        assertEquals(expected, formatter.format(input));
    }
}
