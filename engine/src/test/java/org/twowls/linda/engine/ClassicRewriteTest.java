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
    void algae(LSystem.Builder builder) {
        LSystem ls = builder
                .rule("a").out("a", "b")
                .rule("b").out("a")
                .axiom().out("a")
                .build();

        String[] words = {
                "a",
                "ab",
                "aba",
                "abaab",
                "abaababa"
        };

        Interpreter<String> interpreter = Interpreters.joining().andThen(Interpreters.printing());
        for (int i = 0; i < words.length; i++) {
            assertEquals(words[i], ls.rewrite(i, interpreter));
        }
    }

    @Test
    void fibonacci(LSystem.Builder builder) {
        LSystem ls = builder
                .rule("a").out("b")
                .rule("b").out("a", "b")
                .axiom().out("a")
                .build();

        long[] series = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 10946, 832040 };
        for ( int i = 0; i < series.length; i++) {
            assertEquals(Long.valueOf(series[i]), ls.rewrite(i, Interpreters.counting()));
        }
    }
}
