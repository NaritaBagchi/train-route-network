package com.kiwicorp.algorithm

import org.scalatest._
import com.kiwicorp.utils.AppData
import org.scalatest.BeforeAndAfterAll

class ShortestPathAlgoTest extends FunSuite with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    val stationsInput = Vector[Char]('A', 'B', 'C', 'D', 'E')
    val routesInput = Vector[String]("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7")
    AppData.init(stationsInput, routesInput)
  }

  test("should return correct shortest distance between same source and destination stations") {
    assert(WeightedGraphAlgo.execute("A-C") == 9)
  }

  test("should return correct shortest distance for different source and destination stations") {
    assert(WeightedGraphAlgo.execute("B-B") == 9)
  }
}