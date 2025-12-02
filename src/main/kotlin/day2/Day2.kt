package day2

import kotlin.text.all

class Day2(
    private val input: String
) {

    companion object {

        @Suppress("unused")
        const val EXAMPLE =
            """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""

        @Suppress("JAVA_CLASS_ON_COMPANION")
        val input = javaClass
            .getResourceAsStream("/day2_input.txt")
            ?.bufferedReader(Charsets.UTF_8)
            ?.readLine()
            .orEmpty()
    }

    fun part1(): Long {
        val invalidIds = sequence {
            for (str in input.split(',')) {
                val idRange = str.split('-')
                val from = idRange[0].toLong()
                val to = idRange[1].toLong()
                println("checking range : $from to $to")
                for (id in from..to) {
                    val idStr = id.toString()
                    if (isInvalidId(idStr)) {
                        println("--------------------invalid id : $id")
                        yield(id)
                    }
                }
            }
        }
        return invalidIds.sum()
    }

    fun part2(): Long {
        val invalidIds = sequence {
            for (str in input.split(',')) {
                val idRange = str.split('-')
                val from = idRange[0].toLong()
                val to = idRange[1].toLong()
                for (id in from..to) {
                    val idStr = id.toString()
                    if (isAnySeqInvalidId(idStr)) {
                        yield(id)
                    }
                }
            }
        }
        return invalidIds.sum()
    }

    fun hasRepeatingDigits(id: String): Boolean {
        return id.chunked(2)
                    .groupingBy { it }
                    .eachCount()
                    .any { it.value > 1 } &&
                id.chunked(2).any { it[0] == it[1] }
    }

    fun isInvalidId(id: String): Boolean {
        if (id.length % 2 == 0) {
            val half = id.length / 2
            if (id.take(half) == id.substring(half)) return true
        }
        if (id.length == 2 && id[0] == id[1]) return true

        return false
    }

    fun String.isInvalidIdFunc(): Boolean {
        val repeatedDigits = length % 2 == 0 && chunked(length / 2).distinct().size == 1
        val exactlyTwoSameDigits = length == 2 && all { it == this[0] }

        return repeatedDigits || exactlyTwoSameDigits
    }

    fun isAnySeqInvalidId(id: String): Boolean {
        return (1..id.length / 2).any { chunkSize ->
            id.length % chunkSize == 0 && id.chunked(chunkSize).distinct().size == 1
        }
    }

}