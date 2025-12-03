package day3


class Day3(
    private val input: List<String>
) {

    companion object {

        @Suppress("unused")
        val example = """987654321111111
811111111111119
234234234234278
818181911112111"""
            .trimIndent()
            .lines()

        @Suppress("JAVA_CLASS_ON_COMPANION")
        val day3Input = javaClass
            .getResourceAsStream("/day3_input.txt")
            ?.bufferedReader(Charsets.UTF_8)
            ?.lines()
            ?.toList()
            .orEmpty()
    }

    fun part1(): Long {
        return input.sumOf { it.maxValueWith(2) }
    }

    fun part2(): Long {
        return input.sumOf { it.maxValueWith(12) }
    }

    fun String.maxValueWith(digitsAmount: Int): Long {
        var start = 0
        val result = StringBuilder()
        for (i in 0 until digitsAmount) {
            val end = length - (digitsAmount - i)
            //println("n: $n, k: $digitsAmount, i: $i, start: $start, end: $end")
            val maxDigit = (start..end).maxBy { this[it] }
            result.append(this[maxDigit])
            start = maxDigit + 1
        }
        return result.toString().toLong()
    }

    fun twoDigitsMax() {
        val x = input.map { line ->
            var max = 0
            for (i in line.indices) {
                for (j in i + 1 until line.length) {
                    println("using i: $i and j: $j considering ${line[i]}${line[j]}")
                    val num = "${line[i]}${line[j]}".toInt()
                    if (num > max) {
                        max = num
                    }
                }
            }
            max
        }.onEach { println(it) }
            .sum()
    }

}
