package scalajava

object Color extends Enumeration {
  type ColorValues = Value
  val Red = Value(0, "Stop")
  val Yellow = Value(10)
  val Green = Value("Go")

  def doWhat(color: ColorValues) = {
    if (color == Red) "stop"
    else if (color == Yellow) "hurry up" else "go"
  }
}