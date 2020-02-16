@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom private constructor(private var coeFF: List<Double>) {

    constructor(vararg coeffs: Double) : this(coeffs.toList())

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coeFF[coeFF.size - i - 1]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var result = 0.0
        for (i in coeFF.indices) {
            result += x.pow(i) * coeFF[coeFF.size - i - 1]
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        for (i in coeFF.indices) {
            if (coeFF[i] != 0.0 && coeFF.size - i != 0) return coeFF.size - i - 1
        }
        return 0
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val result = mutableListOf<Double>()
        var maxI = this.coeFF
        var minI = other.coeFF
        val difference = abs(this.coeFF.size - other.coeFF.size)
        if (this.coeFF.size != max(this.coeFF.size, other.coeFF.size)) {
            maxI = other.coeFF
            minI = this.coeFF
        }
        for (i in maxI.indices) {
            if (i < difference) result.add(maxI[i])
            else result.add(maxI[i] + minI[i - difference])
        }
        return Polynom(result)
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(coeFF.map { it * (-1) })


    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this.plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        var result = Polynom(0.0)
        var maxDegreePol = this
        var minDegreePol = other
        val sumOfSize = this.coeFF.size + other.coeFF.size
        val multiple = mutableListOf<Double>()
        if (this.degree() < other.degree()) {
            maxDegreePol = other
            minDegreePol = this
        }
        for (i in maxDegreePol.coeFF.indices) {
            for (element in minDegreePol.coeFF) {
                multiple += element * maxDegreePol.coeFF[i]
            }
            while (multiple.size < sumOfSize - i - 1) multiple += 0.0
            result += Polynom(multiple)
            multiple.clear()
        }
        return result
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */

    operator fun div(other: Polynom): Polynom {
        if (this == Polynom(0.0) || this.degree() < other.degree()) return Polynom(0.0)
        if (this == other) return Polynom(1.0)
        if (other.degree() == 0) {
            val result = mutableListOf<Double>()
            for (element in this.coeFF) {
                result += element / other.coeFF.last()
            }
            return Polynom(*result.toDoubleArray())
        }
        var div = this
        val pol = mutableListOf<Double>()
        var result = Polynom(0.0)
        while (div.degree() >= other.degree()) {
            pol.clear()
            while (pol.size < div.degree() - other.degree() + 1) pol.add(0.0)
            pol[0] = div.coeff(div.degree())
            div -= other.times(Polynom(pol))
            result += Polynom(pol)
        }
        return result
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom {
        if (this.degree() < other.degree() || this == Polynom(0.0)) return this
        if (this == other || other.degree() == 0) return Polynom(0.0)
        var rem = this
        val pol = mutableListOf<Double>()
        while (rem.degree() >= other.degree()) {
            pol.clear()
            while (pol.size < rem.degree() - other.degree() + 1) pol.add(0.0)
            pol[0] = rem.coeff(rem.degree())
            rem -= other.times(Polynom(pol))
        }
        return rem
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is Polynom) return false
        if (this.degree() != other.degree()) return false
        var count1 = 0
        var count2 = 0
        while (this.coeFF[count1] == 0.0) count1++ //Ситуация, когда стоят в начале нулевые коэф.ы, но полиномы эквивалентны
        while (other.coeFF[count2] == 0.0) count2++
        while (count1 < max(this.coeFF.size, other.coeFF.size)) {
            if (this.coeFF[count1] != other.coeFF[count2]) return false
            count1++
            count2++
        }
        return true
    }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var result = degree()
        for (i in this.coeFF.indices) {
            if (this.coeFF[i] != 0.0) {
                result += 31 * result + this.coeFF[i].hashCode()
            }
        }
        return result
    }
}
