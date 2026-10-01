from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("JoinCSV") \
    .master("local[*]") \
    .getOrCreate()

students = spark.read.csv(
    "students.csv",
    header=True,
    inferSchema=True
)

marks = spark.read.csv(
    "marks.csv",
    header=True,
    inferSchema=True
)

print("Students Data:")
students.show()

print("Marks Data:")
marks.show()

result = students.join(
    marks,
    "ID",
    "inner"
).select(
    "ID",
    "Name",
    "Department",
    "Marks"
)

print("Joined Data:")
result.show()

result.write \
    .mode("overwrite") \
    .option("header", True) \
    .csv("joined_output")

print("Output written to joined_output")

spark.stop()