import breeze.linalg.DenseVector

object Practical4 {
  def main(args: Array[String]): Unit = {
    val v1 = DenseVector(1.0, 2.0, 3.0, 4.0, 5.0)
    val v2 = DenseVector(5.0, 4.0, 3.0, 2.0, 1.0)

    val sumValue = breeze.linalg.sum(v1)
    val meanValue = sumValue / v1.length
    val dotProduct = v1 dot v2

    println("Vector 1: " + v1)
    println("Vector 2: " + v2)
    println("Sum: " + sumValue)
    println("Mean: " + meanValue)
    println("Dot Product: " + dotProduct)
  }
}
