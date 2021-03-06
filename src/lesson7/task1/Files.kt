@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (word in substrings) {
        result[word] = 0
    }
    val reader = File(inputName).readLines()
    var count = 0
    for (line in reader) {
        for (word in line.split(Regex("""\s"""))) {
            for ((keys) in result) {
                val partOfWord = word.toLowerCase().windowed(keys.length, step = 1)
                for (part in partOfWord) {
                    if (keys.toLowerCase() in part) count++
                    if (count > 0) {
                        result[keys] = result[keys]!! + count
                        count = 0
                    }
                }
            }
        }
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines()
    val maxLength = reader.maxBy { it.trim().length }?.trim()?.length ?: 0
    val space = StringBuilder()
    for (line in reader) {
        if (reader.size == 1) {
            writer.write(line.trim())
            break
        }
        while ((space.length * 2 + 1) + line.trim().length < maxLength) space.append(" ")
        writer.write(space.toString())
        writer.write(line.trim())
        space.clear()
        writer.newLine()
    }
    writer.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val reader = File(inputName).readLines()
    val writer = File(outputName).bufferedWriter()
    val maxLength =
        reader.maxBy { it.trim().replace(" ", "").length + it.trim().split(" ").toList().size - 1 }?.trim()?.length ?: 0
    var count = 0
    for (line in reader) {
        val line1 = line.trim()
        if (line1.split(" ").size == 1 || line1.length == maxLength || line1 == "") {
            writer.write(line1)
            writer.newLine()
        } else {
            val lineLength = line.trim().replace(" ", "").length
            val word = line.trim().split(" ").filter { it.isNotEmpty() }.toList()
            val wordsInLine = word.size
            val spaceDiv = (maxLength - lineLength) / (wordsInLine - 1)
            var spaceMod = (maxLength - lineLength) % (wordsInLine - 1)
            val result = StringBuilder()
            for (i in 0 until wordsInLine) {
                result.append(word[i])
                while (count < spaceDiv) {
                    result.append(" ")
                    count++
                }
                count = 0
                if (spaceMod > 0) {
                    result.append(" ")
                    spaceMod -= 1
                }
            }
            writer.write(result.toString().trim())
            writer.newLine()
        }
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val wordArray = mutableMapOf<String, Int>()
    val reader = File(inputName).readLines()
    for (line in reader) {
        val line1 = line.toLowerCase().split(Regex("""[^а-яёa-z]+"""))
        for (word in line1) {
            if (word != "") wordArray[word] = wordArray.getOrDefault(word, 0) + 1
        }
    }
    if (wordArray.size <= 20) return wordArray
    return wordArray.toList().sortedBy { it.second }.reversed().take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val reader = File(inputName).readLines()
    val writer = File(outputName).bufferedWriter()
    val result = mutableListOf<String>()
    var maxLength = 0
    for (line in reader) {
        if (line.length == maxLength && line.length == line.toLowerCase().toSet().size) {
            result += line
        }
        if (line.length > maxLength && line.length == line.toLowerCase().toSet().size) {
            maxLength = line.length
            result.clear()
            result += line
        }
    }
    writer.write(result.joinToString(", "))
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val numOfDigitLhv = digitNumber(lhv)
    val numOfDigitRhv = digitNumber(rhv)
    val sb = StringBuilder()
    val numOfDigitMpp = digitNumber(lhv * rhv)
    var space = numOfDigitMpp - numOfDigitLhv + 1
    while (space > 0) {
        sb.append(" ")
        space -= 1
    }
    writer.write(sb.append(lhv).toString())
    sb.clear().append("*")
    writer.newLine()
    space = numOfDigitMpp - numOfDigitRhv
    while (space > 0) {
        sb.append(" ")
        space -= 1
    }
    writer.write(sb.append(rhv).toString())
    sb.clear()
    var lines = numOfDigitMpp + 1
    while (lines > 0) {
        sb.append("-")
        lines -= 1
    }
    writer.newLine()
    writer.write(sb.toString())
    sb.clear()
    var result = rhv % 10 * lhv
    space = numOfDigitMpp - digitNumber(result) + 1
    while (space > 0) {
        sb.append(" ")
        space -= 1
    }
    writer.newLine()
    writer.write(sb.append(result).toString())
    for (i in 1 until numOfDigitRhv) {
        result = rhv / 10.0.pow(i).toInt() % 10 * lhv
        writer.newLine()
        sb.clear().append("+")
        space = numOfDigitMpp - digitNumber(result) - i
        while (space > 0) {
            sb.append(" ")
            space -= 1
        }
        writer.write(sb.append(result).toString())
        sb.clear()
    }
    lines = numOfDigitMpp + 1
    sb.clear()
    writer.newLine()
    while (lines > 0) {
        sb.append("-")
        lines -= 1
    }
    writer.write(sb.toString())
    sb.clear()
    writer.newLine()
    writer.write(sb.append(" ").append(lhv * rhv).toString())
    writer.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun digitOfNumber(number: Int, numberSize: Int): List<Int> {
    val result = mutableListOf<Int>()
    var number1 = number
    for (i in 0 until numberSize) {
        result += number1 % 10
        number1 /= 10
    }
    return result.reversed()
}

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val divRes = lhv / rhv
    val digitsLhv = digitOfNumber(lhv, digitNumber(lhv))
    val digitsDivRes = digitOfNumber(divRes, digitNumber(divRes))
    val sb1 = StringBuilder()
    val sb2 = StringBuilder()
    val sb3 = StringBuilder()
    var indexLhv = 0
    var indexRhv = 1
    var space = 0
    var number = 0
    var count = 0
    var string = ""
    var length: Int
    for (digit in digitsLhv) {
        count++
        string += digit
        number = string.toInt()
        indexLhv++
        if (number / rhv > 0) break
        else number = lhv
    }
    var minusNumber = rhv * digitsDivRes.first()
    var minusNumberSize = digitNumber(minusNumber)
    var numberSize = digitNumber(number)
    if (count == digitNumber(minusNumber)) {
        sb1.append(" ").append(lhv)
        minusNumberSize++
    } else sb1.append(lhv)
    sb3.append(sb1).append(" | ").append(rhv)
    sb2.append("-").append(minusNumber)
    length = sb1.length
    sb1.clear()
    while (sb1.length + sb2.length < numberSize) sb1.append(" ")
    sb1.append(sb2)
    while (sb1.length < length + 3) sb1.append(" ")
    sb1.append(divRes)
    writer.write(sb3.toString())
    writer.newLine()
    writer.write(sb1.toString())
    writer.newLine()
    while (indexLhv <= digitsLhv.size) {
        sb1.clear()
        sb2.clear()
        while (sb1.length < max(minusNumberSize, numberSize)) sb1.append("-")
        while (sb2.length < space) sb2.append(" ")
        length = sb2.length + sb1.length
        writer.write(sb2.append(sb1).toString())
        writer.newLine()
        sb1.clear()
        sb2.clear()
        val differenceNumber = number - minusNumber
        sb1.append(differenceNumber)
        while (sb2.length + differenceNumber.toString().length < length) sb2.append(" ")
        sb2.append(sb1)
        if (digitsLhv.size <= indexLhv) {
            writer.write(sb2.toString())
            break
        }
        sb2.append(digitsLhv[indexLhv])
        length = sb2.length
        writer.write(sb2.toString())
        sb2.clear()
        sb1.clear()
        writer.newLine()
        number = differenceNumber * 10 + digitsLhv[indexLhv]
        minusNumber = rhv * digitsDivRes[indexRhv]
        sb1.append("-").append(minusNumber)
        while (sb1.length + sb2.length < length) sb2.append(" ")
        sb2.append(sb1)
        writer.write(sb2.toString())
        writer.newLine()
        space = sb2.length - number.toString().length
        if (minusNumber.toString().length == digitNumber(number)) space -= 1
        indexLhv++
        indexRhv++
        minusNumberSize = digitNumber(minusNumber) + 1
        numberSize = digitNumber(number)
    }
    writer.close()
}