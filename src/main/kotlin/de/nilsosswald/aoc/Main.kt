package de.nilsosswald.aoc

import de.nilsosswald.aoc.days.*

object Main {
  private val days = listOf<Day<*, *>>(
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
  )

  @JvmStatic
  fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
      val number = args[0].toInt()
      val day = days.find { it.number == number }
        ?: error("Day $number not found!")

      println(day)
    } else {
      days.forEach(::printDay)
    }
  }

  private fun printDay(day: Day<*, *>) {
    val header = "=== Day ${day.number.toString().padStart(2, '0')}: ${day.title} ==="
    val footer = "=".repeat(header.length)

    println(header)
    println("|> Part 1: ${day.partOne()}")
    println("|> Part 2: ${day.partTwo()}")
    println(footer)
  }
}
