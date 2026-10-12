import scala.io.Source
import java.io.PrintWriter

object WordFrequency {

  def tokenize(text: String): Seq[String] =
    text.toLowerCase.split("[^a-z']+").map(_.replaceAll("^'+|'+$", "")).filter(_.nonEmpty).toSeq

  def main(args: Array[String]): Unit = {
    val path = if (args.nonEmpty) args(0) else "data/text.txt"
    val source = Source.fromFile(path)
    val text = source.getLines().mkString(" ")
    source.close()

    val tokens = tokenize(text)
    val frequency = tokens.groupBy(identity).view.mapValues(_.size).toSeq.sortBy { case (w, c) => (-c, w) }

    println(s"Total words  : ${tokens.length}")
    println(s"Unique words : ${frequency.length}")
    println()
    println(f"${"Word"}%-15s Count")
    frequency.foreach { case (w, c) => println(f"$w%-15s $c%d") }

    val writer = new PrintWriter("word_frequency.csv")
    writer.println("word,count")
    frequency.foreach { case (w, c) => writer.println(s"$w,$c") }
    writer.close()
  }
}
