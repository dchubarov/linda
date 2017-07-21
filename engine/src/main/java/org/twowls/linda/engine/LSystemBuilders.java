package org.twowls.linda.engine;

/**
 * Contains factory methods for obtaining instances of L-System builder for various symbol types.
 *
 * @see IntLSystem.Builder
 * @see StringLSystem.Builder
 * @see LSystem.Builder
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public final class LSystemBuilders {

    /**
     * @return a builder instance for integer symbols.
     */
    public static IntLSystem.Builder intSymbols() {
        return null;
    }

    /**
     * @return a builder instance for string symbols.
     */
    public static StringLSystem.Builder stringSymbols() {
        return null;
    }

    /**
     * @return a generic builder instance.
     */
    public static <S> LSystem.Builder<S> genericSymbols() {
        return null;
    }

    /* Prevents instantiation */
    private LSystemBuilders() {}
}
