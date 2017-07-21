package org.twowls.linda.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Trivial and classic rewrite tests.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
class ClassicRewriteTest {

    @Test
    void algae() {
        IntLSystem ls = LSystemBuilders.intSymbols()
                .rule('a').out('a', 'b')
                .rule('b').out('a')
                .axiom().out('a')
                .build();

        String[] words = {
                "a",
                "ab",
                "aba",
                "abaab",
                "abaababa"
        };

        Interpreter<Integer, String> interpreter = Interpreters.joining();
        for (int i = 0; i < words.length; i++) {
            assertEquals(words[i], ls.rewrite(i, interpreter));
            System.out.println(interpreter.getResult());
        }
    }

    @Test
    void fibonacci() {
        IntLSystem ls = LSystemBuilders.intSymbols()
                .rule(0).out(1)
                .rule(1).out(0, 1)
                .axiom().out(0)
                .build();

        long[] series = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 10946, 832040 };
        for (int i = 0; i < series.length; i++) {
            assertEquals(Long.valueOf(series[i]), ls.rewrite(i, Interpreters.counting()));
        }
    }
}
