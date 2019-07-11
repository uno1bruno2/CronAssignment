package CronPackage

import scala.collection.mutable.ListBuffer

object ScheduleTimes {

  def dailyTime(time: Any, filename: String): String = time match {
    case time:Time if time.hours <= infoFromFile(0,filename,InfoFromFile.hoursFromFile).toInt-1
      => s"${infoFromFile(0,filename,InfoFromFile.hoursFromFile)}:${infoFromFile(0,filename,InfoFromFile.minutesFromFile)} today"
    case time:Time if time.hours == InfoFromFile.hoursFromFile(filename)(0).toInt && time.minute <= infoFromFile(0,filename,InfoFromFile.minutesFromFile).toInt
    => s"${infoFromFile(0,filename,InfoFromFile.hoursFromFile)}:${infoFromFile(0,filename,InfoFromFile.minutesFromFile)} today"
    case time:Time
    => s"${infoFromFile(0,filename,InfoFromFile.hoursFromFile)}:${infoFromFile(0,filename,InfoFromFile.minutesFromFile)} tomorrow"
  }

  def hourlyTime(time: Any, filename: String): String = time match {
    case time:Time if time.hours == 23 && time.minute > InfoFromFile.minutesFromFile(filename)(1).toInt
      => s"0:${infoFromFile(1,filename,InfoFromFile.minutesFromFile)} tomorrow"
    case time:Time if time.minute > infoFromFile(1,filename,InfoFromFile.minutesFromFile).toInt
      => s"${time.hours+1}:${infoFromFile(1,filename,InfoFromFile.minutesFromFile)} today"
    case time:Time
      => s"${time.hours}:${infoFromFile(1,filename,InfoFromFile.minutesFromFile)} today"
  }

  def everyMinuteTime(time: Any, filename: String): String = time match {
    case time:Time if time.hours == 23 && time.minute == 59
      => "0:00 tomorrow"
    case time:Time if time.minute == 59
      => s"${time.hours+1}:00 today"
    case time:Time if time.minute < 9
      => s"${time.hours}:0${time.minute+1} today"
    case time:Time => s"${time.hours}:${time.minute+1} today"
  }

  def sixtyTimes(time: Any, filename: String): String = time match {
    case time:Time if time.hours == InfoFromFile.hoursFromFile(filename)(3).toInt && time.minute == 59
      => s"${infoFromFile(3,filename,InfoFromFile.hoursFromFile)}:00 tomorrow"
    case time:Time if time.hours == infoFromFile(3,filename,InfoFromFile.hoursFromFile).toInt && time.minute < 9
      => s"${infoFromFile(3,filename,InfoFromFile.hoursFromFile)}:0${time.minute+1} today"
    case time:Time if time.hours == infoFromFile(3,filename,InfoFromFile.hoursFromFile).toInt
      => s"${infoFromFile(3,filename,InfoFromFile.hoursFromFile)}:${time.minute+1} today"
    case time:Time if time.hours < infoFromFile(3,filename,InfoFromFile.hoursFromFile).toInt
      => s"${infoFromFile(3,filename,InfoFromFile.hoursFromFile)}:00 today"
    case time:Time if time.hours > infoFromFile(3,filename,InfoFromFile.hoursFromFile).toInt
      => s"${infoFromFile(3,filename,InfoFromFile.hoursFromFile)}:00 tomorrow"
  }



  def infoFromFile(lineNum: Int, filename: String, func: String => ListBuffer[String]): String = {
    func(filename)(lineNum)
  }

}
