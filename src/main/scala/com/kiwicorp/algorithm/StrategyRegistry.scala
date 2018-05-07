package com.kiwicorp.algorithm

object StrategyType extends Enumeration {
  type StrategyType = Value
  val ShortestDistance, NumberOfHops, Distance, AllRoutes = Value
}

trait IStrategy {
  def compute(inputParams: String): Int
}

case class ShortestDistanceStrategy(algorithm: ShortestPathAlgo) extends IStrategy {
  def compute(inputParams: String): Int = {
    algorithm.execute(inputParams)
  }
}

object StrategyRegistry {
  import StrategyType._
  def lookup(strategyType: String): IStrategy = {
    StrategyType.withName(strategyType) match {
      case ShortestDistance => ShortestDistanceStrategy(WeightedGraphAlgo)
      case NumberOfHops     => ShortestDistanceStrategy(NumberOfHopsAlgo)
      case Distance         => DistanceStrategy
      case AllRoutes        => AllRoutesStrategy
    }
  }
}

