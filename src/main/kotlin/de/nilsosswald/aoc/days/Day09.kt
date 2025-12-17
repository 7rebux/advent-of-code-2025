package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.math.abs

object Day09 : Day<Long, Int>(9, "Movie Theater") {

  override fun partOne(input: List<String>): Long {
    val points = parseInput(input)

    return points
      .flatMapIndexed { i, point -> points.drop(i + 1).map { point to it } }
      .maxOf { (a, b) -> a.area(b) }
  }

  override fun partTwo(input: List<String>): Int {
    return 0
  }

  private fun parseInput(input: List<String>): List<Point> {
    return input.map { line ->
      val (x, y) = line.split(",").map(String::toInt)
      Point(x, y)
    }
  }

  private fun Point.area(other: Point) = abs(1L + x - other.x) * abs(1L + y - other.y)

  private data class Point(val x: Int, val y: Int)

  private val exampleInput = listOf(
    "7,1",
    "11,1",
    "11,7",
    "9,7",
    "9,5",
    "2,5",
    "2,3",
    "7,3"
  )

  override val partOneTestExamples = mapOf(exampleInput to 50L)
  override val partTwoTestExamples = mapOf(exampleInput to 24)
}
