package com.kiwicorp.algorithm

import com.kiwicorp.trains.network.NetworkGraph
import com.kiwicorp.utils.AppData

object DistanceStrategy extends IStrategy {
  def compute(inputParams: String): Int = {
    val stations = inputParams.split("-")
    var distanceCount = 0
    var routeExists = true
    for (i <- 0 until stations.length) {
      if (i + 1 < stations.length && routeExists) {
        val sourceStationIndex = AppData.stationsMap.get(stations(i).head).get.index
        val destStationIndex = AppData.stationsMap.get(stations(i + 1).head).get.index
        if (NetworkGraph.adjacencyMatrix(sourceStationIndex)(destStationIndex) > 0) {
          distanceCount += NetworkGraph.adjacencyMatrix(sourceStationIndex)(destStationIndex)
        } else {
          println(s"NO SUCH ROUTE for $inputParams")
          routeExists = false
        }
      }
    }
    if (routeExists) distanceCount else 0
  }
}
