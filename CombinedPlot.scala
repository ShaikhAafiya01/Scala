import java.awt.Color
import breeze.linalg.DenseVector
import breeze.plot._
import scala.util.Random

object CombinedPlot {
  def main(args: Array[String]): Unit = {
    val rnd = new Random(11)
    val n = 80
    val xArr = Array.fill(n)(rnd.nextDouble() * 10)
    val yArr = xArr.map(x => 2.0 * x + 3.0 + rnd.nextGaussian() * 2.0)

    val mx = xArr.sum / n
    val my = yArr.sum / n
    val slope = xArr.zip(yArr).map { case (a, b) => (a - mx) * (b - my) }.sum / xArr.map(a => math.pow(a - mx, 2)).sum
    val intercept = my - slope * mx

    val lineX = DenseVector(Array(0.0, 10.0))
    val lineY = lineX.map(v => slope * v + intercept)

    val xs = DenseVector(xArr)
    val ys = DenseVector(yArr)

    val fig = Figure()
    val p = fig.subplot(0)
    p += scatter(xs, ys, _ => 0.12, _ => Color.BLUE)
    p += plot(lineX, lineY, '-', "red")
    p.xlabel = "X"
    p.ylabel = "Y"
    p.title = "Scatter Plot with Regression Line"
    fig.saveas("combined_plot.png")
  }
}
