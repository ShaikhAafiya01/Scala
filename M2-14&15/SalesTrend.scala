import scala.util.Random

object SalesTrend {
  def main(args: Array[String]): Unit = {
    val dailySales = (1 to 30).map { date =>
      (date, 200 + Random.nextInt(100))
    }

    println("Monthly Sales Data:")

    dailySales.foreach {
      case (date, amount) =>
        println(s"Date $date: $amount units")
    }

    val totalSales = dailySales.map(_._2).sum
    val averageSales = totalSales.toDouble / dailySales.size
    val highestSale = dailySales.maxBy(_._2)
    val lowestSale = dailySales.minBy(_._2)

    println(f"\nTotal Sales: $totalSales units")
    println(f"Average Sales: $averageSales%.2f units")
    println(s"Highest Sales: Day ${highestSale._1} - ${highestSale._2} units")
    println(s"Lowest Sales: Day ${lowestSale._1} - ${lowestSale._2} units")
  }
}