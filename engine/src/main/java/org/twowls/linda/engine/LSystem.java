package org.twowls.linda.engine;

import java.util.function.Function;

/**
 * Lindenmayer system (L-System) API.
 *
 * @param <S> the type of a single symbol
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public interface LSystem<S> {

    /**
     * Rewrites the current L-System specified number of derivations.
     * @param derivations number of derivations, must be zero or positive.
     * @param interpreter an object interpreting rewriting result, must not be {@code null}.
     * @param <R> the type of {@code interpreter} result.
     * @return the result of the interpreter.
     */
    <R> R rewrite(int derivations, Interpreter<S, R> interpreter);

    /**
     * Provides methods allowing building of an <i>L-System</i> in the DSL fashion.
     * @param <S> the type of a single symbol.
     */
    interface Builder<S> {

        /**
         * Begins definition of the axiom.
         * @return the current {@code Builder} instance.
         */
        Builder<S> axiom();

        /**
         * Begins definition of a named rule.
         * @param symbol a symbol identifying the rule.
         * @return the current {@code Builder} instance.
         */
        Builder<S> rule(S symbol);

        /**
         * Defines one or more variables.
         * @param names variable names
         * @return the current {@code Builder} instance.
         */
        Builder<S> def(String... names);

        /**
         * Instructs engine to inject the result of function execution into current scope.
         * @param fn the function to execute
         * @return the current {@code Builder} instance.
         */
        Builder<S> fun(Function<State<S>, State.Var> fn);

        /**
         * Instructs engine to pass the value of a variable into current scope.
         * @param name the name of variable.
         * @return the current {@code Builder} instance.
         */
        Builder<S> var(String name);

        /**
         * Instructs engine to inject the fixed boolean value into current scope.
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder<S> val(boolean value);

        /**
         * Instructs engine to inject the fixed double value into current scope.
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder<S> val(double value);

        /**
         * Instructs engine to inject the fixed int value into current scope.
         * @param value the value to inject.
         * @return the current {@code Builder} instance.
         */
        Builder<S> val(int value);

        /**
         * Begins a logical branch of a named rule that gets executed with certain probability.
         * @param probability the probability, must be in range 0..1 exclusive.
         * @return the current {@code Builder} instance.
         */
        Builder<S> probably(double probability);

        /**
         * Begins a logical branch of a named rule that only gets executed if given condition is met.
         * @param fn a function that evaluates a condition against current state of the rewrite process.
         * @return the current {@code Builder} instance.
         */
        Builder<S> when(Function<State<S>, Boolean> fn);

        /**
         * Begins a logical branch of a named rule that only gets executed if symbol that is currently
         * being processed precedes the specified sequence of symbols.
         * @param symbol one of more symbols forming <i>left-context</i> of the current symbol.
         * @return the current {@code Builder} instance.
         */
        Builder<S> precedes(S symbol);

        /**
         * Begins a logical branch of a named rule that only gets executed if symbol that is currently
         * being processed follows the specified sequence of symbols.
         * @param symbol one of more symbols forming <i>right-context</i> of the current symbol.
         * @return the current {@code Builder} instance.
         */
        Builder<S> follows(S symbol);

        /**
         * Specifies one or more symbols that should be ignored during context matching defined
         * by {@link #precedes} or {@link #follows}.
         * @param symbol one or more symbols to ignore.
         * @return the current {@code Builder} instance.
         */
        Builder<S> skipping(S symbol);

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder<S> not();

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder<S> and();

        /**
         *
         * @return the current {@code Builder} instance.
         */
        Builder<S> or();

        /**
         * Begins a branch of a named rule that gets executed if no conditional branch defined
         * by ({@link #probably(double)}, {@link #when(Function)}, {@link #precedes},
         * {@link #follows}) fit execution conditions.
         * @return the current {@code Builder} instance.
         */
        Builder<S> otherwise();

        /**
         * Defines a sequence of symbols to be output.
         * @return the current {@code Builder} instance.
         */
        Builder<S> out(S symbol);

        /**
         * Builds the L-System based on data supplied by invoking other {@code Builder} methods.
         * @return the built L-System
         */
        LSystem<S> build();
    }

    /**
     * Holds state of an executing rewriting process.
     */
    interface State<S> {

        /**
         * @return number of symbols produced so far.
         */
        long seq();

        /**
         * Tests whether current symbol is equal to {@code symbol}.
         * Equivalent to {@code Objects.equals(s.sym(), symbol)}.
         * @param symbol a symbol to compare to.
         * @return {@code true} if current symbol equals {@code symbol}, otherwise {@code false}.
         */
        boolean is(S symbol);

        /**
         * @return the current symbol
         */
        S sym();

        /**
         * Retrieves a variable by its name.
         * @param name the variable name, must not be {@code null}.
         * @return a {@code Var} representing the variable.
         */
        Var var(String name);

        /**
         * Updates a variable value.
         * @param name the variable name, must not be {@code null}.
         * @param v a {@code Var} containing new value.
         */
        void set(String name, Var v);

        /**
         * Wraps a {@code boolean} value into anonymous variable.
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(boolean value);

        /**
         * Wraps a {@code double} value into anonymous variable.
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(double value);

        /**
         * Wraps an {@code int} value into anonymous variable.
         * @param value the value to be wrapped.
         * @return a {@code Var} containing given value.
         */
        Var wrap(int value);

        /**
         * Represents a variable within a rewrite context.
         */
        interface Var {

            /**
             * Returns variable value as a {@code boolean}.
             * @return a {@code boolean} value of the variable.
             */
            boolean booleanVal();

            /**
             * Returns variable value as a {@code double}.
             * @return a {@code double} value of the variable.
             */
            double doubleVal();

            /**
             * Returns variable value as an {@code int}.
             * @return a {@code int} value of the variable.
             */
            int intVal();

            /**
             * Compares this variable's value to the other's.
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
