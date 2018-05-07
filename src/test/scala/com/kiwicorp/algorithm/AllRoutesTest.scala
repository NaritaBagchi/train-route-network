package com.kiwicorp.algorithm

import org.scalatest.FunSuite
import com.kiwicorp.utils.AppData
import org.scalatest.BeforeAndAfterAll

class AllRoutesTest extends FunSuite with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    val stationsInput = Vector[Char]('A', 'B', 'C', 'D', 'E')
    val routesInput = Vector[String]("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7")
    AppData.init(stationsInput, routesInput)
  }

  test("should return correct number of trips for input hop count between stations") {
    assert(AllRoutesStrategy.compute("C-C-Hops-3") == 2)
  }

  test("should return correct number of trips for input distance between stations") {
    assert(AllRoutesStrategy.compute("C-C-Distance-30") == 7)
  }

  test("should return correct number of trips for input of exact hops between stations") {
    assert(AllRoutesStrategy.compute("A-C-Exact-4") == 3)
  }
}
