package org.twowls.linda.engine;

import java.util.function.Function;

/**
 * <p>Lindenmayer system (L-System) API.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public interface LSystem {

    /**
     * <p>Rewrites the current L-System specified number of derivations.</p>
     * @param derivations number of derivations, must be zero or positive.
     * @param interpreter an object interpreting rewriting result, must not be {@code null}.
     * @param <R> the type of {@code interpreter} result.
     * @return the result of the interpreter.
     */
    <R> R rewrite(int derivations, Interpreter<R> interpreter);

    /**
     * <p>Provides methods allowing building of an <i>L-System</i> in the DSL fashion.</p>
     */
    interface Builder {

        /**
         * <p>Begins definition of the axiom.</p>
         * @return the current {@code Builder} instance.
         */
        Builder axiom();

        /**
         * <p>Begins definition of a named rule.</p>
         * @param symbol a symbol identifying the rule.
         * @return the current {@code Builder} instance.
         */
        Builder rule(String symbol);

        /**
         * <p>Defines one or more variables.</p>
         * @param symbols variable names
         * @return the current {@code Builder} instance.
         */
        Builder def(String... symbols);

        /**
         * <p>Instructs engine to inject the result of function execution into current scope.</p>
         * @param fn the function to execute
         * @return the current {@code Builder} instance.
         */
        Builder fun(Function<State, State.Var> fn);

        /**
         * <p>Instructs engine to pass the value of a variable into current scope.</p>
         * @param symbol the name of variable.
         * @return the current {@code Builder} instance.
         */
        Builder var(String symbol);

        /**
         * <p>Instructs engine to inject the fixed boolean value into current scope.</p>
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder val(boolean value);

        /**
         * <p>Instructs engine to inject the fixed double value into current scope.</p>
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder val(double value);

        /**
         * <p>Instructs engine to inject the fixed int value into current scope.</p>
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder val(int value);

        /**
         * <p>Begins a logical branch of a named rule that gets executed with certain probability.</p>
         * @param probability the probability, must be in range 0..1 exclusive.
         * @return the current {@code Builder} instance.
         */
        Builder probably(double probability);

        /**
         * <p>Begins a logical branch of a named rule that only gets executed if given condition is met.</p>
         * @param fn a function that evaluates a condition against current state of the rewrite process.
         * @return the current {@code Builder} instance.
         */
        Builder when(Function<State, Boolean> fn);

        /**
         * <p>Begins a logical branch of a named rule that only gets executed if symbol that is currently
         * being processed precedes the specified sequence of symbols.</p>
         * @param symbols one of more symbols forming <i>left-context</i> of the current symbol.
         * @return the current {@code Builder} instance.
         */
        Builder precedes(String... symbols);

        /**
         * <p>Begins a logical branch of a named rule that only gets executed if symbol that is currently
         * being processed follows the specified sequence of symbols.</p>
         * @param symbols one of more symbols forming <i>right-context</i> of the current symbol.
         * @return the current {@code Builder} instance.
         */
        Builder follows(String... symbols);

        /**
         * <p>Specifies one or more symbols that should be ignored during context matching defined
         * by {@link #precedes(String...)} or {@link #follows(String...)}.</p>
         * @param symbols one or more symbols to ignore.
         * @return the current {@code Builder} instance.
         */
        Builder skipping(String... symbols);

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder not();

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder and();

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder or();

        /**
         * <p>Begins a branch of a named rule that gets executed if no conditional branch defined
         * by ({@link #probably(double)}, {@link #when(Function)}, {@link #precedes(String...)},
         * {@link #follows(String...)}) fit execution conditions.</p>
         * by {@link #precedes(String...)} or {@link #follows(String...)}.</p>
         * @return the current {@code Builder} instance.
         */
        Builder otherwise();

        /**
         * <p>Defines a sequence of symbols to be output.</p>
         * @return the current {@code Builder} instance.
         */
        Builder out(String... symbols);

        /**
         * <p>Instructs that last sequence of symbols given by {@link #out(String...)} should be
         * exploded before output into parts marked by a {@code delimiter}.</p>
         * @param delimiter the delimiter that separates symbols in a monolithic string.
         * @return the current {@code Builder} instance.
         */
        Builder exploding(String delimiter);

        /**
         * <p>Instructs that last sequence of symbols given by {@link #out(String...)} should be
         * exploded into sequence of <i>valid-symbols</i>.</p>
         * @return the current {@code Builder} instance.
         */
        Builder exploding();

        /**
         * <p>Builds the L-System based on data supplied by invoking other {@code Builder} methods.</p>
         * @return the built L-System
         */
        LSystem build();
    }

    /**
     * <p>Holds state of an executing rewriting process.</p>
     */
    interface State {

        /**
         * @return number of symbols produced so far.
         */
        long seq();

        /**
         * <p>Tests whether current symbol is equal to {@code symbol}.
         * Equivalent to {@code Objects.equals(s.sym(), symbol)}.</p>
         * @param symbol a symbol to compare to.
         * @return {@code true} if current symbol equals {@code symbol}, otherwise {@code false}.
         */
        boolean is(String symbol);

        /**
         * @return the current symbol
         */
        String sym();

        /**
         * <p>Retrieves a variable by its name.</p>
         * @param symbol the variable name, must not be {@code null}.
         * @return a {@code Var} representing the variable.
         */
        Var var(String symbol);

        /**
         * <p>Updates a variable value.</p>
         * @param symbol the variable name, must not be {@code null}.
         * @param v a {@code Var} containing new value.
         */
        void set(String symbol, Var v);

        /**
         * <p>Wraps a {@code boolean} value into anonymous variable.</p>
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(boolean value);

        /**
         * <p>Wraps a {@code double} value into anonymous variable.</p>
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(double value);

        /**
         * <p>Wraps an {@code int} value into anonymous variable.</p>
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(int value);

        /**
         * <p>Represents a variable within a rewrite context.</p>
         */
        interface Var {

            /**
             * <p>Returns variable value as a {@code boolean}.</p>
             * @return a {@code boolean} value of the variable.
             */
            boolean booleanVal();

            /**
             * <p>Returns variable value as a {@code double}.</p>
             * @return a {@code double} value of the variable.
             */
            double doubleVal();

            /**
             * <p>Returns variable value as an {@code int}.</p>
             * @return a {@code int} value of the variable.
             */
            int intVal();

            /**
             * <p>Compares this variable's value to the other's.</p>
             * @param other a variable to compare this one to.
             * @return zero if variables are equal, negative value if this variable less than the {@code other},
             *  or positive if this variable is greater than the {@code other}.
             */
            int compareTo(Var other);

            /**
             * @param other a variable to compare this one to.
             * @return {@code true} if this variable's value is greater than {@code other}'s, otherwise {@code false}.
             */
            default boolean greaterThan(Var other) {
                return (compareTo(other) > 0);
            }

            /**
             * @param other a variable to compare this one to.
             * @return {@code true} if this variable's value is greater than
             *  or equal to {@code other}'s, otherwise {@code false}.
             */
            default boolean greaterThanOrEqual(Var other) {
                return (compareTo(other) >= 0);
            }

            /**
             * @param other a variable to compare this one to.
             * @return {@code true} if this variable's value is less than to {@code other}'s, otherwise {@code false}.
             */
            default boolean lessThan(Var other) {
                return (compareTo(other) < 0);
            }

            /**
             * @param other a variable to compare this one to.
             * @return {@code true} if this variable's value is less than or equal
             *  to {@code other}'s, otherwise {@code false}.
             */
            default boolean lessThanOrEqual(Var other) {
                return (compareTo(other) <= 0);
            }
        }
    }
}
