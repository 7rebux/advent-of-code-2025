package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day05 : Day<Int, Long>(5, "Cafeteria") {

  override fun partOne(input: List<String>): Int {
    val (freshIdRanges, availableIds) = parseInput(input)

    return availableIds.count { id ->
      freshIdRanges.any { range -> id in range }
    }
  }

  override fun partTwo(input: List<String>): Long {
    return parseInput(input)
      .freshIdRanges
      .sortedBy(LongRange::first)
      .fold(emptyList<LongRange>()) { acc, range ->
        val prev = acc.lastOrNull() ?: return@fold listOf(range)

        when {
          // Not touching
          range.first > prev.last + 1 -> acc.plusElement(range)
          // Overlapping
          range.last > prev.last -> acc.dropLast(1).plusElement(prev.first..range.last)
          // Included in previous
          else -> acc
        }
      }
      .sumOf { it.size }
  }

  private fun parseInput(input: List<String>): Ingredients {
    val availableIds = input
      .takeWhile(String::isNotBlank)
      .map { line ->
        line
          .split("-")
          .map(String::toLong)
          .let { (a, b) -> LongRange(a, b) }
      }
    val freshIdRanges = input
      .takeLastWhile(String::isNotBlank)
      .map(String::toLong)

    return Ingredients(availableIds, freshIdRanges)
  }

  private val LongRange.size get() = last - first + 1

  private data class Ingredients(val freshIdRanges: List<LongRange>, val availableIds: List<Long>)

  private val exampleInput = listOf(
    "3-5",
    "10-14",
    "16-20",
    "12-18",
    "",
    "1",
    "5",
    "8",
    "11",
    "17",
    "32"
  )

  override val partOneTestExamples = mapOf(exampleInput to 3)
  override val partTwoTestExamples = mapOf(exampleInput to 14L)
}
