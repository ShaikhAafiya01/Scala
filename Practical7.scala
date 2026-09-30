import breeze.linalg._

object Practical7:
  def main(args: Array[String]): Unit =

    val A = DenseMatrix(
      (10.0, 20.0),
      (30.0, 40.0)
    )

    val B = DenseMatrix(
      (2.0, 4.0),
      (5.0, 8.0)
    )

    println("Matrix A:")
    println(A)

    println("Matrix B:")
    println(B)

    println("Addition:")
    println(A + B)

    println("Subtraction:")
    println(A - B)

    println("Element-wise Multiplication:")
    println(A *:* B)

    println("Element-wise Division:")
    println(A /:/ B)
