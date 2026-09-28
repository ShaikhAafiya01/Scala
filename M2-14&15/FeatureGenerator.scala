object FeatureGenerator {
  def main(args: Array[String]): Unit = {
    val values = List(2, 4, 6)

    val polynomialFeatures = values.flatMap { value =>
      List(
        value,
        value * value,
        value * value * value
      )
    }

    println("Generated Polynomial Features:")
    println(polynomialFeatures)
  }
}

