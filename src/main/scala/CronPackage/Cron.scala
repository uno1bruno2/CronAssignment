package CronPackage

import scala.collection.mutable.ListBuffer

case class Time(hours:Int, minute:Int)

object Cron {

  var currentTime = CurrentTime.currentTime()
  var scheduleTimes = ScheduleTimes
  var information = InfoFromFile

  def infoFromFile(lineNum: Int, filename: String, func: String => ListBuffer[String]): String = {
    func(filename)(lineNum)
  }

  def start(): Unit = {
    val filename = "C:\\Users\\Admin\\IdeaProjects\\PrimeNumbersScala\\config.txt"

    println(scheduleTimes.dailyTime(currentTime,filename) + " "
      + infoFromFile(0,filename,information.commandRunFromFile))
    println(scheduleTimes.hourlyTime(currentTime,filename) + " "
      + infoFromFile(1,filename,information.commandRunFromFile))
    println(scheduleTimes.everyMinuteTime(currentTime, filename) + " "
      + infoFromFile(2,filename,information.commandRunFromFile))
    println(scheduleTimes.sixtyTimes(currentTime, filename) + " "
      + infoFromFile(3,filename,information.commandRunFromFile))
  }



}
