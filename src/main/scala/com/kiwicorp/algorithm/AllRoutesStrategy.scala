package com.kiwicorp.algorithm

import scala.collection.mutable.ListBuffer

import com.kiwicorp.trains.network.NetworkGraph
import com.kiwicorp.trains.network.models.DataModels.Station
import com.kiwicorp.utils.AppData
import scala.collection.mutable.ArrayBuffer

object LimitType extends Enumeration {
  type LimitType = Value
  val Hops, Distance, Exact = Value
}

object AllRoutesStrategy extends IStrategy {
  import LimitType._
  case class AllRoutesInput(source: Station, destination: Station, limitType: LimitType, limitCount: Int)

  private def parseInputParams(inputParams: String): AllRoutesInput = {
    val params = inputParams.split("-")
    val source = AppData.stationsMap(params(0).head)
    val destination = AppData.stationsMap(params(1).head)
    val limitType = LimitType.withName(params(2))
    val limitCount = params(3).toInt
    AllRoutesInput(source, destination, limitType, limitCount)
  }

  def compute(inputParams: String): Int = {
    val input = parseInputParams(inputParams)
    val result = ArrayBuffer.empty[ListBuffer[Station]]
    input.limitType match {
      case Exact => searchAllPathWithExact(input, input.source, ListBuffer(input.source), result)
      case _     => searchAllPathWithMax(input, input.source, ListBuffer(input.source), 0, result)
    }
    result.length
  }

  private[this] def searchAllPathWithExact(input: AllRoutesInput, current: Station, visited: ListBuffer[Station], 
                                                             pathResult: ArrayBuffer[ListBuffer[Station]]): Unit = {
    val neighbours = NetworkGraph.getAdjacentStations(current)
    for (neighbour <- neighbours) {
      visited += (neighbour)
      // Plus 1 for the destination
      if ((visited.length == input.limitCount + 1) && foundDestination(neighbour, input.destination)) {
        pathResult += visited
      } else if (isHopCountWithinLimit(visited, input.limitCount + 2)) {
        searchAllPathWithExact(input, neighbour, visited, pathResult)
      }
      visited.remove(visited.length - 1)
    }
  }

  private[this] def searchAllPathWithMax(input: AllRoutesInput, current: Station, visited: ListBuffer[Station],
                                            accumulator: Int, pathResult: ArrayBuffer[ListBuffer[Station]]): Unit = {
    val neighbours = NetworkGraph.getAdjacentStations(current)
    for (neighbour <- neighbours) {
      visited += (neighbour)
      val newDistance = accumulator + NetworkGraph.getEdgeWeight(current, neighbour)
      if (checkLimits(input.limitType, input.limitCount, visited, newDistance)) {
        if (foundDestination(neighbour, input.destination)) {
          pathResult += visited
        }
        searchAllPathWithMax(input, neighbour, visited, newDistance, pathResult)
      }
      visited.remove(visited.length - 1)
    }
  }

  private def checkLimits(limitType: LimitType, limitCount: Int, visited: ListBuffer[Station], distance: Int): Boolean = {
    limitType match {
      case Distance => isVisitedDistanceWithinLimit(distance, limitCount)
      case Hops     => isHopCountWithinLimit(visited, limitCount + 2)
    }
  }

  private def isVisitedDistanceWithinLimit(distance: Int, limitCount: Int) = distance < limitCount

  private def isHopCountWithinLimit(visited: ListBuffer[Station], limitCount: Int) = visited.length < limitCount

  private def foundDestination(current: Station, destination: Station) = current == destination

}
