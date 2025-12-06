package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day06 : Day<Long, Long>(6, "Trash Compactor") {

  override fun partOne(input: List<String>): Long {
    return input
      .map { line -> line.split(" ").filter(String::isNotBlank) }
      .let { rows ->
        (0 until rows.first().size).map { col ->
          Problem(
            rows.dropLast(1).map { it[col].toLong() },
            rows.last()[col].first().toOperatorFn()
          )
        }
      }
      .sumOf(Problem::solve)
  }

  override fun partTwo(input: List<String>): Long {
    return (0 until input.maxOf { it.length })
      .map { col -> input.mapNotNull { it.getOrNull(col) }.filterNot { it.isWhitespace() } }
      .split { it.isEmpty() }
      .map { chunk ->
        val operator = chunk
          .flatten()
          .first { !it.isDigit() }
        val values = chunk.map { chars ->
          chars
            .filter(Char::isDigit)
            .joinToString("")
            .toLong()
        }

        Problem(values, operator.toOperatorFn())
      }
      .sumOf(Problem::solve)
  }

  private fun Char.toOperatorFn(): (Long, Long) -> Long = when (this) {
    '*' -> Long::times
    '+' -> Long::plus
    else -> error("Unknown operator")
  }

  private fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return this.fold(mutableListOf(mutableListOf<T>())) { acc, e ->
      if (predicate(e)) acc.add(mutableListOf()) else acc.last().add(e)
      acc
    }
  }

  private data class Problem(
    val values: List<Long>,
    val operatorFn: (Long, Long) -> Long
  ) {
    fun solve() = values.reduce(operatorFn)
  }

  private val exampleInput = listOf(
    "123 328  51 64 ",
    " 45 64  387 23 ",
    "  6 98  215 314",
    "  *   +   *   +"
  )

  override val partOneTestExamples = mapOf(exampleInput to 4277556L)
  override val partTwoTestExamples = mapOf(exampleInput to 3263827L)
}
