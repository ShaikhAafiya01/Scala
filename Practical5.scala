import breeze.linalg.DenseMatrix
import breeze.linalg.det
import breeze.stats.distributions.Rand

object Practical5 extends App {
  val matrix = DenseMatrix.rand[Double](3, 3, Rand.uniform)

  val transpose = matrix.t
  val determinant = det(matrix)

  println("Random Matrix:")
  println(matrix)

  println("Transpose:")
  println(transpose)

  println(f"Determinant: $determinant%.4f")
}
