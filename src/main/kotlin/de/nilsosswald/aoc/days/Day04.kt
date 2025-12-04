package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day04 : Day<Int, Int>(4, "Printing Department") {

  override fun partOne(input: List<String>): Int {
    return input
      .map(String::toList)
      .let { grid ->
        findPaperRolls(grid).count { point ->
          countAdjacentPaperRolls(grid, point) < 4
        }
      }
  }

  override fun partTwo(input: List<String>): Int {
    return input
      .map(String::toMutableList)
      .let { grid ->
        generateSequence {
          findPaperRolls(grid)
            .filter { countAdjacentPaperRolls(grid, it) < 4 }
            .onEach { grid[it.y][it.x] = '.' }
            .count()
            .takeIf { it > 0 }
        }
      }
      .sum()
  }

  private fun findPaperRolls(input: List<List<Char>>): List<Point> {
    return input.flatMapIndexed { y, line ->
      line.mapIndexedNotNull { x, c ->
        if (c == '.') null else Point(x, y)
      }
    }
  }

  private fun countAdjacentPaperRolls(grid: List<List<Char>>, point: Point): Int {
    return point
      .adjacentPoints()
      .count { grid.getOrNull(it.y)?.getOrNull(it.x) == '@' }
  }

  private fun Point.adjacentPoints(): List<Point> {
    return (-1..1)
      .flatMap { dy ->
        (-1..1).map { dx -> Point(x + dx, y + dy) }
      }
      .filterNot { it == this }
  }

  private data class Point(val x: Int, val y: Int)

  private val exampleInput = listOf(
    "..@@.@@@@.",
    "@@@.@.@.@@",
    "@@@@@.@.@@",
    "@.@@@@..@.",
    "@@.@@@@.@@",
    ".@@@@@@@.@",
    ".@.@.@.@@@",
    "@.@@@.@@@@",
    ".@@@@@@@@.",
    "@.@.@@@.@."
  )

  override val partOneTestExamples = mapOf(exampleInput to 13)
  override val partTwoTestExamples = mapOf(exampleInput to 43)
}
