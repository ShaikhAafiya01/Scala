import breeze.linalg._
import breeze.plot._
import scala.io.Source

object M2_6 {

  def sigmoid(z: Double): Double = {
    1.0 / (1.0 + math.exp(-z))
  }

  def main(args: Array[String]): Unit = {

    val file = "logistic_regression.csv"

    val data = Source.fromFile(file)
      .getLines()
      .drop(1)
      .map(_.split(",").map(_.toDouble))
      .toList

    val x = DenseVector(data.map(_(0)).toArray)
    val y = DenseVector(data.map(_(1)).toArray)

    var w = 0.0
    var b = 0.0

    val learningRate = 0.1
    val iterations = 1000

    for (_ <- 1 to iterations) {

      var dw = 0.0
      var db = 0.0

      for (i <- 0 until x.length) {

        val prediction = sigmoid(w * x(i) + b)
        val error = prediction - y(i)

        dw += error * x(i)
        db += error
      }

      w -= learningRate * dw / x.length
      b -= learningRate * db / x.length
    }

    println("Logistic Regression")
    println("-------------------")
    println(f"Weight = $w%.2f")
    println(f"Bias = $b%.2f")

    val testX = 4.5
    val probability = sigmoid(w * testX + b)

    val predictedClass =
      if (probability >= 0.5) 1 else 0

    println(f"Test X = $testX%.1f")
    println(f"Probability = $probability%.2f")
    println(s"Predicted Class = $predictedClass")

    val graphX =
      DenseVector((1 to 60).map(_.toDouble / 10).toArray)

    val graphY =
      graphX.map(value => sigmoid(w * value + b))

    val fig = Figure()
    val plt = fig.subplot(0)

    plt += plot(x, y, '.')
    plt += plot(graphX, graphY, '-')

    plt.xlabel = "X"
    plt.ylabel = "Probability"
    plt.title = "Logistic Regression"

    fig.refresh()
  }
}
