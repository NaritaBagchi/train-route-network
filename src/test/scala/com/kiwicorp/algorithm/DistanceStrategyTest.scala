package com.kiwicorp.algorithm

import org.scalatest.FunSuite
import com.kiwicorp.utils.AppData
import org.scalatest.BeforeAndAfterAll

class DistanceStrategyTest extends FunSuite with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    val stationsInput = Vector[Char]('A', 'B', 'C', 'D', 'E')
    val routesInput = Vector[String]("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7")
    AppData.init(stationsInput, routesInput)
  }

  test("should return correct number of edges between stations") {
    assert(DistanceStrategy.compute("A-E-B-C-D") == 22)
  }

  test("should return zero if no path found between stations") {
    assert(DistanceStrategy.compute("A-E-C") == 0)
  }

}