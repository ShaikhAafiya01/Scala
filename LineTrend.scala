import scala.io.Source
import breeze.linalg.DenseVector
import breeze.plot._

object LineTrend {
  def main(args: Array[String]): Unit = {
    val path = if (args.nonEmpty) args(0) else "data/monthly_sales.csv"
    val source = Source.fromFile(path)
    val rows = source.getLines().drop(1).filter(_.trim.nonEmpty).map(_.split(",").map(_.trim)).toVector
    source.close()

    val months = rows.map(_(0))
    val sales = DenseVector(rows.map(_(1).toDouble).toArray)
    val xs = DenseVector((0 until months.length).map(_.toDouble).toArray)

    val fig = Figure()
    val p = fig.subplot(0)
    p += plot(xs, sales, '-', "blue", "Sales")
    p.legend = true
    p.xlabel = "Month index"
    p.ylabel = "Sales"
    p.title = "Monthly Sales Trend"
    fig.saveas("line_trend.png")
  }
}
