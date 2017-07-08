package org.twowls.linda.engine;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>Provides utility methods that create simple interpreter instances.</p>
 *
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
public final class Interpreters {

    /**
     * <p>Creates a simple interpreter that counts up incoming symbols.</p>
     * @return an {@code Interpreter<Long>} instance.
     */
    public static Interpreter<Long> counting() {
        return new Interpreter<>() {
            AtomicLong cnt;

            @Override
            public void before(LSystem.State state) {
                cnt.set(0L);
            }

            @Override
            public void interpret(LSystem.State state) {
                cnt.incrementAndGet();
            }

            @Override
            public Long getResult() {
                return cnt.longValue();
            }
        };
    }

    /**
     * <p>Creates a simple interpreter that joins incoming symbols into a string.</p>
     * @return an {@code Interpreter<String>} instance.
     */
    public static Interpreter<String> joining() {
        return joining(null);
    }

    /**
     * <p>Creates a simple interpreter that joins incoming symbols into a string.</p>
     * @param separator a string separating individual symbols.
     * @return an {@code Interpreter<String>} instance.
     */
    public static Interpreter<String> joining(String separator) {
        return new Interpreter<>() {
            StringBuilder builder;

            @Override
            public void before(LSystem.State state) {
                builder = new StringBuilder();
            }

            @Override
            public void interpret(LSystem.State state) {
                if (separator != null && builder.length() > 0) builder.append(separator);
                builder.append(state.sym());
            }

            @Override
            public String getResult() {
                return (builder != null ? builder.toString() : "");
            }
        };
    }

    /**
     * <p>Creates a simple interpreter that sends incoming symbols to {@code System.out}.</p>
     * @return an {@code Interpreter<Void>} instance.
     */
    public static Interpreter<Void> printing() {
        return printing(System.out);
    }

    /**
     * <p>Creates a simple interpreter that sends incoming symbols to a given stream.</p>
     * @return an {@code Interpreter<Void>} instance.
     */
    public static Interpreter<Void> printing(PrintStream printStream) {
        return new Interpreter<>() {
            @Override
            public void interpret(LSystem.State state) {
                printStream.print(state.sym());
            }

            @Override
            public void after(LSystem.State state) {
                printStream.println();
            }
        };
    }

    /* Prevents instantiation */
    private Interpreters() {}
}
