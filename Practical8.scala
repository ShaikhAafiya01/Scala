import com.github.tototoshi.csv._

object Practical8:

  def main(args: Array[String]): Unit =

    val reader = CSVReader.open("src/main/scala/students.csv")

    val rows: List[Map[String, String]] =
      reader.allWithHeaders()

    reader.close()

    val ages =
      rows.map(row => row("Age").toDouble)

    val marks =
      rows.map(row => row("Marks").toDouble)

    val attendance =
      rows.map(row => row("Attendance").toDouble)

    def statistics(name: String, data: List[Double]): Unit =

      val mean = data.sum / data.size
      val minimum = data.min
      val maximum = data.max

      println(name)
      println("Mean: " + mean)
      println("Minimum: " + minimum)
      println("Maximum: " + maximum)
      println()

    println("BASIC STATISTICS")
    println("----------------")

    statistics("Age", ages)
    statistics("Marks", marks)
    statistics("Attendance", attendance)
