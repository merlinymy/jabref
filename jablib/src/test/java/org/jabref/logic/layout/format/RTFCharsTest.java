package org.jabref.logic.layout.format;

import org.jabref.logic.layout.LayoutFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RTFCharsTest {
    private LayoutFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new RTFChars();
    }

    @AfterEach
    void tearDown() {
        formatter = null;
    }

    @ParameterizedTest
    @CsvSource({
            "'', ''",
            "'hallo', 'hallo'",
            "'R\\u233eflexions sur le timing de la quantit\\u233e', 'Réflexions sur le timing de la quantité'",
            "'h\\'e1llo', 'h\\'allo'"
    })
    void basicFormat(String expected, String input) {
        assertEquals(expected, formatter.format(input));
    }

    @ParameterizedTest
    @CsvSource({
            "'{\\i hallo}', '\\emph{hallo}'",
            "'{\\i hallo}', '{\\emph hallo}'",
            "'An article title with {\\i a book title} emphasized', 'An article title with \\emph{a book title} emphasized'",
            "'{\\i hallo}', '\\textit{hallo}'",
            "'{\\i hallo}', '{\\textit hallo}'",
            "'{\\b hallo}', '\\textbf{hallo}'",
            "'{\\b hallo}', '{\\textbf hallo}'"
    })
    void laTeXHighlighting(String expected, String input) {
        assertEquals(expected, formatter.format(input));
    }

    @Test
    void complicated() {
        assertEquals("R\\u233eflexions sur le timing de la quantit\\u233e {\\u230ae} should be \\u230ae",
                formatter.format("Réflexions sur le timing de la quantité {\\ae} should be æ"));
    }

    @Test
    void complicated2() {
        assertEquals("h\\'e1ll{\\u339oe}", formatter.format("h\\'all{\\oe}"));
    }

    @Test
    void complicated3() {
        assertEquals("Le c\\u339oeur d\\u233e\\u231cu mais l'\\u226ame plut\\u244ot na\\u239ive, Lou\\u255ys r" +
                "\\u234eva de crapa\\u252?ter en cano\\u235e au del\\u224a des \\u238iles, pr\\u232es du m\\u228alstr" +
                "\\u246om o\\u249u br\\u251ulent les nov\\u230ae.", formatter.format("Le cœur déçu mais l'âme plutôt " +
                "naïve, Louÿs rêva de crapaüter en canoë au delà des îles, près du mälström où brûlent les novæ."));
    }

    @Test
    void complicated4() {
        assertEquals("l'\\u238ile exigu\\u235e\n" +
                "  O\\u249u l'ob\\u232ese jury m\\u251ur\n" +
                "  F\\u234ete l'ha\\u239i volap\\u252?k,\n" +
                "  \\u194Ane ex a\\u233equo au whist,\n" +
                "  \\u212Otez ce v\\u339oeu d\\u233e\\u231cu.", formatter.format("l'île exiguë\n" +
                "  Où l'obèse jury mûr\n" +
                "  Fête l'haï volapük,\n" +
                "  Âne ex aéquo au whist,\n" +
                "  Ôtez ce vœu déçu."));
    }

    @Test
    void complicated5() {
        assertEquals("\\u193Arv\\u237izt\\u369?r\\u337? t\\u252?k\\u246orf\\u250ur\\u243og\\u233ep",
                formatter.format("Árvíztűrő tükörfúrógép"));
    }

    @Test
    void complicated6() {
        assertEquals("Pchn\\u261a\\u263c w t\\u281e \\u322l\\u243od\\u378z je\\u380za lub o\\u347sm skrzy\\u324n fig",
                formatter.format("Pchnąć w tę łódź jeża lub ośm skrzyń fig"));
    }

    @ParameterizedTest
    @CsvSource({
            "'\\'f3', '\\'{o}'",        // ó
            "\\'f2, '\\`{o}'",        // ò
            "\\'f4, '\\^{o}'",        // ô
            "\\'f6, '\\\"{o}'",       // ö
            "'\\u245o', '\\~{o}'",      // õ
            "'\\u333o', '\\={o}'",
            "'\\u335o', '{\\uo}'",
            "'\\u231c', '{\\cc}'",      // ç
            "'{\\u339oe}', '{\\oe}'",
            "'{\\u338OE}', '{\\OE}'",
            "'{\\u230ae}', '{\\ae}'",   // æ
            "'{\\u198AE}', '{\\AE}'",   // Æ

            "'', '\\.{o}'",             // ???
            "'', '\\vo'",               // ???
            "'', '\\Ha'",               // ã ???
            "'', '\\too'",
            "'', '\\do'",               // ???
            "'', '\\bo'",               // ???

            "'\\u229a', '{\\aa}'",      // å
            "'\\u197A', '{\\AA}'",      // Å
            "'\\u248o', '{\\o}'",       // ø
            "'\\u216O', '{\\O}'",       // Ø
            "'\\u322l', '{\\l}'",
            "'\\u321L', '{\\L}'",
            "'\\u223ss', '{\\ss}'",     // ß
            "'\\u191?', '\\`?'",        // ¿
            "'\\u161!', '\\`!'",        // ¡

            "'', '\\dag'",
            "'', '\\ddag'",
            "'\\u167S', '{\\S}'",       // §
            "'\\u182P', '{\\P}'",       // ¶
            "'\\u169?', '{\\copyright}'", // ©
            "'\\u163?', '{\\pounds}'"   // £
    })
    void specialCharacters(String expected, String input) {
        assertEquals(expected, formatter.format(input));
    }

    @ParameterizedTest(name = "specialChar={0}, formattedStr={1}")
    @CsvSource({
            "ÀÁÂÃÄĀĂĄ, \\u192A\\u193A\\u194A\\u195A\\u196A\\u256A\\u258A\\u260A", // A
            "àáâãäåāăą, \\u224a\\u225a\\u226a\\u227a\\u228a\\u229a\\u257a\\u259a\\u261a", // a
            "ÇĆĈĊČ, \\u199C\\u262C\\u264C\\u266C\\u268C", // C
            "çćĉċč, \\u231c\\u263c\\u265c\\u267c\\u269c", // c
            "ÐĐ, \\u208D\\u272D", // D
            "ðđ, \\u240d\\u273d", // d
            "ÈÉÊËĒĔĖĘĚ, \\u200E\\u201E\\u202E\\u203E\\u274E\\u276E\\u278E\\u280E\\u282E", // E
            "èéêëēĕėęě, \\u232e\\u233e\\u234e\\u235e\\u275e\\u277e\\u279e\\u281e\\u283e", // e
            "ĜĞĠĢŊ, \\u284G\\u286G\\u288G\\u290G\\u330G", // G
            "ĝğġģŋ, \\u285g\\u287g\\u289g\\u291g\\u331g", // g
            "ĤĦ, \\u292H\\u294H", // H
            "ĥħ, \\u293h\\u295h", // h
            "ÌÍÎÏĨĪĬĮİ, \\u204I\\u205I\\u206I\\u207I\\u296I\\u298I\\u300I\\u302I\\u304I", // I
            "ìíîïĩīĭį, \\u236i\\u237i\\u238i\\u239i\\u297i\\u299i\\u301i\\u303i", // i
            "Ĵ, \\u308J", // J
            "ĵ, \\u309j", // j
            "Ķ, \\u310K", // K
            "ķ, \\u311k", // k
            "ĹĻĿ, \\u313L\\u315L\\u319L", // L
            "ĺļŀł, \\u314l\\u316l\\u320l\\u322l", // l
            "ÑŃŅŇ, \\u209N\\u323N\\u325N\\u327N", // N
            "ñńņň, \\u241n\\u324n\\u326n\\u328n", // n
            "ÒÓÔÕÖØŌŎ, \\u210O\\u211O\\u212O\\u213O\\u214O\\u216O\\u332O\\u334O", // O
            "òóôõöøōŏ, \\u242o\\u243o\\u244o\\u245o\\u246o\\u248o\\u333o\\u335o", // o
            "ŔŖŘ, \\u340R\\u342R\\u344R", // R
            "ŕŗř, \\u341r\\u343r\\u345r", // r
            "ŚŜŞŠ, \\u346S\\u348S\\u350S\\u352S", // S
            "śŝşš, \\u347s\\u349s\\u351s\\u353s", // s
            "ŢŤŦ, \\u354T\\u356T\\u358T", // T
            "ţŧ, \\u355t\\u359t", // t
            "ÙÚÛÜŨŪŬŮŲ, \\u217U\\u218U\\u219U\\u220U\\u360U\\u362U\\u364U\\u366U\\u370U", // U
            "ùúûũūŭůų, \\u249u\\u250u\\u251u\\u361u\\u363u\\u365u\\u367u\\u371u", // u
            "Ŵ, \\u372W", // W
            "ŵ, \\u373w", // w
            "ŶŸÝ, \\u374Y\\u376Y\\u221Y", // Y
            "ŷÿ, \\u375y\\u255y", // y
            "ŹŻŽ, \\u377Z\\u379Z\\u381Z", // Z
            "źżž, \\u378z\\u380z\\u382z", // z
            "Æ, \\u198AE", // AE
            "æ, \\u230ae", // ae
            "Œ, \\u338OE", // OE
            "œ, \\u339oe", // oe
            "Þ, \\u222TH", // TH
            "ß, \\u223ss", // ss
            "¡, \\u161!" // !
    })
    void moreSpecialCharacters(String specialChar, String expectedResult) {
        String formattedStr = formatter.format(specialChar);
        assertEquals(expectedResult, formattedStr);
    }

    @ParameterizedTest
    @CsvSource({
            "\\'e0, \\`{a}",
            "\\'e8, \\`{e}",
            "\\'ec, \\`{i}",
            "\\'f2, \\`{o}",
            "\\'f9, \\`{u}",

            "\\'e1, \\'a",
            "\\'e9, \\'e",
            "\\'ed, \\'i",
            "\\'f3, \\'o",
            "\\'fa, \\'u",

            "\\'e2, \\^a",
            "\\'ea, \\^e",
            "\\'ee, \\^i",
            "\\'f4, \\^o",
            "\\'fa, \\^u",

            "\\'e4, \\\"a",
            "\\'eb, \\\"e",
            "\\'ef, \\\"i",
            "\\'f6, \\\"o",
            "\\u252u, \\\"u",

            "\\'f1, \\~n"
    })
    void rtfCharacters(String expected, String input) {
        assertEquals(expected, formatter.format(input));
    }

    @ParameterizedTest
    @CsvSource({
            "\\'c0, \\`A",
            "\\'c8, \\`E",
            "\\'cc, \\`I",
            "\\'d2, \\`O",
            "\\'d9, \\`U",

            "\\'c1, \\'A",
            "\\'c9, \\'E",
            "\\'cd, \\'I",
            "\\'d3, \\'O",
            "\\'da, \\'U",

            "\\'c2, \\^A",
            "\\'ca, \\^E",
            "\\'ce, \\^I",
            "\\'d4, \\^O",
            "\\'db, \\^U",

            "\\'c4, \\\"A",
            "\\'cb, \\\"E",
            "\\'cf, \\\"I",
            "\\'d6, \\\"O",
            "\\'dc, \\\"U"
    })
    void rTFCharactersCapital(String expected, String input) {
        assertEquals(expected, formatter.format(input));
    }
}
