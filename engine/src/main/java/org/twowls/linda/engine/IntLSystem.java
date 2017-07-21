package org.twowls.linda.engine;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Specialized L-System whose symbols are {@code int}s.
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public interface IntLSystem extends LSystem<Integer> {
    interface Builder extends LSystem.Builder<Integer> {
        @Override
        Builder axiom();

        Builder rule(int symbol);

        @Override
        Builder def(String... names);

        @Override
        Builder fun(Function<State<Integer>, State.Var> fn);

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
        Builder when(Function<State<Integer>, Boolean> fn);

        @Override
        Builder precedes(Integer symbol);

        default Builder precedes(int symbol) {
            return precedes(Integer.valueOf(symbol));
        }

        /**
         * Allows to specify a sequence of symbols that current symbol precedes.
         * @param symbols symbols to be matched.
         * @return the current {@code Builder} instance.
         */
        default Builder precedes(int... symbols) {
            IntStream.of(symbols).forEach(this::precedes);
            return this;
        }

        @Override
        Builder follows(Integer symbol);

        default Builder follows(int symbol) {
            return follows(Integer.valueOf(symbol));
        }

        /**
         * Allows to specify a sequence of symbols that current symbol follows.
         * @param symbols symbols to be matched.
         * @return the current {@code Builder} instance.
         */
        default Builder follows(int... symbols) {
            IntStream.of(symbols).forEach(this::follows);
            return this;
        }

        @Override
        Builder skipping(Integer symbol);

        default Builder skipping(int symbol) {
            return skipping(Integer.valueOf(symbol));
        }

        /**
         * Allows to skip a sequence of symbols.
         * @param symbols symbols to be skipped during context matching.
         * @return the current {@code Builder} instance.
         */
        default Builder skipping(int... symbols) {
            IntStream.of(symbols).forEach(this::skipping);
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
        Builder out(Integer symbol);

        /**
         * Output a single int symbol.
         * @param symbol a symbol to output.
         * @return the current {@code Builder} instance.
         */
        default Builder out(int symbol) {
            return out(Integer.valueOf(symbol));
        }

        /**
         * Output a sequence of symbols.
         * @param symbols the symbols to output.
         * @return the current {@code Builder} instance.
         */
        default Builder out(int... symbols) {
            IntStream.of(symbols).forEach(this::out);
            return this;
        }

        @Override
        IntLSystem build();
    }
}
