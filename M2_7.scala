import breeze.linalg._
import breeze.plot._
import scala.io.Source

object M2_7 {

  def distance(a: DenseVector[Double],
               b: DenseVector[Double]): Double = {

    var sum = 0.0

    for (i <- 0 until a.length) {
      val d = a(i) - b(i)
      sum += d * d
    }

    math.sqrt(sum)
  }

  def main(args: Array[String]): Unit = {

    val file = "nearest_neighbor.csv"

    val data = Source.fromFile(file)
      .getLines()
      .drop(1)
      .map(_.split(","))
      .toList

    val points = data.map(row =>
      DenseVector(row(0).toDouble, row(1).toDouble)
    )

    val labels = data.map(row => row(2).toInt)

    val testPoint = DenseVector(5.0, 4.0)

    val distances = points.zip(labels).map {
      case (point, label) =>
        (distance(point, testPoint), label)
    }

    val nearest = distances.minBy(_._1)

    println("Nearest Neighbor Classification")
    println("--------------------------------")
    println(f"Test Point = (${testPoint(0)}, ${testPoint(1)})")
    println(f"Nearest Distance = ${nearest._1}%.2f")
    println(s"Predicted Class = ${nearest._2}")

    val class0 = points.zip(labels).filter(_._2 == 0).map(_._1)

    val class1 = points.zip(labels).filter(_._2 == 1).map(_._1)

    val x0 = DenseVector(class0.map(_(0)).toArray)
    val y0 = DenseVector(class0.map(_(1)).toArray)

    val x1 = DenseVector(class1.map(_(0)).toArray)
    val y1 = DenseVector(class1.map(_(1)).toArray)

    val testX = DenseVector(testPoint(0))
    val testY = DenseVector(testPoint(1))

    val fig = Figure()
    val plt = fig.subplot(0)

    plt += plot(x0, y0, '.')
    plt += plot(x1, y1, '.')
    plt += plot(testX, testY, '+')

    plt.xlabel = "X"
    plt.ylabel = "Y"
    plt.title = "Nearest Neighbor Classification"

    fig.refresh()
  }
}
