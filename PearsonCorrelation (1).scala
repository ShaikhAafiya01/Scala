import scala.io.Source
import org.apache.commons.math3.distribution.TDistribution

object PearsonCorrelation {

  def mean(v: Seq[Double]): Double = v.sum / v.length

  def pearson(x: Seq[Double], y: Seq[Double]): Double = {
    val mx = mean(x)
    val my = mean(y)
    val numerator = x.zip(y).map { case (a, b) => (a - mx) * (b - my) }.sum
    val denominator = math.sqrt(x.map(a => math.pow(a - mx, 2)).sum) * math.sqrt(y.map(b => math.pow(b - my, 2)).sum)
    numerator / denominator
  }

  def main(args: Array[String]): Unit = {
    val path = if (args.nonEmpty) args(0) else "data/study.csv"
    val source = Source.fromFile(path)
    val rows = source.getLines().drop(1).filter(_.trim.nonEmpty).map(_.split(",").map(_.trim)).toVector
    source.close()

    val x = rows.map(_(0).toDouble)
    val y = rows.map(_(1).toDouble)
    val n = x.length

    val r = pearson(x, y)
    val df = n - 2
    val t = r * math.sqrt(df / (1 - r * r))
    val pValue = 2 * (1 - new TDistribution(df).cumulativeProbability(math.abs(t)))
    val alpha = 0.05
    val tCritical = new TDistribution(df).inverseCumulativeProbability(1 - alpha / 2)

    val direction =
      if (r > 0) "positive"
      else if (r < 0) "negative"
      else "no linear"

    val strength = math.abs(r) match {
      case a if a >= 0.8 => "very strong"
      case a if a >= 0.6 => "strong"
      case a if a >= 0.4 => "moderate"
      case a if a >= 0.2 => "weak"
      case _ => "very weak"
    }

    println(f"Observations (n)        : $n")
    println(f"Pearson r               : $r%.4f")
    println(f"Degrees of freedom      : $df")
    println(f"t statistic             : $t%.4f")
    println(f"Critical t (alpha=$alpha%.2f): +/- $tCritical%.4f")
    println(f"p-value                 : $pValue%.6f")
    println(s"Relationship            : $strength $direction relationship")

    if (pValue < alpha)
      println("Decision                : Reject H0, the correlation is statistically significant")
    else
      println("Decision                : Fail to reject H0, the correlation is not statistically significant")
  }
}
