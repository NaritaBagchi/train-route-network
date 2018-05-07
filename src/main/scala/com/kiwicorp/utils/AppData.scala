package com.kiwicorp.utils

import scala.collection.immutable.Map

import com.kiwicorp.trains.network.models.DataModels.Station
import com.kiwicorp.trains.network.NetworkGraph

object AppData {
  // (A -> Station(A, 0)), (B -> Station(B, 0)), ...
  var stationsMap = Map.empty[Char, Station]
  // [Station(A, 0), Station(B, 0), ...]
  var stations = Vector[Station]()

  def init(stationsInput: Vector[Char], routesInput: Vector[String]) = {
    stationsInput.zipWithIndex.foreach {
      case (station, index) => {
        val stationObj = Station(station, index)
        stationsMap += (station -> stationObj)
        stations = stations :+ stationObj
      }
    }
    buildNetworkGraph(routesInput)
  }

  private[this] def buildNetworkGraph(routesInput: Vector[String]) = {
    for (route <- routesInput) {
      val fromStation = AppData.stationsMap.get(route.charAt(0)).get
      val toStation = AppData.stationsMap.get(route.charAt(1)).get
      val distance = route.charAt(2).toInt - 48
      NetworkGraph.addRoute(fromStation, toStation, distance)
    }
  }
}
