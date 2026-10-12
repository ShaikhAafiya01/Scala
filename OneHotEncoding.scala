import scala.io.Source
import java.io.PrintWriter

object OneHotEncoding {

  def main(args: Array[String]): Unit = {
    val path = if (args.nonEmpty) args(0) else "data/customers.csv"
    val column = if (args.length > 1) args(1) else "city"

    val source = Source.fromFile(path)
    val lines = source.getLines().filter(_.trim.nonEmpty).toVector
    source.close()

    val header = lines.head.split(",").map(_.trim)
    val rows = lines.tail.map(_.split(",", -1).map(_.trim))
    val idx = header.indexOf(column)

    val categories = rows.map(_(idx)).distinct.sorted
    val newHeader = header.patch(idx, categories.map(c => s"${column}_$c"), 1)

    val encoded = rows.map { r =>
      val oneHot = categories.map(c => if (r(idx) == c) "1" else "0")
      r.patch(idx, oneHot, 1)
    }

    println(newHeader.mkString(","))
    encoded.foreach(r => println(r.mkString(",")))

    val writer = new PrintWriter("customers_encoded.csv")
    writer.println(newHeader.mkString(","))
    encoded.foreach(r => writer.println(r.mkString(",")))
    writer.close()
  }
}
