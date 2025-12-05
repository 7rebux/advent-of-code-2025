import de.nilsosswald.aoc.Day
import de.nilsosswald.aoc.days.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DaysTest {
  data class Answer<O, T>(
    val day: Day<O, T>,
    val partOne: O,
    val partTwo: T
  )

  @TestFactory
  fun answers() = listOf(
    Answer(Day01, 1129, 6638),
    Answer(Day02, 30599400849, 46270373595),
    Answer(Day03, 17100, 170418192256861),
    Answer(Day04, 1564, 9401),
    Answer(Day05, 505, 344423158480189),
  ).map {
    DynamicTest.dynamicTest("Day ${it.day.number} - ${it.day.title}") {
      if (it.day.partOneTestExamples.isNotEmpty()) {
        print("Testing Part 1 examples..")
        it.day.partOneTestExamples.entries.forEach { entry ->
          Assertions.assertEquals(entry.value, it.day.partOne(entry.key))
        }
        print(" SUCCESS\n")
      }

      print("Testing Part 1..")
      Assertions.assertEquals(it.partOne, it.day.partOne())
      print(" SUCCESS\n")

      if (it.day.partTwoTestExamples.isNotEmpty()) {
        print("Testing Part 2 examples..")
        it.day.partTwoTestExamples.entries.forEach { entry ->
          Assertions.assertEquals(entry.value, it.day.partTwo(entry.key))
        }
        print(" SUCCESS\n")
      }

      print("Testing Part 2..")
      Assertions.assertEquals(it.partTwo, it.day.partTwo())
      print(" SUCCESS\n")
    }
  }
}
