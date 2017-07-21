package org.twowls.linda.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Tests various aspects of DSL composition of L-Systems.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
class DSLTest {

    enum Alphabet {
        FIRST,
        SECOND,
        DRAW,
        MOVE,
        ROLL,
        TILT
    }

    void testGenericOutput() {
        LSystem<Alphabet> ls = LSystemBuilders.<Alphabet>genericSymbols()
                .rule(Alphabet.SECOND).out(Alphabet.DRAW).out(Alphabet.MOVE)
                .rule(Alphabet.FIRST).out(Alphabet.SECOND)
                .axiom().out(Alphabet.SECOND)
                .build();

    }
    @Test
    void testExplodingOutput() {
        StringLSystem ls = LSystemBuilders.stringSymbols()
                .axiom().out("dog").exploding()
                .build();

        assertEquals("d+o+g", ls.rewrite(0, Interpreters.joining("+")));
    }
}
