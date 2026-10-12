import scala.io.Source
import scala.util.Try
import java.time.LocalDate
import java.awt.Color
import breeze.linalg.DenseVector
import breeze.plot._

object MovingAverage {

  case class Record(date: LocalDate, close: Double)

  def sma(v: Vector[Double], w: Int): Vector[Double] =
    v.sliding(w).map(win => win.sum / w).toVector

  def wma(v: Vector[Double], w: Int): Vector[Double] = {
    val weights = (1 to w).map(_.toDouble).toVector
    val denom = weights.sum
    v.sliding(w).map(win => win.zip(weights).map { case (a, b) => a * b }.sum / denom).toVector
  }

  def ema(v: Vector[Double], w: Int): Vector[Double] = {
    val alpha = 2.0 / (w + 1)
    v.tail.scanLeft(v.head)((prev, x) => alpha * x + (1 - alpha) * prev)
  }

  def pad(v: Vector[Double], total: Int): Vector[Double] =
    Vector.fill(total - v.length)(Double.NaN) ++ v

  def mae(actual: Vector[Double], ma: Vector[Double], w: Int): Double = {
    val errors = (w until actual.length).map(t => math.abs(actual(t) - ma(t - 1)))
    errors.sum / errors.length
  }

  def rmse(actual: Vector[Double], ma: Vector[Double], w: Int): Double = {
    val errors = (w until actual.length).map(t => math.pow(actual(t) - ma(t - 1), 2))
    math.sqrt(errors.sum / errors.length)
  }

  def main(args: Array[String]): Unit = {
    val path = if (args.nonEmpty) args(0) else "data/stock.csv"
    val window = if (args.length > 1) args(1).toInt else 5

    val source = Source.fromFile(path)
    val parsed = source.getLines().drop(1).map(_.split(",").map(_.trim)).flatMap { cols =>
      Try(Record(LocalDate.parse(cols(0)), cols(1).toDouble)).toOption
    }.toVector
    source.close()

    val cleaned = parsed.groupBy(_.date).map { case (_, rs) => rs.head }.toVector.sortBy(_.date.toEpochDay)

    val closes = cleaned.map(_.close)
    val n = closes.length

    val returns = closes.sliding(2).map(p => (p(1) - p(0)) / p(0)).toVector
    val rollingVol = returns.sliding(window).map { w =>
      val m = w.sum / w.length
      math.sqrt(w.map(r => math.pow(r - m, 2)).sum / w.length)
    }.toVector

    println(s"Raw rows parsed        : ${parsed.length}")
    println(s"Rows after cleaning    : $n")
    println(f"Mean daily return      : ${returns.sum / returns.length}%.5f")
    println(f"Average rolling vol    : ${rollingVol.sum / rollingVol.length}%.5f")

    val smaV = pad(sma(closes, window), n)
    val wmaV = pad(wma(closes, window), n)
    val emaV = ema(closes, window)

    println(s"\nWindow size: $window")
    println("Day  Close     SMA       WMA       EMA")
    (0 until n).foreach { i =>
      println(f"${i + 1}%3d  ${closes(i)}%7.2f  ${smaV(i)}%8.2f  ${wmaV(i)}%8.2f  ${emaV(i)}%8.2f")
    }

    val scores = Seq(
      "SMA" -> (mae(closes, smaV, window), rmse(closes, smaV, window)),
      "WMA" -> (mae(closes, wmaV, window), rmse(closes, wmaV, window)),
      "EMA" -> (mae(closes, emaV, window), rmse(closes, emaV, window))
    )

    println("\nOne-step-ahead error")
    scores.foreach { case (name, (m, r)) => println(f"$name  MAE=$m%.4f  RMSE=$r%.4f") }

    val best = scores.minBy(_._2._1)._1
    println(s"\nBest moving average for this dataset: $best")
    println("SMA gives equal weight to all days and lags most. WMA weights recent days more. EMA reacts fastest to recent changes through exponentially decaying weights.")

    val xs = DenseVector((0 until n).map(_.toDouble).toArray)
    val from = window - 1
    val xsMa = DenseVector((from until n).map(_.toDouble).toArray)

    val fig = Figure()
    val p = fig.subplot(0)
    p += plot(xs, DenseVector(closes.toArray), '-', "black", "Close")
    p += plot(xsMa, DenseVector(smaV.drop(from).toArray), '-', "blue", "SMA")
    p += plot(xsMa, DenseVector(wmaV.drop(from).toArray), '-', "red", "WMA")
    p += plot(xs, DenseVector(emaV.toArray), '-', "green", "EMA")
    p.legend = true
    p.xlabel = "Day"
    p.ylabel = "Price"
    p.title = s"Moving Averages (window = $window)"
    fig.saveas("moving_average.png")
  }
}
