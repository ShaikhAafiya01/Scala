object M2_3 {
  def main(args: Array[String]): Unit = {

    val data = List(10, 20, 10, 30, 20, 10, 40, 30, 20, 50)

    val frequency = data.groupBy(identity).view.mapValues(_.size).toMap

    var cumulative = 0

    println("Value\tFrequency\tCumulative Frequency")

    frequency.toSeq.sortBy(_._1).foreach { case (value, freq) =>
      cumulative += freq
      println(s"$value\t$freq\t\t$cumulative")
    }
  }
}