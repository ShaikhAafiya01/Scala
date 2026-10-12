import breeze.linalg.DenseVector
import breeze.plot._
import breeze.stats.distributions.{Gaussian, RandBasis}

object HistogramPlot {
  def main(args: Array[String]): Unit = {
    implicit val basis: RandBasis = RandBasis.withSeed(7)
    val data = DenseVector(Gaussian(50, 10).sample(1000).toArray)
    val binSizes = Seq(5, 15, 40)

    val fig = Figure()
    binSizes.zipWithIndex.foreach { case (bins, i) =>
      val p = fig.subplot(binSizes.length, 1, i)
      p += hist(data, bins)
      p.xlabel = "Value"
      p.ylabel = "Frequency"
      p.title = s"Histogram with $bins bins"
    }
    fig.saveas("histogram_plot.png")
  }
}
