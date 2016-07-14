package com.cupenya.modules.business.hour.calculation

import com.cupenya.modules.business.hour.SlaSchedule
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime


trait BusinessHourCalculation {
  def calculateBusinessElapsedTime(start: Long, end: Long, slaSchedule: SlaSchedule) = {
    val startDate = new DateTime(start, slaSchedule.timezone)
    val endDate = new DateTime(end, slaSchedule.timezone)
    val roundedStartDate = startDate.hourOfDay().roundCeilingCopy()
    val roundedEndDate = endDate.hourOfDay().roundFloorCopy()
    val lastHour = endDate.getMinuteOfHour.minutes + endDate.getSecondOfMinute.seconds + endDate.getMillisOfSecond.millis
    val firstHour = 1.hour - (startDate.getMinuteOfHour.minutes + startDate.getSecondOfMinute.seconds + startDate.getMillisOfSecond.millis)
    var currentDate = roundedStartDate
    def isExcluded(dateTime: DateTime): Boolean = {
      slaSchedule.excludedWeekdays.contains(dateTime.dayOfWeek().get()) ||
        slaSchedule.excludedDates.contains(dateTime.toLocalDate) ||
        slaSchedule.excludedHours.contains(dateTime.hourOfDay().get())
    }
    var businessTime = 0.hour
    while (currentDate < roundedEndDate) {
      if (! isExcluded(currentDate)) {
        businessTime += 1.hour
      }
      currentDate += 1.hour
    }
    (isExcluded(startDate), isExcluded(endDate)) match {
      case (true, false) =>
        (businessTime + lastHour).millis
      case (false, true) =>
        (businessTime + firstHour).millis
      case (true, true) =>
        businessTime.millis
      case (false, false) =>
        if (startDate.toLocalDate == endDate.toLocalDate && startDate.hourOfDay().get() == endDate.hourOfDay().get()) {
          val start = startDate.getMinuteOfHour.minutes + startDate.getSecondOfMinute.seconds + startDate.getMillisOfSecond.millis
          val end = endDate.getMinuteOfHour.minutes + endDate.getSecondOfMinute.seconds + endDate.getMillisOfSecond.millis
          (end - start).millis
        } else {
          (businessTime + firstHour + lastHour).millis
        }
    }
  }
}
