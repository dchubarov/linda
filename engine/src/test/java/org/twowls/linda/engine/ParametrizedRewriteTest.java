package org.twowls.linda.engine;

import org.junit.jupiter.api.Test;

/**
 * <p>Tests for parametrized L-Systems.</p>
 * @author Dmitry Chubarov
 * @since 1.0.0
 */
class ParametrizedRewriteTest {

    @Test
    void testSimple(LSystem.Builder builder) {
        // @formatter:off
        LSystem ls = builder
                .rule("A").def("x", "y")
                    .when(s -> s.var("y").intVal() <= 3)
                        .out("A")
                            .fun(s -> s.wrap(s.var("x").intVal() * 2))
                            .fun(s -> s.wrap(s.var("x").intVal() + s.var("y").intVal()))
                    .otherwise()
                        .out("B")
                            .var("x")
                        .out("A")
                            .fun(s -> s.wrap(s.var("x").intVal() / s.var("y").intVal()))
                            .val(0)
                .rule("B").def("x")
                    .when(s -> s.var("x").intVal() >= 1)
                        .out("B")
                            .fun(s -> s.wrap(s.var("x").intVal() - 1))
                    .otherwise()
                        .out("C")
                .axiom()
                    .out("B").val(2)
                    .out("A").val(4).val(4)
                .build();
        // @formatter:on

        //  0:  B(2)  A(4,4)
        //  1:  B(1)  B(4)   A(1,0)
        //  2:  B(0)  B(3)   A(2,1)
        //  3:  C     B(2)   A(4,3)
        //  4:  C     B(1)   A(8,7)
        //  5:  C     B(0)   B(8)   A(1,0)
        //  6:  C     C      B(7)   A(2,1)
        //  7:  C     C      B(6)   A(4,3)
        //  8:  C     C      B(5)   A(8,7)
        //  9:  C     C      B(4)   B(8)   A(1,0)
        // 10:  C     C      B(3)   B(7)   A(2,1)
        // ...
    }
}
