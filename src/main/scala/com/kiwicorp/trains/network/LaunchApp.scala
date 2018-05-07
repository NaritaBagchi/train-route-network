package com.kiwicorp.trains.network

import com.kiwicorp.algorithm.StrategyRegistry
import com.kiwicorp.trains.network.models.DataModels.InputData
import com.kiwicorp.utils.AppData

object LaunchApp {
  
  def main(args: Array[String]): Unit = {
    var stationsInput = Vector[Char]()
    var routesInput = Vector[String]()

    stationsInput = stationsInput ++ args(0).toCharArray()
    routesInput = routesInput ++ args(1).split(",")
    AppData.init(stationsInput, routesInput)

    val inputData = parseInput(args(2))
    println(StrategyRegistry.lookup(inputData.problemType).compute(inputData.inputParams))
  }

  private[this] def parseInput(inputString: String): InputData = {
    val inputTokens = inputString.split("_")
    InputData(inputTokens(0), inputTokens(1))
  }
}
