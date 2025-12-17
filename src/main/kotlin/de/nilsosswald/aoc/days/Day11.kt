package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day

object Day11 : Day<Int, Long>(11, "Reactor") {

  override fun partOne(input: List<String>): Int {
    val nodes = parseInput(input)
    val queue = mutableListOf("you")
    var count = 0

    while (queue.isNotEmpty()) {
      val node = queue.removeFirst()
      if (node == "out") count++ else nodes[node]?.forEach(queue::add)
    }

    return count
  }

  override fun partTwo(input: List<String>): Long {
    return countValidPaths("svr", parseInput(input))
  }

  fun countValidPaths(
    node: String,
    nodes: Map<String, List<String>>,
    foundFft: Boolean = false,
    foundDac: Boolean = false,
    cache: MutableMap<PathState, Long> = mutableMapOf()
  ): Long {
    return cache.getOrPut(PathState(node, foundFft, foundDac)) {
      val hasFft = foundFft || node == "fft"
      val hasDac = foundDac || node == "dac"

      when (node) {
        "out" -> if (hasFft && hasDac) 1 else 0
        else -> nodes.getValue(node).sumOf { next ->
          countValidPaths(next, nodes, hasFft, hasDac, cache)
        }
      }
    }
  }

  private fun parseInput(input: List<String>): Map<String, List<String>> {
    return input.associate { line ->
      val (key, values) = line.split(": ")
      key to values.split(" ")
    }
  }

  data class PathState(
    val node: String,
    val foundFft: Boolean,
    val foundDac: Boolean
  )

  override val partOneTestExamples = mapOf(
    listOf(
      "aaa: you hhh",
      "you: bbb ccc",
      "bbb: ddd eee",
      "ccc: ddd eee fff",
      "ddd: ggg",
      "eee: out",
      "fff: out",
      "ggg: out",
      "hhh: ccc fff iii",
      "iii: out"
    ) to 5
  )

  override val partTwoTestExamples = mapOf(
    listOf(
      "svr: aaa bbb",
      "aaa: fft",
      "fft: ccc",
      "bbb: tty",
      "tty: ccc",
      "ccc: ddd eee",
      "ddd: hub",
      "hub: fff",
      "eee: dac",
      "dac: fff",
      "fff: ggg hhh",
      "ggg: out",
      "hhh: out"
    ) to 2L
  )
}
