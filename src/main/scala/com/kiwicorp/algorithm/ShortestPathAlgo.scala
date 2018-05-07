package com.kiwicorp.algorithm

import scala.collection.immutable.Map
import scala.collection.mutable.Queue

import com.kiwicorp.trains.network.NetworkGraph
import com.kiwicorp.trains.network.models.DataModels.Station
import com.kiwicorp.utils.AppData

trait ShortestPathAlgo {

  def execute(inputParams: String): Int = {
    val inputParamsArr = inputParams.split("-")
    val source = AppData.stationsMap(inputParamsArr(0).head)
    val destination = AppData.stationsMap(inputParamsArr(1).head)

    val distanceTable = buildDistanceTable(source)
    computeResult(source, destination, distanceTable)
  }

  def buildDistanceTable(source: Station): Map[Station, (Int, Station)]

  def computeResult(source: Station, destination: Station, distanceTable: Map[Station, (Int, Station)]): Int = {
    var distanceFromSource = distanceTable.get(destination).get._1
    if (source.equals(destination)) {
      val itr = distanceTable.keys.iterator
      var shortestDistance = 1000
      while (itr.hasNext) {
        val dest = itr.next
        if (dest != source) {
          val toSource = NetworkGraph.adjacencyMatrix(dest.index)(source.index)
          val distFromDT = distanceTable.get(dest).get._1
          val totalDistance = (toSource + distFromDT)
          if (toSource > 0 && shortestDistance > totalDistance) {
            shortestDistance = totalDistance
          }
        }
      }
      distanceFromSource = shortestDistance
    }
    //println(s"Length of the Shortest route from ${source.townCode} to ${destination.townCode} is $distanceFromSource units")
    distanceFromSource
  }
}

object NumberOfHopsAlgo extends ShortestPathAlgo {
  def buildDistanceTable(source: Station): Map[Station, (Int, Station)] = {
    var distanceTable = Map[Station, (Int, Station)]()
    val destinationsQueue = Queue[Station]()

    destinationsQueue.enqueue(source)
    distanceTable += (source -> (0, source))

    while (!destinationsQueue.isEmpty) {
      val currentStation = destinationsQueue.dequeue
      val neighbours = NetworkGraph.getAdjacentStations(currentStation)
      for (neighbour <- neighbours) {
        if (!distanceTable.keySet.exists(_ == neighbour)) {
          val currentStationDistance = distanceTable.get(currentStation).get._1
          distanceTable += (neighbour -> (currentStationDistance + 1, currentStation))
          destinationsQueue.enqueue(neighbour)
        }
      }
    }
    distanceTable
  }
}

object WeightedGraphAlgo extends ShortestPathAlgo {
  def buildDistanceTable(source: Station): Map[Station, (Int, Station)] = {
    var distanceTable = Map[Station, (Int, Station)]()
    var visitedSources = Set[Station]()
    val processingQueue = Queue[Station]()

    processingQueue.enqueue(source)
    distanceTable += (source -> (0, source))

    while (!processingQueue.isEmpty) {
      val currentStation = processingQueue.dequeue
      // keep track of visited visitedSources, to not visit them again (cyclic graph)
      visitedSources = visitedSources + (currentStation)

      val currentDistance = distanceTable.get(currentStation).get._1
      val neighbours = NetworkGraph.getAdjacentStations(currentStation)

      for (neighbour <- neighbours) {
        val newDistanceToNeighbour = currentDistance + NetworkGraph.getEdgeWeight(currentStation, neighbour)

        if (distanceTable.get(neighbour).equals(None)) {
          distanceTable += (neighbour -> (newDistanceToNeighbour, currentStation))
        } else {
          val recordedDistanceToNeighbour = distanceTable.get(neighbour).get._1
          if (newDistanceToNeighbour < recordedDistanceToNeighbour)
            distanceTable += (neighbour -> (newDistanceToNeighbour, currentStation))
        }
        if (!visitedSources.contains(neighbour)) {
          processingQueue.enqueue(neighbour)
        }
      }
    }
    distanceTable
  }
}
