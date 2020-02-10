package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class PolynomTest {

    private fun assertApproxEquals(expected: Polynom, actual: Polynom, eps: Double) {
        assertEquals(expected.degree(), actual.degree())
        for (i in 0..expected.degree()) {
            assertEquals(expected.coeff(i), actual.coeff(i), eps)
        }
    }

    @Test
    @Tag("Easy")
    fun getValue() {
        val p = Polynom(1.0, 3.0, 2.0)
        assertEquals(42.0, p.getValue(5.0), 1e-10)
        assertEquals(132.0, p.getValue(10.0), 1e-10)
        assertEquals(949650.0, p.getValue(973.0), 1e-10)
        val q = Polynom(0.0, 0.0, 0.0, 0.0)
        assertEquals(0.0, q.getValue(3.0), 1e-10)
        val r = Polynom(5.1, 0.0, 2.3, 0.0, 0.9)
        assertEquals(20008.984310000003, r.getValue(7.9), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun degree() {
        val p = Polynom(1.0, 1.0, 1.0)
        assertEquals(2, p.degree())
        val q = Polynom(0.0)
        assertEquals(0, q.degree())
        val r = Polynom(0.0, 1.0, 2.0)
        assertEquals(1, r.degree())
        val m = Polynom(9.0, 0.0, 9.0, 0.0, 9.0, 9.0)
        assertEquals(5, m.degree())
    }

    @Test
    @Tag("Easy")
    fun plus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -1.0, 2.0, 6.0)
        assertApproxEquals(r, p1 + p2, 1e-10)
        assertApproxEquals(r, p2 + p1, 1e-10)
        val p3 = Polynom(83.6, 2.0, 0.0, -1.0, 2.2, -1.0, 143.0)
        val p4 = Polynom(2.0, 42.9, 1.0, 3.0, -12.9, 0.0)
        val r1 = Polynom(83.6, 4.0, 42.9, 0.0, 5.2, -13.9, 143.0)
        assertApproxEquals(r1, p3 + p4, 1e-10)
        assertApproxEquals(r1, p4 + p3, 1e-10)
    }

    @Test
    @Tag("Easy")
    operator fun unaryMinus() {
        val p = Polynom(1.0, -1.0, 2.0)
        val r = Polynom(-1.0, 1.0, -2.0)
        assertApproxEquals(r, -p, 1e-11)
        val p1 = Polynom(83.6, 2.0, 0.0, -1.0, 2.2, -1.0, 143.0)
        val r1 = Polynom(-83.6, -2.0, 0.0, 1.0, -2.2, 1.0, -143.0)
        assertApproxEquals(r1, -p1, 1e-11)
        val p2 = Polynom(2.0, 42.9, 1.0, 3.0, -12.9, 0.0)
        val r2 = Polynom(-2.0, -42.9, -1.0, -3.0, 12.9, 0.0)
        assertApproxEquals(r2, -p2, 1e-11)
        val p3 = Polynom(0.0)
        val r3 = Polynom(0.0)
        assertApproxEquals(r3, -p3, 1e-11)
    }

    @Test
    @Tag("Easy")
    fun minus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -3.0, -4.0, 2.0)
        assertApproxEquals(r, p1 - p2, 1e-10)
        assertApproxEquals(-r, p2 - p1, 1e-10)
        val p3 = Polynom(84.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val p4 = Polynom(2.0, 42.0, 1.0, 3.0, -12.0, 0.0)
        val r1 = Polynom(84.0, 0.0, -42.0, -2.0, -1.0, 11.0, 143.0)
        assertApproxEquals(r1, p3 - p4, 1e-10)
        assertApproxEquals(-r1, p4 - p3, 1e-10)
    }

    @Test
    @Tag("Normal")
    fun times() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0)
        assertApproxEquals(r, p1 * p2, 1e-10)
        assertApproxEquals(r, p2 * p1, 1e-10)
        val p3 = Polynom(83.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val p4 = Polynom(2.0, 42.0, 1.0, 3.0, -12.0, 0.0)
        val r1 = Polynom(166.0, 3490.0, 167.0, 249.0, -1028.0, 57.0, 243.0, 6023.0, 116.0, 441.0, -1716.0, 0.0)
        assertApproxEquals(r1, p3 * p4, 1e-10)
        assertApproxEquals(r1, p4 * p3, 1e-10)
    }

    @Test
    @Tag("Hard")
    fun div() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        assertApproxEquals(r, p1 / p2, 1e-10)
        assertApproxEquals(Polynom(1.0, 2.0), Polynom(2.0, 4.0) / Polynom(0.0, 2.0), 1e-10)
        val p3 = Polynom(30.0, 15.0, 0.0, 5.0)
        val p4 = Polynom(5.0)
        val r1 = Polynom(6.0, 3.0, 0.0, 1.0)
        assertApproxEquals(r1, p3 / p4, 1e-10)
        val p5 = Polynom(83.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val p6 = Polynom(83.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val r2 = Polynom(1.0)
        assertApproxEquals(r2, p5 / p6, 1e-10)
        val p7 = Polynom(1.0, 3.0, 2.0)
        val p8 = Polynom(1.0, 3.0, 2.0)
        val r3 = Polynom(1.0)
        assertApproxEquals(r3, p7 / p8, 1e-10)
        val p9 = Polynom(1.0, 3.0, 2.0, 0.0)
        val p10 = Polynom(8.0, 0.0, 12.0, 1.0, 3.0, 2.0)
        val r4 = Polynom(0.0)
        assertApproxEquals(r4, p9 / p10, 1e-10)
    }

    @Test
    @Tag("Hard")
    fun rem() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        val q = Polynom(12.0, 14.0) //Здесь ошибка, до исправления: q = Polynom(12.0, 16.0)
        assertApproxEquals(q, p1 % p2, 1e-10)
        assertApproxEquals(p1, p2 * r + q, 1e-10)
        val p3 = Polynom(30.0, 15.0, 0.0, 5.0)
        val p4 = Polynom(5.0)
        val r1 = Polynom(6.0, 3.0, 0.0, 1.0)
        val q1 = Polynom(0.0)
        assertApproxEquals(q1, p3 % p4, 1e-10)
        assertApproxEquals(p3, p4 * r1 + q1, 1e-10)
        val p5 = Polynom(83.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val p6 = Polynom(83.0, 2.0, 0.0, -1.0, 2.0, -1.0, 143.0)
        val r2 = Polynom(1.0)
        val q2 = Polynom(0.0)
        assertApproxEquals(q2, p5 % p6, 1e-10)
        assertApproxEquals(p5, p6 * r2 + q2, 1e-10)
        val p7 = Polynom(1.0, 3.0, 2.0)
        val p8 = Polynom(1.0, 3.0, 2.0)
        val r3 = Polynom(1.0)
        val q3 = Polynom(0.0)
        assertApproxEquals(q3, p7 % p8, 1e-10)
        assertApproxEquals(p7, p8 * r3 + q3, 1e-10)
        val p9 = Polynom(1.0, 3.0, 2.0, 0.0)
        val p10 = Polynom(8.0, 0.0, 12.0, 1.0, 3.0, 2.0)
        val r4 = Polynom(0.0)
        val q4 = Polynom(1.0, 3.0, 2.0, 0.0)
        assertApproxEquals(q4, p9 % p10, 1e-10)
        assertApproxEquals(p9, p10 * r4 + q4, 1e-10)
    }

    @Test
    @Tag("Easy")
    fun equals() {
        assertEquals(Polynom(1.0, 2.0, 3.0), Polynom(1.0, 2.0, 3.0))
        assertEquals(Polynom(0.0, 2.0, 3.0), Polynom(2.0, 3.0))
    }

    @Test
    @Tag("Normal")
    fun hashCodeTest() {
        assertEquals(Polynom(1.0, 2.0, 3.0).hashCode(), Polynom(1.0, 2.0, 3.0).hashCode())
    }
}