import com.github.tototoshi.csv._

object Practical9:

  def main(args: Array[String]): Unit =

    val reader =
      CSVReader.open("src/main/scala/missing_students.csv")

    val rows: List[Map[String, String]] =
      reader.allWithHeaders()

    reader.close()

    // Get existing Marks values
    val marks =
      rows.flatMap { row =>
        val value = row("Marks")

        if value.trim.isEmpty then
          None
        else
          Some(value.toDouble)
      }

    // Get existing Attendance values
    val attendance =
      rows.flatMap { row =>
        val value = row("Attendance")

        if value.trim.isEmpty then
          None
        else
          Some(value.toDouble)
      }

    // Calculate column means
    val marksMean =
      marks.sum / marks.size

    val attendanceMean =
      attendance.sum / attendance.size

    println("Column Means")
    println("------------")
    println("Marks Mean: " + marksMean)
    println("Attendance Mean: " + attendanceMean)

    println()
    println("Data After Replacing Missing Values")
    println("-----------------------------------")

    rows.foreach { row =>

      val marksValue =
        if row("Marks").trim.isEmpty then
          marksMean
        else
          row("Marks").toDouble

      val attendanceValue =
        if row("Attendance").trim.isEmpty then
          attendanceMean
        else
          row("Attendance").toDouble

      println(
        row("Name") +
          " - Marks: " +
          marksValue +
          " - Attendance: " +
          attendanceValue
      )
    }