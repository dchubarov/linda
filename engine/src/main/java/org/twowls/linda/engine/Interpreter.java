package org.twowls.linda.engine;

import static java.util.Objects.requireNonNull;

/**
 * <p>An object that interprets a <i>production</i> of rewriting engine. Production consists
 * of a current symbol, its context (neighboring symbols) and state (variables).</p>
 *
 * @param <R> the type of result value
 * @param <S> the type of a single symbol
 * @see LSystem#rewrite(int, Interpreter)
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
@FunctionalInterface
public interface Interpreter<S, R> {

    /**
     * <p>Invoked by rewriting engine when production is ready and needs to be interpreted.</p>
     * @param state an object containing state of the rewriting process.
     */
    void interpret(LSystem.State<S> state);

    /**
     * <p>Invoked by rewriting engine before rewriting process start.</p>
     * @param state an object containing initial state of the rewriting process.
     */
    default void before(LSystem.State<S> state) {
        // default implementation does not take any action
    }

    /**
     * <p>Invoked by rewriting engine after rewriting process is finished even if no productions were done.</p>
     * @param state an object containing final state of the rewriting process.
     */
    default void after(LSystem.State<S> state) {
        // default implementation does not take any action
    }

    /**
     * @return the result of interpretation, if any.
     */
    default R getResult() {
        return null;
    }

    /**
     * Creates a combined interpreter that calls this interpreter first and then the {@code other}.
     * @param other the other interpreter, must not be {@code null}.
     * @return a combined interpreter instance.
     */
    default Interpreter<S, R> andThen(Interpreter<S, ?> other) {
        requireNonNull(other);
        Interpreter<S, R> self = this;
        return new Interpreter<>() {
            @Override
            public void interpret(LSystem.State<S> state) {
                self.interpret(state); other.interpret(state);
            }

            @Override
            public void before(LSystem.State<S> state) {
                self.before(state); self.before(state);
            }

            @Override
            public void after(LSystem.State<S> state) {
                self.after(state); other.after(state);
            }

            @Override
            public R getResult() {
                return self.getResult();
            }
        };
    }
}
