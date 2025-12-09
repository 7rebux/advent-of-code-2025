package de.nilsosswald.aoc.days

import de.nilsosswald.aoc.Day
import kotlin.math.hypot

object Day08 : Day<Int, Int>(8, "Playground") {

  // 10 for the example input
  private const val PAIRS_COUNT = 1000

  override fun partOne(input: List<String>): Int {
    val boxes = parseInput(input)
    val initialCircuits = boxes.map { mutableListOf(it) }.toMutableList()

    return findShortestDistancePairs(boxes)
      .take(PAIRS_COUNT)
      .fold(initialCircuits) { acc, (a, b) ->
        val circuitA = acc.first { a in it }
        val circuitB = acc.first { b in it }

        if (circuitA === circuitB) {
          return@fold acc
        }

        circuitA.addAll(circuitB)
        acc.remove(circuitB)

        acc
      }
      .map { it.size }
      .sortedDescending()
      .take(3)
      .reduce(Int::times)
  }

  override fun partTwo(input: List<String>): Int {
    val boxes = parseInput(input)
    val circuits = boxes.map { mutableListOf(it) }.toMutableList()

    findShortestDistancePairs(boxes).forEach { (a, b) ->
      val circuitA = circuits.first { a in it }
      val circuitB = circuits.first { b in it }

      if (circuitA === circuitB) {
        return@forEach
      }

      circuitA.addAll(circuitB)
      circuits.remove(circuitB)

      if (circuits.size == 1) {
        return (a.x * b.x).toInt()
      }
    }

    error("No solution found")
  }

  private fun parseInput(input: List<String>): List<Box> {
    return input.map { line ->
      val (x, y, z) = line.split(",").map(String::toDouble)
      Box(x, y, z)
    }
  }

  private fun findShortestDistancePairs(boxes: List<Box>): List<Pair<Box, Box>> {
    return boxes
      .flatMapIndexed { i, a ->
        boxes.drop(i + 1).map { b -> a to b }
      }
      .sortedBy { (a, b) -> a.distanceTo(b) }
  }

  private fun Box.distanceTo(other: Box): Double =
    hypot(x - other.x, hypot(y - other.y, z - other.z))

  private data class Box(val x: Double, val y: Double, val z: Double)

  private val exampleInput = listOf(
    "162,817,812",
    "57,618,57",
    "906,360,560",
    "592,479,940",
    "352,342,300",
    "466,668,158",
    "542,29,236",
    "431,825,988",
    "739,650,466",
    "52,470,668",
    "216,146,977",
    "819,987,18",
    "117,168,530",
    "805,96,715",
    "346,949,466",
    "970,615,88",
    "941,993,340",
    "862,61,35",
    "984,92,344",
    "425,690,689"
  )

  override val partOneTestExamples: Map<List<String>, Int> = mapOf(
    // exampleInput to 40
  )

  override val partTwoTestExamples = mapOf(exampleInput to 25272)
}
