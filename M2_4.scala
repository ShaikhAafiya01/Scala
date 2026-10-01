object M2_4 {

  case class Student(id: Int, name: String, marks: Double)

  def main(args: Array[String]): Unit = {

    val students = List(
      Student(1, "Aafiya", 85),
      Student(2, "Sara", 92),
      Student(3, "Zoya", 78),
      Student(4, "Mira", 95),
      Student(5, "Ali", 88),
      Student(6, "Noor", 90),
      Student(7, "Riya", 75)
    )

    val top5 = students.sortBy(-_.marks).take(5)

    println("Top 5 Students:")
    top5.foreach(println)
  }
}
