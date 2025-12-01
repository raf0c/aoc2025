package day1

import kotlin.math.abs

class Day1(
    private val input: List<String>
) {

    companion object {

        @Suppress("unused")
        val example = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""".trimIndent().lines()

        @Suppress("JAVA_CLASS_ON_COMPANION")
        val day1Input = javaClass
            .getResourceAsStream("/day1_input.txt")
            ?.bufferedReader(Charsets.UTF_8)
            ?.lines()
            ?.toList()
            .orEmpty()
    }

    fun part1(): Int {
        val result = sequence {
            for (line in input) {
                val dir = if (line.first() == 'R') 1 else -1
                val turns = line.substring(1).toInt()
                yield(dir * turns)
            }
        }.runningFold(50) { acc, turn -> acc + turn }
            .count { turn -> turn.mod(100) == 0 }
        return result
    }

    fun part2(): Int {
        val result =  input.map { line ->
            val dir = if (line.first() == 'R') 1 else -1
            val turns = line.substring(1).toInt()
            dir * turns
        }.fold(50 to 0) { (turns, count): Pair<Int, Int>, signedChange: Int ->
            val currentPosition = turns + signedChange
            val flips = abs(turns.floorDiv(100) - currentPosition.floorDiv(100))
            val stoppedAt0up = if (currentPosition.mod(100) == 0 && currentPosition < turns) 1 else 0
            val stoppedAt0down = if (turns.mod(100) == 0 && currentPosition < turns) -1 else 0
            val change = flips + stoppedAt0up + stoppedAt0down
            currentPosition to (count + change)
        }.second

        return result
    }


    fun part1Dirty() {
        val result = input.map { line ->
            val direction = line.takeWhile { it.isLetter() }
            val turns = line.substringAfter(direction).toInt()
            direction to turns
        }.fold(CircularResult(50, 0)) { acc: CircularResult, pair: Pair<String, Int> ->
            val turn = when (pair.first) {
                "R" -> acc.turns.circular100Add(pair.second)
                "L" -> acc.turns.circular100Substract(pair.second)
                else -> 0
            }
            val newZeroCount = if (turn == 0) {
                acc.zeroCount + 1
            } else {
                acc.zeroCount
            }

            return@fold CircularResult(turn, newZeroCount)
        }
        println(result.zeroCount)
    }


    fun part2Dirty() {
        val finalStatePair: Pair<Int, Int> = input.map { line ->
            val direction = line.takeWhile { it.isLetter() }
            val turns = line.substringAfter(direction).toInt()
            direction to turns
        }.fold(50 to 0) { (currentRawPos, currentZeroCount), pair: Pair<String, Int> ->
            val signedChange = pair.second * if (pair.first == "R") 1 else -1
            val newRawPos = currentRawPos + signedChange

            val flips = abs(currentRawPos.floorDiv(100) - newRawPos.floorDiv(100))

            val stoppedAt0up = if (newRawPos % 100 == 0 && newRawPos < currentRawPos) 1 else 0
            val stoppedAt0down = if (currentRawPos % 100 == 0 && newRawPos < currentRawPos) -1 else 0

            val crossingsInStep = flips + stoppedAt0up + stoppedAt0down

            val newZeroCount = currentZeroCount + crossingsInStep

            return@fold newRawPos to newZeroCount
        }

        val finalAnswer = finalStatePair.second

        println("zero count: ${finalStatePair.second}")
        println("sum of the count and passes: $finalAnswer")
    }


    fun Int.circular100Add(other: Int): Int {
        return (this + other) % 100
    }

    fun Int.circular100Substract(other: Int): Int {
        val result = (this - other) % 100
        // ensure a positive result between 0 and 99.
        return (result + 100) % 100
    }

}

data class CircularResult(val turns: Int, val zeroCount: Int)
