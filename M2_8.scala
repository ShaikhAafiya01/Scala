import breeze.linalg._
import breeze.plot._
import scala.io.Source

object M2_8 {

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

    val file = "kmeans.csv"

    val data = Source.fromFile(file)
      .getLines()
      .drop(1)
      .map(_.split(",").map(_.toDouble))
      .toList

    val points = data.map(row =>
      DenseVector(row(0), row(1))
    )

    // Initial centroids
    var centroid1 = DenseVector(2.0, 2.0)
    var centroid2 = DenseVector(9.0, 8.0)

    var cluster1 = List[DenseVector[Double]]()
    var cluster2 = List[DenseVector[Double]]()

    // K-Means iterations
    for (_ <- 1 to 10) {

      cluster1 = List()
      cluster2 = List()

      points.foreach { point =>

        val d1 = distance(point, centroid1)
        val d2 = distance(point, centroid2)

        if (d1 < d2)
          cluster1 = point :: cluster1
        else
          cluster2 = point :: cluster2
      }

      // New centroid for Cluster 1
      if (cluster1.nonEmpty) {

        val newX =
          cluster1.map(_(0)).sum / cluster1.length

        val newY =
          cluster1.map(_(1)).sum / cluster1.length

        centroid1 = DenseVector(newX, newY)
      }

      // New centroid for Cluster 2
      if (cluster2.nonEmpty) {

        val newX =
          cluster2.map(_(0)).sum / cluster2.length

        val newY =
          cluster2.map(_(1)).sum / cluster2.length

        centroid2 = DenseVector(newX, newY)
      }
    }

    // Display result
    println("K-Means Clustering")
    println("------------------")

    println("\nCluster 1:")
    cluster1.reverse.foreach(println)

    println("\nCluster 2:")
    cluster2.reverse.foreach(println)

    println(
      f"\nCentroid 1 = (${centroid1(0)}%.2f, ${centroid1(1)}%.2f)"
    )

    println(
      f"Centroid 2 = (${centroid2(0)}%.2f, ${centroid2(1)}%.2f)"
    )

    // Data for graph
    val x1 =
      DenseVector(cluster1.map(_(0)).toArray)

    val y1 =
      DenseVector(cluster1.map(_(1)).toArray)

    val x2 =
      DenseVector(cluster2.map(_(0)).toArray)

    val y2 =
      DenseVector(cluster2.map(_(1)).toArray)

    val cx =
      DenseVector(centroid1(0), centroid2(0))

    val cy =
      DenseVector(centroid1(1), centroid2(1))

    // Graph
    val fig = Figure()
    val plt = fig.subplot(0)

    plt += plot(x1, y1, '.')
    plt += plot(x2, y2, '.')
    plt += plot(cx, cy, '+')

    plt.xlabel = "X"
    plt.ylabel = "Y"
    plt.title = "K-Means Clustering (K = 2)"

    fig.refresh()
  }
}