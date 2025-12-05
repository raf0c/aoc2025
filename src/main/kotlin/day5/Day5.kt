package day5

import kotlin.collections.map

class Day5(
    private val input: List<String>
) {

    companion object {

        @Suppress("unused")
        val example = """3-5
10-14
16-20
12-18

1
5
8
11
17
32""".trimIndent().lines()

        @Suppress("JAVA_CLASS_ON_COMPANION")
        val day5Input = javaClass
            .getResourceAsStream("/day5_input.txt")
            ?.bufferedReader(Charsets.UTF_8)
            ?.lines()
            ?.toList()
            .orEmpty()
    }

    fun part1(): Int {
        val freshIds = input.subList(0, input.indexOf("")).map {
            val range = it.split('-')
            val from = range[0].toLong()
            val to = range[1].toLong()
            from..to
        }
        val availableIds = input.subList(input.indexOf("") + 1, input.size).map { str -> str.toLong() }
        val x = availableIds.mapNotNull { availableId: Long ->
            val isInFreshIds = freshIds.any { freshIdRange -> availableId in freshIdRange }
            if (isInFreshIds) {
                availableId
            } else {
                null
            }
        }
        return x.count()
    }

    fun part2(): Long {
        val freshIdsRanges = input.subList(0, input.indexOf("")).map {
            val range = it.split('-')
            val from = range[0].toLong()
            val to = range[1].toLong()
            from..to
        }.sortedBy { idRange -> idRange.first }

        val merged = mutableListOf<LongRange>()
        for (range in freshIdsRanges) {
            if (merged.isEmpty() || merged.last().last < range.first - 1) {
                merged.add(range)
            } else {
                // the current range overlaps or is adjacent to the last merged range,
                // so update the last merged range to cover both by extending its end to the maximum of the two ends.
                merged[merged.lastIndex] = merged.last().first..maxOf(merged.last().last, range.last)
            }
        }

        val totalUniqueCount = merged
            .sumOf {
                println("counting range: $it, first: ${it.first} last: ${it.last} diff: ${it.last - it.first + 1}")
                it.last - it.first + 1
            }
        println("Total unique count: $totalUniqueCount")
        return totalUniqueCount
    }

    fun part2b(): Long {
        val countAllInclusiveIds = input
            .takeWhile { line -> line.isNotEmpty() }
            .map { stringRange ->
                stringRange.split('-').let { (from, to) ->
                    from.toLong()..to.toLong()
                }
            }
            .sortedBy { idRange -> idRange.first }
            .fold(mutableListOf<LongRange>()) { acc, range ->
                if (acc.isEmpty() || acc.last().last < range.first - 1) {
                    acc.add(range)
                } else {
                    // the current range overlaps or is adjacent to the last merged range,
                    // so update the last merged range to cover both by extending its end to the maximum of the two ends.
                    acc[acc.lastIndex] = acc.last().first..maxOf(acc.last().last, range.last)
                }
                acc
            }.sumOf { it.last - it.first + 1 }
        return countAllInclusiveIds
    }
}

