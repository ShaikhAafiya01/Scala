import com.github.tototoshi.csv._

object Practical10:

  def main(args: Array[String]): Unit =

    val reader =
      CSVReader.open("src/main/scala/students.csv")

    val rows: List[Map[String, String]] =
      reader.allWithHeaders()

    reader.close()

    val threshold = 80.0

    val filteredRows =
      rows.filter { row =>
        row("Marks").toDouble > threshold
      }

    println("Students with Marks Greater Than 80")
    println("-----------------------------------")

    filteredRows.foreach { row =>

      println(
        row("Name") +
          " - Marks: " +
          row("Marks")
      )
    }
