package org.jabref.logic.layout.format;

import org.jabref.logic.layout.LayoutFormatter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorLastFirstAbbrCommasTest {

    /**
     * Test method for {@link org.jabref.logic.layout.format.AuthorLastFirstAbbrCommas#format(java.lang.String)}.
     */

    @ParameterizedTest
    @CsvSource({
            "'', ''", // Empty case
            "'Someone, V. S.', 'Van Something Someone'", // Single Names
            "'von Neumann, J. and Black Brown, P.', 'John von Neumann and Black Brown, Peter'", // Two names
            "'von Neumann, J., Smith, J. and Black Brown, P.', 'von Neumann, John and Smith, John and Black Brown, Peter'", // Three names
            "'von Neumann, J., Smith, J. and Black Brown, P.', 'John von Neumann and John Smith and Black Brown, Peter'" // Three names
    })
    void format(String expected, String input) {
        LayoutFormatter formatter = new AuthorLastFirstAbbrCommas();
        assertEquals(expected, formatter.format(input));
    }
}
