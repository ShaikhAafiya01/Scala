import breeze.linalg._
import breeze.plot._
import scala.io.Source

object M2_5 {

  def main(args: Array[String]): Unit = {

    val file = "linear_regression.csv"

    val data = Source.fromFile(file)
      .getLines()
      .drop(1)
      .map(_.split(",").map(_.toDouble))
      .toList

    val x = DenseVector(data.map(_(0)).toArray)
    val y = DenseVector(data.map(_(1)).toArray)

    val xMean = sum(x) / x.length
    val yMean = sum(y) / y.length

    var numerator = 0.0
    var denominator = 0.0

    for (i <- 0 until x.length) {
      numerator += (x(i) - xMean) * (y(i) - yMean)
      denominator += (x(i) - xMean) * (x(i) - xMean)
    }

    val slope = numerator / denominator
    val intercept = yMean - slope * xMean

    val testX = 6.0
    val prediction = intercept + slope * testX

    println("Linear Regression")
    println("-----------------")
    println(f"Slope = $slope%.2f")
    println(f"Intercept = $intercept%.2f")
    println(f"Prediction for X = $testX%.1f : $prediction%.2f")

    val predictedY =
      x.map(value => intercept + slope * value)

    val fig = Figure()
    val plt = fig.subplot(0)

    plt += plot(x, y, '.')
    plt += plot(x, predictedY, '-')

    plt.xlabel = "X"
    plt.ylabel = "Y"
    plt.title = "Linear Regression"

    fig.refresh()
  }
}
