import scala.util.Random

object Practical3 {
  def main(args: Array[String]): Unit = {
    val data = Array.fill(10)(Random.nextInt(100) + 1)
    val mean = data.sum.toDouble / data.length
    val variance = data.map(x => math.pow(x - mean, 2)).sum / data.length
    val standardDeviation = math.sqrt(variance)

    println(s"Dataset: ${data.mkString(", ")}")
    println(s"Variance: $variance")
    println(s"Standard Deviation: $standardDeviation")
  }
}

