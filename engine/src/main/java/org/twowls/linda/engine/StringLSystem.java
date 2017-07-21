package org.twowls.linda.engine;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Specialized L-System whose symbols are {@code String}s.
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public interface StringLSystem extends LSystem<String> {
    interface Builder extends LSystem.Builder<String> {

        @Override
        Builder axiom();

        @Override
        Builder rule(String symbol);

        @Override
        Builder def(String... names);

        @Override
        Builder fun(Function<State<String>, State.Var> fn);

        @Override
        Builder var(String name);

        @Override
        Builder val(int value);

        @Override
        Builder val(double value);

        @Override
        Builder val(boolean value);

        @Override
        Builder probably(double probability);

        @Override
        Builder when(Function<State<String>, Boolean> fn);

        @Override
        Builder precedes(String symbol);

        /**
         * Allows to specify a sequence of symbols that current symbol precedes.
         * @param symbols symbols to be matched.
         * @return the current {@code Builder} instance.
         */
        default Builder precedes(String... symbols) {
            Stream.of(symbols).forEach(this::precedes);
            return this;
        }

        @Override
        Builder follows(String symbol);

        /**
         * Allows to specify a sequence of symbols that current symbol follows.
         * @param symbols symbols to be matched.
         * @return the current {@code Builder} instance.
         */
        default Builder follows(String... symbols) {
            Stream.of(symbols).forEach(this::follows);
            return this;
        }

        @Override
        Builder skipping(String symbol);

        /**
         * Allows to skip a sequence of symbols.
         * @param symbols symbols to be skipped during context matching.
         * @return the current {@code Builder} instance.
         */
        default Builder skipping(String... symbols) {
            Stream.of(symbols).forEach(this::skipping);
            return this;
        }

        @Override
        Builder not();

        @Override
        Builder and();

        @Override
        Builder or();

        @Override
        Builder otherwise();

        @Override
        Builder out(String symbol);

        /**
         * Output a sequence of symbols.
         * @param symbols the symbols to output.
         * @return the current {@code Builder} instance.
         */
        default Builder out(String... symbols) {
            Stream.of(symbols).forEach(this::out);
            return this;
        }

        /**
         * Instructs that last sequence of symbols given by {@link #out(String)} should be
         * exploded before output into parts marked by a {@code delimiter}.
         * @param delimiter the delimiter that separates a monolithic string into symbols.
         * @return the current {@code Builder} instance.
         */
        Builder exploding(String delimiter);

        /**
         * Instructs that last sequence of symbols given by {@link #out(String)} should be
         * exploded before output into single characters.
         * @return the current {@code Builder} instance.
         */
        Builder exploding();

        @Override
        StringLSystem build();
    }
}
