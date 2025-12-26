package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day09 : Day<Long, Long>(9, "Movie Theater") {

  override fun partOne(input: List<String>): Long {
    return parseInput(input)
      .allRectangles()
      .maxOf(Bounds::area)
  }

  override fun partTwo(input: List<String>): Long {
    val corners = parseInput(input)
    val rectangles = corners
      .allRectangles()
      .sortedByDescending(Bounds::area)
    val lines = corners
      .zipWithNext()
      .plus(Pair(corners.last(), corners.first()))
      .map { (a, b) -> Bounds(a, b) }

    return rectangles
      .first { rectangle ->
        lines.none { lines ->
          val (left, top) = rectangle.min
          val (right, bottom) = rectangle.max
          val (leftX, leftY) = lines.min
          val (rightX, rightY) = lines.max

          leftX < right && leftY < bottom && rightX > left && rightY > top
        }
      }
      .let(Bounds::area)
  }

  private fun parseInput(input: List<String>): List<Point> {
    return input.map { line ->
      val (x, y) = line.split(",").map(String::toInt)
      Point(x, y)
    }
  }

  private fun List<Point>.allRectangles(): List<Bounds> {
    return this.flatMapIndexed { i, a ->
      this.drop(i + 1).map { b -> Bounds(a, b) }
    }
  }

  private data class Bounds(private val a: Point, private val b: Point) {
    val min = Point(minOf(a.x, b.x), minOf(a.y, b.y)) // Top left corner
    val max = Point(maxOf(a.x, b.x), maxOf(a.y, b.y)) // Bottom right corner
    val area = (1L + max.x - min.x) * (1L + max.y - min.y)
  }

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
  override val partTwoTestExamples = mapOf(exampleInput to 24L)
}
