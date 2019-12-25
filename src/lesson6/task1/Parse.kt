@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson5.task1.buildGrades
import lesson5.task1.removeFillerWords
import java.lang.IndexOutOfBoundsException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = mapOf(
    "" to 0, "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5, "июня" to 6,
    "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12
)

fun dateStrToDigit(str: String): String {
    val split = str.split(" ")
    if (str.isEmpty() || split.size != 3) return ""
    try {
        val day = split[0].toInt()
        val year = split[2].toInt()
        if (split[1] in months && day in 1..31 && split.size == 3) {
            val month = months[split[1]]
            if (day > daysInMonth(month!!, year) || day < 0 || month !in 1..12) return ""
            return String.format("%02d.%02d.%d", day, month, year)
        }
    } catch (e: NumberFormatException) {
        return ""
    }
    return ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
val reMonths = buildGrades(months)

fun dateDigitToStr(digital: String): String {
    val split = digital.split(".")
    if (digital.isEmpty() || split.size != 3 || split[0].contains(Regex("""\D"""))
        || split[1].contains(Regex("""\D""")) || split[2].contains(Regex("""\D"""))
    ) return ""
    val day = split[0].toInt()
    val month = split[1].toInt()
    val year = split[2].toInt()
    if (day > daysInMonth(month, year) || day < 0 || month !in 1..12 || split.size != 3) return ""
    val count = reMonths.getValue(month).joinToString()
    if (day in 1..31 && month in 1..12 && split.size == 3) {
        return String.format("%2d %s %d", day, count, year).trim()
    }
    return ""
}


/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val result = mutableListOf<Char>()
    val symbolNot = setOf('-', '(', ')', ' ')
    if (!phone.matches(Regex("""(\+?\d+)?[\d\s\-]*(\([\d\s\-]+\))?[\d\s\-]*"""))
        || phone.matches(Regex("""\s"""))) return ""
    for (char in phone) {
        if (char !in symbolNot) result.add(char)
    }
    return result.joinToString("")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (jumps.isEmpty()) return -1
    val symbolNot = setOf("-", "%")
    var result = -1
    val split = jumps.split(" ")
    if (!jumps.matches(Regex("""((\d+|[-%])\s)*(\d+|[-%])"""))) return -1
    for (element in split) {
        if (element !in symbolNot && element.toInt() > result) result = element.toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var result = -1
    val split = jumps.split(" ")
    if (!jumps.matches(Regex("""((\d+\s[-%+]+\s)*(\d+\s[-%+]+))"""))) {
        return -1
    }
    for (i in 0..split.size - 2) {
        if (split[i + 1] == "+" && split[i].toInt() > result) result = split[i].toInt()
    }
    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val split = expression.split(" ")
    var result = 0
    require(expression.matches(Regex("""(\d+\s[+-]\s)*\d+""")))
    result += split[0].toInt()
    for (i in 2 until split.size) {
        if (i % 2 == 0 && split[i - 1] == "+") result += split[i].toInt()
        if (i % 2 == 0 && split[i - 1] == "-") result -= split[i].toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var result = ""
    var price = 0.0
    val split = description.split("; ")
    if (description.isEmpty()) return result
    for (element in split) {
        val elem = element.split(" ")
        if (elem[1].toDouble() >= price) {
            price = elem[1].toDouble()
            result = elem[0]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman.isEmpty()) return -1
    var index = 0
    var index1 = 0
    var index2 = 0
    val count: Char = roman.last()
    val list = mutableListOf<Char>()
    var minusNumber = 0
    val romanNumber = setOf(
        "", "I", "V", "X", "L",
        "C", "D", "M"
    )
    val arab = listOf(0, 1, 5, 10, 50, 100, 500, 1000)
    for (element in roman) {
        if (element.toString() !in romanNumber) return -1
        list.add(element)
    }
    for (element in romanNumber) {
        if (count.toString() == element) break
        else index2++
    }
    var result = arab[index2]
    for (i in 0..list.size - 2) {
        for (element in romanNumber) {
            if (list[i].toString() == element) break
            else index++
        }
        for (element in romanNumber) {
            if (list[i + 1].toString() == element) break
            else index1++
        }
        if (index1 > index) {
            minusNumber += arab[index]
        } else result += arab[index]
        index = 0
        index1 = 0
    }
    return result - minusNumber
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
