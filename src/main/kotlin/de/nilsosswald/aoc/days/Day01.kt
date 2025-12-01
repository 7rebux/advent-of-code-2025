package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.math.abs

object Day01 : Day<Int, Int>(1, "Secret Entrance") {

  override fun partOne(input: List<String>): Int {
    return parseInput(input)
      .runningFold(50) { acc, n -> (acc + n).mod(100) }
      .count { it == 0 }
  }

  override fun partTwo(input: List<String>): Int {
    return parseInput(input)
      .fold(50 to 0) { (dial, count), n ->
        val next = dial + n % 100 // % = rem
        val normalized = next.mod(100)
        val loops = abs(n) / 100
        val zeros = when {
          // Started on 0
          dial == 0 -> loops
          // Ended on 0
          normalized == 0 -> loops + 1
          // Additional wrap
          next != normalized -> loops + 1
          // None of the above
          else -> loops
        }

        normalized to count + zeros
      }
      .second
  }

  private fun parseInput(input: List<String>) = input.map {
    val dist = it.drop(1).toInt()
    if (it[0] == 'L') -dist else dist
  }

  private val exampleInput = listOf("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82")
  override val partOneTestExamples = mapOf(exampleInput to 3)
  override val partTwoTestExamples = mapOf(exampleInput to 6)
}
