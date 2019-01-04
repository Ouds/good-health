// 近似 knn 算法

// 使用基于 z-value 的 knn 进行中小规模的计算
// 使用基于 LSH 的 method 进行大规模的计算

package knn

import scala.collection.mutable.{ArrayBuffer, PriorityQueue}

class approxKNN() {

  val r = scala.util.Random

  type Point = Array[Double]

  val alpha = 2
  val gamma = 5

  def approxKNN(train: ArrayBuffer[Point], test: ArrayBuffer[Point], k: Int):
  ArrayBuffer[(Point, Array[Point])] = {
  val dim = train.head.length
    if ( dim < 30) {
      val ZknnClass = new zKNN(alpha, gamma)
      ZknnClass.zknnQuery(train, test, k)
    } else {
      // do LSH method, below is a place-holder for now
      val lshKnnClass = new lshKNN(alpha)
      lshKnnClass.lshknnQuery(train, test, k)
    }
  }

  // method for brute-force knn using a priority queue  
  def distance(a: Point, b: Point): Double = {
    math.sqrt(a.zipWithIndex.map { x =>
      (x._1 - b(x._2)) * (x._1 - b(x._2))
    }.sum)
  }

  def basicknnQuery(train: scala.collection.mutable.Set[Point], test: ArrayBuffer[Point], k: Int):
  ArrayBuffer[(Point, Array[Point])] = {

    val queue = new PriorityQueue[ (Point, Double) ]()(Ordering.by(x => x._2))
    val out = new ArrayBuffer[ (Point, Array[Point]) ]

    for (testPoint <- test) {      
      val outSingle = new Array[Point](k)
      for (trainPoint <- train) {
        queue.enqueue((trainPoint, distance(testPoint, trainPoint)))
        if (queue.size > k) {
          queue.dequeue()
        }
      }
      for (i <- 0 until k) {
        outSingle(i) = queue.dequeue()._1
      }
      out += ((testPoint, outSingle))
    }   
  return out 
  }

  // prob don't need to overload
  def basicknnQuery(train: ArrayBuffer[Point], test: ArrayBuffer[Point], k: Int):
  ArrayBuffer[(Point, Array[Point])] = {

    val queue = new PriorityQueue[ (Point, Double) ]()(Ordering.by(x => x._2))
    val out = new ArrayBuffer[ (Point, Array[Point]) ]

    for (testPoint <- test) {      
      val outSingle = new Array[Point](k)
      for (trainPoint <- train) {
        queue.enqueue((trainPoint, distance(testPoint, trainPoint)))
        if (queue.size > k) {
          queue.dequeue()
        }
      }
      for (i <- 0 until k) {
        outSingle(i) = queue.dequeue()._1
      }
      out += ((testPoint, outSingle))
    }   
  return out 
  }


  def basicknnQuerySingleTest(train: scala.collection.mutable.Set[Point], test: Point, k: Int):
  (Point, Array[Point]) = {
    basicknnQuery(train, ArrayBuffer(test), k).head
 }

}
