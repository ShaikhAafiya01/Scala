object Practical2 {

  def mean(numbers: List[Double]): Double = {
    numbers.sum / numbers.length
  }

  def median(numbers: List[Double]): Double = {
    val sorted = numbers.sorted
    val n = sorted.length

    if (n % 2 == 1)
      sorted(n / 2)
    else
      (sorted(n / 2 - 1) + sorted(n / 2)) / 2.0
  }

  def mode(numbers: List[Double]): List[Double] = {
    val frequencies = numbers.groupBy(identity).view.mapValues(_.size)
    val maxFrequency = frequencies.values.max

    frequencies
      .filter(_._2 == maxFrequency)
      .keys
      .toList
      .sorted
  }

  def main(args: Array[String]): Unit = {
    val numbers = List(10.0, 20.0, 20.0, 30.0, 40.0, 20.0)

    println("Numbers: " + numbers)
    println("Mean: " + mean(numbers))
    println("Median: " + median(numbers))
    println("Mode: " + mode(numbers))
  }
}
