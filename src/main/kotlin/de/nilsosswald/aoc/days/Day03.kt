package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day03 : Day<Long, Long>(3, "Lobby") {

  override fun partOne(input: List<String>): Long {
    return input.sumOf { largestJoltage(it, digits = 2) }
  }

  override fun partTwo(input: List<String>): Long {
    return input.sumOf { largestJoltage(it, digits = 12) }
  }

  private fun largestJoltage(bank: String, digits: Int): Long {
    return (0..<digits)
      .fold(-1 to "") { (lastIndex, joltage), n ->
        bank
          .withIndex()
          .filter { (i, _) -> i > lastIndex && i < bank.length - digits + 1 + n }
          .maxBy { it.value.digitToInt() }
          .let { (index, value) -> index to joltage + value }
      }
      .second
      .toLong()
  }

  private val exampleInput = listOf(
    "987654321111111",
    "811111111111119",
    "234234234234278",
    "818181911112111"
  )

  override val partOneTestExamples = mapOf(exampleInput to 357L)
  override val partTwoTestExamples = mapOf(exampleInput to 3121910778619)
}
