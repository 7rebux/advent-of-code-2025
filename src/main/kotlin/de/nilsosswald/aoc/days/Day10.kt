package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.text.map

object Day10: Day<Int, Int>(10, "Factory") {

  override fun partOne(input: List<String>): Int {
    return parseInput(input).sumOf(::solvePartOne)
  }

  override fun partTwo(input: List<String>): Int {
    return 0
  }

  private fun solvePartOne(machine: Machine): Int {
    val queue = machine.buttonSchematics
      .mapIndexed { i, button -> 1.shl(i) to button }
      .toMutableList()
    // Keeps track of all seen pressed button combinations
    val visited = BooleanArray(1 shl machine.buttonSchematics.size)

    while (queue.isNotEmpty()) {
      val (mask, current) = queue.removeFirst()

      if (current == machine.lightDiagram) {
        return Integer.bitCount(mask)
      }

      machine.buttonSchematics.forEachIndexed { i, button ->
        val bit = 1 shl i

        if (mask and bit == 0) {
          val nextMask = mask or bit

          if (!visited[nextMask]) {
            visited[nextMask] = true
            queue.add(nextMask to current.xor(button))
          }
        }
      }
    }

    error("No solution found")
  }

  // We are parsing the light diagram and button schematics as binary numbers.
  // This way we can XOR button schematics on a light diagram state.
  private fun parseInput(input: List<String>): List<Machine> {
    return input.map { line ->
      val groups = line.split(" ").map { it.substring(1, it.lastIndex) }
      val initialDiagram = ".".repeat(groups.first().length)
      val goalDiagram = groups.first().map { if (it == '.') '0' else '1' }.joinToString("").toInt(2)
      val joltageRequirements = groups.last().split(",").map(String::toInt)
      val buttons = groups.subList(1, groups.lastIndex)
        .map { it.split(",").map(String::toInt).toList() }
        .map { button ->
          initialDiagram
            .mapIndexed { i, _ -> if (i in button) "1" else "0" }
            .joinToString("")
            .toInt(2)
        }

      Machine(goalDiagram, buttons, joltageRequirements)
    }
  }

  private data class Machine(
    val lightDiagram: Int,
    val buttonSchematics: List<Int>,
    val joltageRequirements: List<Int>
  )

  private val exampleInput = listOf(
    "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
    "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
    "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
  )

  override val partOneTestExamples = mapOf(exampleInput to 7)
  override val partTwoTestExamples = mapOf(exampleInput to 33)
}
