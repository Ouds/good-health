package scalajava

class ComplexFunction {

  def factorial(n: Int): Int = {
    @annotation.tailrec
    def loop(acc: Int, n: Int): Int = {
      if (n <= 1) acc
      else loop(n * acc, n - 1)
    }

    loop(1, n)
  }

  def gaoSum(gao: Int => Int)(a: Int)(b: Int): Int = {
    @annotation.tailrec
    def loop(n: Int, acc: Int): Int =
      if (n > b) acc
      else loop(n + 1, acc + gao(n))

    loop(a, 0)
  }

  def greet: Unit = {
    println(factorial(5))
    println(gaoSum(x => x)(4)(7))
  }

  def main(args: Array[String]) {
    println("Hello World!");
  }

}