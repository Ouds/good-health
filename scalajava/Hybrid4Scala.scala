package scalajava

import scala.beans.BeanProperty
import scala.collection.mutable.ArrayBuffer

class Hybrid4Scala {
  
  @BeanProperty var name = 0
  /**
   * 将会生成四个方法：
   * 1、name: String
   * 2、name _= (newValue: String): Unit
   * 3、getName(): String
   * 4、setName(newValue: String): Unit
   */

  def getStr: String = {
    val h4Java: Hybrid4Java = new Hybrid4Java
    "Hybrid4Scala call " + h4Java.getStr
  }
  
  /**
   * 内部类
   */
  class Member(val name: String) {
    val contacts = new ArrayBuffer[Member]
  }
  private val members = new ArrayBuffer[Member]

}