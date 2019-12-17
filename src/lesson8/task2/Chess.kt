@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson8.task1.Point
import lesson8.task1.lineByPoints
import javax.swing.text.Segment
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        if (column !in 1..8 || row !in 1..8) return ""
        val result = 'a' + column - 1
        return result.toString() + row.toString()
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    require(notation.contains(Regex("""[a-h]+[1-8]+""")))
    return Square(column = notation.first() - 'a' + 1, row = notation[1].toString().toInt())
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    require(!(start.column !in 1..8 || start.row !in 1..8 || end.column !in 1..8 || end.row !in 1..8))
    return if (start.column == end.column && start.row == end.row) 0
    else if (start.column == end.column || start.row == end.row) 1
    else 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf<Square>()
    if (start.row == end.row && start.column == end.column) {
        result.add(start)
        return result
    }
    if (start.row == end.row || start.column == end.column) {
        result.add(start)
        result.add(end)
        return result
    }
    if (start.row != end.row && start.column != end.column) {
        result.add(start)
        result.add(Square(start.column, end.row))
        result.add(end)
    }
    return result
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    require(!(start.column !in 1..8 || start.row !in 1..8 || end.column !in 1..8 || end.row !in 1..8))
    if (start.column == end.column && start.row == end.row) return 0
    if (abs(start.column - end.column) == abs(start.row - end.row)) return 1
    if ((start.column + start.row) % 2 == (end.column + end.row) % 2) return 2
    return -1
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf(start)
    if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return listOf()
    if (start.row == end.row && start.column == end.column) {
        return result
    }
    if (abs(start.row - end.row) == abs(start.column - end.column)) {
        result.add(end)
        return result
    }
    val changeOfDiagonal = abs(abs(start.row - end.row) - abs(start.column - end.column)) / 2
    var column: Int
    var row: Int
    if (start.row == end.row) {
        row = if (start.row + changeOfDiagonal <= 8) start.row + changeOfDiagonal
        else start.row - changeOfDiagonal
        column = start.column + changeOfDiagonal
        result.add(Square(column, row))
    }
    if (start.column == end.column) {
        row = start.row + changeOfDiagonal
        column = if (start.column + changeOfDiagonal <= 8) start.column + changeOfDiagonal
        else start.column - changeOfDiagonal
        result.add(Square(column, row))
    }
    if (start.column < end.column && start.row > end.row) {
        if (end.row + changeOfDiagonal <= 8) {
            row = start.row + changeOfDiagonal
            column = start.column + changeOfDiagonal
        } else {
            row = end.row - changeOfDiagonal
            column = end.column - changeOfDiagonal
        }
        result.add(Square(column, row))
    }
    if (start.column < end.column && start.row < end.row) {
        if (end.row + changeOfDiagonal <= 8) {
            row = end.row - changeOfDiagonal
            column = end.column + changeOfDiagonal
        } else {
            row = start.row + changeOfDiagonal
            column = start.column - changeOfDiagonal
        }
        result.add(Square(column, row))
    }
    if (start.column > end.column && start.row > end.row) {
        if (start.row + changeOfDiagonal <= 8) {
            row = start.row + changeOfDiagonal
            column = start.column - changeOfDiagonal
        } else {
            row = end.row - changeOfDiagonal
            column = end.column + changeOfDiagonal
        }
        result.add(Square(column, row))
    }
    if (start.column > end.column && start.row < end.row) {
        if (end.row + changeOfDiagonal <= 8) {
            row = end.row + changeOfDiagonal
            column = end.column + changeOfDiagonal
        } else {
            row = start.row - changeOfDiagonal
            column = start.column - changeOfDiagonal
        }
        result.add(Square(column, row))
    }
    result.add(end)
    return result
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    require(!(start.column !in 1..8 || start.row !in 1..8 || end.column !in 1..8 || end.row !in 1..8))
    if (start.column == end.column) return abs(end.row - start.row)
    if (start.row == end.row) return abs(end.column - start.column)
    if (start.column != end.column && start.row != end.row) {
        val column = abs(start.column - end.column)
        val row = abs(start.row - end.row)
        val min = minOf(column, row)
        val plusRow = if (start.row > end.row) start.row - min
        else start.row + min
        val rlyPlusRow = abs(plusRow - end.row)
        return column + rlyPlusRow
    }
    return 0
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    var count: Int
    val result = mutableListOf(start)
    var row: Int
    var column: Int
    if (start.column == end.column) {
        count = abs(end.row - start.row)
        row = start.row
        while (count > 0) {
            if (start.row > end.row) row -= 1
            else row++
            column = start.column
            result.add(Square(column, row))
            count -= 1
        }
    }
    if (start.row == end.row) {
        count = abs(end.column - start.column)
        column = start.column
        while (count > 0) {
            row = start.row
            if (start.column > end.column) column -= 1
            else column++
            result.add(Square(column, row))
            count -= 1
        }
    }
    if (start.column != end.column && start.row != end.row) {
        count = abs(start.column - end.column)
        column = start.column
        row = start.row
        while (count > 0) {
            if (start.row > end.row) row -= 1
            else row++
            if (start.column > end.column) column -= 1
            else column++
            result.add(Square(column, row))
            count -= 1
        }
        if (row != end.row) {
            var row1 = abs(row - end.row)
            var row2 = row
            while (row1 > 0) {
                if (row > end.row) row2 -= 1
                else row2++
                result.add(Square(column, row2))
                row1 -= 1
            }
        }
        return result
    }
    if (start.column != end.column && start.row != end.row) result.add(end)
    return result
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()
