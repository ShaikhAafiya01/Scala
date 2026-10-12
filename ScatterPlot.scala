import java.awt.Color
import breeze.linalg.DenseVector
import breeze.plot._
import scala.util.Random

object ScatterPlot {
  def main(args: Array[String]): Unit = {
    val rnd = new Random(42)
    val n = 200
    val xs = DenseVector.fill(n)(rnd.nextDouble() * 10)
    val ys = DenseVector.fill(n)(rnd.nextDouble() * 10)

    val fig = Figure()
    val p = fig.subplot(0)
    p += scatter(xs, ys, _ => 0.12, i => if (xs(i) + ys(i) > 10) Color.RED else Color.BLUE)
    p.xlabel = "X axis"
    p.ylabel = "Y axis"
    p.title = "Scatter Plot of Random Data"
    fig.saveas("scatter_plot.png")
  }
}
