package org.twowls.linda.engine;

/**
 * <p>Holds state of an executing rewriting process.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public interface RewriteContext {

    /**
     * <p>Retrieves a variable by its name.</p>
     * @param symbol the variable name, must not be {@code null}.
     * @return a {@code Var} representing the variable.
     */
    Var var(String symbol);

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
