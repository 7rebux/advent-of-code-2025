package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day02 : Day<Long, Long>(2, "Gift Shop") {

  override fun partOne(input: List<String>): Long {
    return parseInput(input)
      .filter(::isInvalid)
      .sum()
  }

  override fun partTwo(input: List<String>): Long {
    return parseInput(input)
      .filter { id ->
        val length = id.toString().length
        (2..length).any { isInvalid(id, it) }
      }
      .sum()
  }

  private fun parseInput(input: List<String>): List<Long> {
    return input.first()
      .split(",")
      .map {
        it.split("-")
          .map(String::toLong)
          .let { (min, max) -> min..max }
      }
      .flatten()
  }

  private fun isInvalid(id: Long, repeat: Int = 2): Boolean {
    val s = id.toString()
    return s.length % repeat == 0 && s.chunked(s.length / repeat).distinct().size == 1
  }

  private val exampleInput = listOf("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")
  override val partOneTestExamples = mapOf(exampleInput to 1227775554L)
  override val partTwoTestExamples = mapOf(exampleInput to 4174379265L)
}
