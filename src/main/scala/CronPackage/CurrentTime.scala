package CronPackage

import java.text.SimpleDateFormat
import java.util.Calendar

object CurrentTime {

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

}
