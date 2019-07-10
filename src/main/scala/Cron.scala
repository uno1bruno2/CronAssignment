import java.text.SimpleDateFormat
import java.util.Calendar

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class Time(hours:Int, minute:Int)

object Cron {

  def currentTime(): Time = {
    val today = Calendar.getInstance().getTime()

    val minuteFormat = new SimpleDateFormat("mm")
    val hourFormat = new SimpleDateFormat("hh")
    val amPmFormat = new SimpleDateFormat("a")

    var currentHour = hourFormat.format(today).toInt
    val currentMinute = minuteFormat.format(today).toInt
    val amOrPm = amPmFormat.format(today)

    if (amOrPm == "PM") {
      currentHour = currentHour + 12
    }

    Time(currentHour, currentMinute)
  }

  def dailyTime(time: Any, filename: String): String = time match {
    case time:Time if time.hours <= infoFromFile(0,filename,hoursFromFile).toInt-1
    => s"${infoFromFile(0,filename,hoursFromFile)}:${infoFromFile(0,filename,minutesFromFile)} today"
    case time:Time if time.hours == hoursFromFile(filename)(0).toInt && time.minute <= infoFromFile(0,filename,minutesFromFile).toInt
    => s"${infoFromFile(0,filename,hoursFromFile)}:${infoFromFile(0,filename,minutesFromFile)} today"
    case time:Time
    => s"${infoFromFile(0,filename,hoursFromFile)}:${infoFromFile(0,filename,minutesFromFile)} tomorrow"
  }

  def hourlyTime(time: Any, filename: String): String = time match {
    case time:Time if time.hours == 23 && time.minute > minutesFromFile(filename)(1).toInt
    => s"0:${infoFromFile(1,filename,minutesFromFile)} tomorrow"
    case time:Time if time.minute > infoFromFile(1,filename,minutesFromFile).toInt
    => s"${time.hours+1}:${infoFromFile(1,filename,minutesFromFile)} today"
    case time:Time
    => s"${time.hours}:${infoFromFile(1,filename,minutesFromFile)} today"
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
    case time:Time if time.hours == hoursFromFile(filename)(3).toInt && time.minute == 59
    => s"${infoFromFile(3,filename,hoursFromFile)}:00 tomorrow"
    case time:Time if time.hours == infoFromFile(3,filename,hoursFromFile).toInt && time.minute < 9
    => s"${infoFromFile(3,filename,hoursFromFile)}:0${time.minute+1} today"
    case time:Time if time.hours == infoFromFile(3,filename,hoursFromFile).toInt
    => s"${infoFromFile(3,filename,hoursFromFile)}:${time.minute+1} today"
    case time:Time if time.hours < infoFromFile(3,filename,hoursFromFile).toInt
    => s"${infoFromFile(3,filename,hoursFromFile)}:00 today"
    case time:Time if time.hours > infoFromFile(3,filename,hoursFromFile).toInt
    => s"${infoFromFile(3,filename,hoursFromFile)}:00 tomorrow"
  }



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
      while (num < 1) {
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

  def infoFromFile(lineNum: Int, filename: String, func: String => ListBuffer[String]): String = {
    func(filename)(lineNum)
  }

  def start(): Unit = {
    val filename = "C:\\Users\\Admin\\IdeaProjects\\PrimeNumbersScala\\config.txt"

    println(dailyTime(currentTime(),filename) + " " + infoFromFile(0,filename,commandRunFromFile))
    println(hourlyTime(currentTime(),filename) + " " + infoFromFile(1,filename,commandRunFromFile))
    println(everyMinuteTime(currentTime(),filename) + " " + infoFromFile(2,filename,commandRunFromFile))
    println(sixtyTimes(currentTime(),filename) + " " + infoFromFile(3,filename,commandRunFromFile))
  }



}
