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

    @Test
    void testExplodingOutput(StringLSystem.Builder builder) {
        StringLSystem ls = builder
                .axiom().out("dog").exploding()
                .build();

        assertEquals("d+o+g", ls.rewrite(0, Interpreters.joining("+")));
    }
}
