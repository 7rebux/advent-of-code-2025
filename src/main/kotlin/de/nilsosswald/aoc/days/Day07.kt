package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.text.getOrNull

object Day07 : Day<Int, Long>(7, "Laboratories") {

  override fun partOne(input: List<String>): Int {
    val cursors = mutableListOf(findStart(input))
    var splits = 0

    while (cursors.any { it.y != input.lastIndex }) {
      val cursor = cursors.removeFirst()
      val below = Point(cursor.x, cursor.y + 1)

      when (input[below.y].getOrNull(below.x)) {
        '.' -> cursors.add(below)
        '^' -> {
          val branches = listOf(
            below.copy(x = below.x - 1),
            below.copy(x = below.x + 1)
          ).filterNot(cursors::contains).ifEmpty { continue }

          cursors.addAll(branches)
          splits++
        }
      }
    }

    return splits
  }

  override fun partTwo(input: List<String>): Long {
    return countPaths(input, findStart(input))
  }

  private fun findStart(grid: List<String>) =
    Point(grid.first().indexOf('S'), 0)

  private fun countPaths(
    grid: List<String>,
    beam: Point,
    cache: MutableMap<Point, Long> = mutableMapOf()
  ): Long {
    return cache.getOrPut(beam) {
      when {
        beam.y == grid.lastIndex -> 1
        grid[beam.y][beam.x] == '^' ->
          countPaths(grid, Point(beam.x - 1, beam.y + 1), cache) +
            countPaths(grid, Point(beam.x + 1, beam.y + 1), cache)
        else -> countPaths(grid, Point(beam.x, beam.y + 1), cache)
      }
    }
  }

  private data class Point(val x: Int, val y: Int)

  private val exampleInput = listOf(
    ".......S.......",
    "...............",
    ".......^.......",
    "...............",
    "......^.^......",
    "...............",
    ".....^.^.^.....",
    "...............",
    "....^.^...^....",
    "...............",
    "...^.^...^.^...",
    "...............",
    "..^...^.....^..",
    "...............",
    ".^.^.^.^.^...^.",
    "..............."
  )

  override val partOneTestExamples = mapOf(exampleInput to 21)
  override val partTwoTestExamples = mapOf(exampleInput to 40L)
}
