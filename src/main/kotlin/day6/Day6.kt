package day6

import kotlin.collections.flatten

class Day6(
    private val input: String
) {

    companion object {

        @Suppress("unused")
        val example = """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """.trimIndent()

        @Suppress("JAVA_CLASS_ON_COMPANION")
        val day6Input: String = javaClass
            .getResourceAsStream("/day6_input.txt")
            ?.bufferedReader(Charsets.UTF_8)?.use { reader ->
                reader.readText()
            } ?: throw IllegalStateException("Resource /day6_input.txt not found")
    }

    fun part1(): Long {
        val columnPairs = createColumnPairs(input)
        val result = columnPairs.fold(0L) { acc, pair ->
            acc + when (pair.first) {
                "*" -> pair.second.fold(1L, Long::times)
                "+" -> pair.second.sum()
                else -> 0L
            }
        }
        return result
    }

    fun part2(): Long {


        return 0L
    }

    fun createColumnPairs(input: String): List<Pair<String, List<Long>>> {
        val lines = input.trim().lines().filter { it.isNotBlank() }

        val operatorLine = lines.last()
        val numberLines = lines.dropLast(1)

        val rowsOfNumbers = numberLines.map { line ->
            line.trim().split("""\s+""".toRegex()).filter { it.isNotEmpty() }
        }

        // transpose rows and columns
        val numberOfColumns = rowsOfNumbers.firstOrNull()?.size ?: return emptyList()
        val columnsOfNumbers = (0 until numberOfColumns).map { colIndex ->
            rowsOfNumbers.map { row ->
                row.getOrElse(colIndex) { throw IllegalStateException("Inconsistent column count in input.") }
            }
        }

        val operators = operatorLine.trim().split("""\s+""".toRegex()).filter { it.isNotEmpty() }

        return operators.zip(columnsOfNumbers) { op, colStrings ->
            val longList = colStrings.mapNotNull { it.toLongOrNull() }
            op to longList
        }
    }


}