import breeze.linalg._

object Practical6:
  def main(args: Array[String]): Unit =

    val matrix = DenseMatrix(
      (1.0, 2.0, 3.0, 4.0),
      (5.0, 6.0, 7.0, 8.0),
      (9.0, 10.0, 11.0, 12.0)
    )

    println("Original Matrix:")
    println(matrix)

    val subMatrix = matrix(0 to 1, 1 to 2)

    println("Sub-Matrix:")
    println(subMatrix)

    val rowSums = sum(subMatrix(*, ::))
    val columnSums = sum(subMatrix(::, *))

    println("Row Sums:")
    println(rowSums)

    println("Column Sums:")
    println(columnSums)