package CronPackage

import scala.collection.mutable.ListBuffer
import scala.io.Source

object InfoFromFile {

  def commandRunFromFile(filename: String): ListBuffer[String] = {
    var filecontentsList = new ListBuffer[String]()
    for (line <- Source.fromFile(filename).getLines) {
      var num = 0
      var index = 0
      while (num < 2) {
        if(line.charAt(index) == ' ') {
          num += 1
        }
        index += 1
      }
      filecontentsList += line.substring(index)
    }
    filecontentsList
  }

  def minutesFromFile(filename: String): ListBuffer[String] = {
    var filecontentsList = new ListBuffer[String]()
    for (line <- Source.fromFile(filename).getLines) {
      var num = 0
      var index = 0
      while (index < line.size && num < 1) {
        if(line.charAt(index) == ' ') {
          num += 1
        }
        index += 1
      }
      filecontentsList += line.substring(0,index-1)
    }
    filecontentsList
  }

  def hoursFromFile(filename: String): ListBuffer[String] = {
    var filecontentsList = new ListBuffer[String]()
    for (line <- Source.fromFile(filename).getLines) {
      var num = 0
      var indexStart = 0
      var indexEnd = 0
      while (num < 2) {
        if(line.charAt(indexEnd) == ' ') {
          if (num == 0) {
            indexStart = indexEnd + 1
          }
          num += 1
        }
        indexEnd += 1
      }
      filecontentsList += line.substring(indexStart,indexEnd-1)
    }
    filecontentsList
  }

}
