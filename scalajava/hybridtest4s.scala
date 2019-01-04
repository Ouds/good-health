package scalajava


object HybridTest4s {

  def TestScalaMain = {
    "TestScalaMain"
  }

  def main(args: Array[String]) {
    println("This is TestScalaMain");
   var cf = new ComplexFunction
   cf.greet
   
  }

  def test: String = {
    "TestScalaMain"
  }
  
  def doWhat(color: Color.ColorValues) = {
    if (color == Color.Red) "stop"
    else if (color == Color.Yellow) "hurry up" else "go"
  }

}