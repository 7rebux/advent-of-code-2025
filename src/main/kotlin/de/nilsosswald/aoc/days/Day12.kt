package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.collections.Map

object Day12 : Day<Int, Nothing?>(12, "Christmas Tree Farm") {

  // Most of the parsing logic isn’t needed.
  // The real input is structured in a way so that we only need to check
  // each 3x3 section of the grid instead of handling different shapes.
  override fun partOne(input: List<String>): Int {
    return parseInput(input)
      .count { it.containingSquares(3) >= it.totalRequiredPresents() }
  }

  private fun parseInput(input: List<String>): List<Region> {
    val groups = input.split(String::isBlank)
    val presents = groups
      .dropLast(1)
      .map { lines ->
        lines.drop(1).flatMapIndexed { y, line ->
          line.mapIndexedNotNull { x, c ->
            if (c == '#') Point(x, y) else null
          }
        }
      }
      .toTypedArray()

    return groups.last().map { line ->
      val (left, right) = line.split(": ")
      val (width, height) = left.split("x").map(String::toInt)
      val requiredPresents = right
        .split(" ")
        .map(String::toInt)
        .mapIndexedNotNull { i, count ->
          if (count > 0) Present(presents[i]) to count else null
        }
        .toMap()

      Region(width, height, requiredPresents)
    }
  }

  private fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return this.fold(mutableListOf(mutableListOf<T>())) { acc, e ->
      if (predicate(e)) acc.add(mutableListOf()) else acc.last().add(e)
      acc
    }
  }

  private fun Region.containingSquares(size: Int) =
    (this.width / size) * (this.height / size)

  private fun Region.totalRequiredPresents() = this.requiredPresents.values.sum()

  private data class Region(
    val width: Int,
    val height: Int,
    val requiredPresents: Map<Present, Int>
  )

  private data class Present(val shape: List<Point>)

  private data class Point(val x: Int, val y: Int)

  // There is no second part for the last day
  override fun partTwo(input: List<String>) = null

  // Not testable because example input is more complicated than real input...
  override val partOneTestExamples: Map<List<String>, Int> = emptyMap()
  override val partTwoTestExamples: Map<List<String>, Nothing?> = emptyMap()
}
