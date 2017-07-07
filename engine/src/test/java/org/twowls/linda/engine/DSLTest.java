package org.twowls.linda.engine;

import org.junit.jupiter.api.Test;

/**
 * <p>Tests various aspects of DSL composition of L-Systems.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public abstract class DSLTest {

    protected abstract LSystem.Builder builder();

    @Test
    public void testExplodingOutput() {
        LSystem ls = builder()
                .axiom().out("dog").exploding()
                .build();

        // TODO check if actual output is a sequence of 'd','o','g' not a single symbol 'dog'

    }
}
