package com.kiwicorp.trains.network.models

object DataModels {
  // An edge - vertex1, vertex2, weight.
  case class Route (fromStation :Station, toStation: Station, distance: Int)
  
  // A Vertex - Node - Town - Station
  case class Station (townCode: Char, index: Int)
  
  case class InputData(problemType: String, inputParams: String)
}