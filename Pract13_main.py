from pyspark.sql import SparkSession
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.classification import LogisticRegression
from pyspark.ml import Pipeline
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

spark = SparkSession.builder \
    .appName("Customer Classification") \
    .master("local[*]") \
    .getOrCreate()

data = spark.read.csv(
    "customer_classification.csv",
    header=True,
    inferSchema=True
)

print("Customer Dataset:")
data.show()

assembler = VectorAssembler(
    inputCols=["age", "income"],
    outputCol="features"
)

classifier = LogisticRegression(
    featuresCol="features",
    labelCol="label"
)

pipeline = Pipeline(
    stages=[assembler, classifier]
)

train_data, test_data = data.randomSplit(
    [0.75, 0.25],
    seed=10
)

print("Training Data:")
train_data.show()

print("Test Data:")
test_data.show()

model = pipeline.fit(train_data)

predictions = model.transform(test_data)

print("Classification Results:")
predictions.select(
    "age",
    "income",
    "label",
    "prediction",
    "probability"
).show(truncate=False)

evaluator = MulticlassClassificationEvaluator(
    labelCol="label",
    predictionCol="prediction",
    metricName="accuracy"
)

accuracy = evaluator.evaluate(predictions)

print("Model Accuracy:", accuracy)

spark.stop()
