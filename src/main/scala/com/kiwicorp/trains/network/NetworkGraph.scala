package com.kiwicorp.trains.network

import com.kiwicorp.trains.network.models.DataModels._
import com.kiwicorp.utils.AppData

// Adjacency matrix graph
object NetworkGraph {
  val noOfVertices = AppData.stations.length
  var adjacencyMatrix = Array.ofDim[Int](noOfVertices, noOfVertices)

  /**
   *  Add a route in the network
   */
  def addRoute(fromStation: Station, toStation: Station, distance: Int): Unit = {
    val routeObj: Route = Route(fromStation, toStation, distance)
    adjacencyMatrix(routeObj.fromStation.index)(routeObj.toStation.index) = routeObj.distance;
  }

  /**
   * Get adjacent stations of the given station.
   */
  def getAdjacentStations(station: Station): Vector[Station] = {
    var adjacentStations = Vector[Station]()
    for (i <- 0 until noOfVertices) {
      if (adjacencyMatrix(station.index)(i) > 0) {
        adjacentStations = adjacentStations :+ AppData.stations(i);
      }
    }
    adjacentStations
  }

  /**
   * Get the edge weight between the supplied stations
   */
  def getEdgeWeight(sourceStation: Station, destinationStation: Station): Int = {
    adjacencyMatrix(sourceStation.index)(destinationStation.index)
  }

  def display(): Unit = {
    for (i <- 0 until noOfVertices) {
      for (j <- 0 until noOfVertices) {
        print(adjacencyMatrix(i)(j) + " ")
      }
      println()
    }
  }
}
