package com.cupenya.modules.business.hour


import com.cupenya.modules.business.hour.SlaSchedule.BusinessHours
import com.cupenya.modules.business.hour.calculation.BusinessHourCalculation
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import org.specs2.time.NoTimeConversions


class BusinessHourCalculationTest extends Specification with BusinessHourCalculation with NoTimeConversions {
  "BusinessHourCalculation calculate Business Elapsed Time" should {
    "start date excluded, end date included" in new DefaultContext {
      val start = new  DateTime(2016, 4, 22, 7, 55, 0, 0, DateTimeZone.UTC)
      val end = new  DateTime(2016, 4, 22, 8, 5, 0, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.millis, end.millis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual 5.minutes.millis
    }

    "start date included, end date excluded" in new DefaultContext {
      val start = new  DateTime(2016, 4, 22, 17, 50, 0, 0, DateTimeZone.UTC)
      val end = new  DateTime(2016, 4, 22, 19, 30, 0, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.millis, end.millis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual 10.minutes.millis
    }

    "start and end date are included" in new DefaultContext {
      val start = new  DateTime(2016, 4, 22, 10, 30, 0, 0, DateTimeZone.UTC)
      val end = new  DateTime(2016, 4, 22, 11, 30, 0, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.millis, end.millis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual 1.hour.millis
    }

    "start and end date are excluded" in new DefaultContext {
      val start = new  DateTime(2016, 4, 22, 19, 30, 0, 0, DateTimeZone.UTC)
      val end = new  DateTime(2016, 4, 22, 20, 0, 0, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.millis, end.millis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual 0.second.millis
    }

    "exclude non business hours considering 24h" in new DefaultContext {
      val start = new DateTime(2016, 4, 20, 17, 50, 10, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 4, 21, 17, 50, 10, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual 10.hours.millis
    }

    "consider minutes and seconds" in new DefaultContext {
      val start = new DateTime(2016, 4, 20, 17, 50, 10, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 4, 21, 16, 45, 5, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual (8.hours + 54.minutes + 55.seconds).millis
    }

    "exclude weekends" in new DefaultContext {
      val start = new DateTime(2016, 4, 22, 17, 50, 0, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 4, 25, 8, 5, 5, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual (15.minutes + 5.seconds).millis
    }

    "exclude national holidays" in new DefaultContext {
      val start = new DateTime(2016, 12, 23, 17, 50, 0, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 12, 27, 8, 5, 5, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHoursTest)
      businessTime.toDuration.getMillis mustEqual (15.minutes + 5.seconds).millis
    }

    "deals with timezones" in {
      val start = new DateTime(2016, 4, 22, 5, 30, 0, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 4, 22, 6, 5, 5, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHours)
      businessTime.toDuration.getMillis mustEqual (5.minutes + 5.seconds).millis
    }

    "start and end at the same hour" in {
      val start = new DateTime(2016, 2, 18, 10, 36, 0, 0, DateTimeZone.UTC)
      val end = new DateTime(2016, 2, 18, 10, 47, 5, 0, DateTimeZone.UTC)
      val businessTime = calculateBusinessElapsedTime(start.getMillis, end.getMillis, BusinessHours)
      businessTime.toDuration.getMillis mustEqual (11.minutes + 5.seconds).millis
    }
  }

  trait DefaultContext extends Scope {
    val BusinessHoursTest = new SlaSchedule {
      override def excludedWeekdays: List[Int] = List(6, 7)

      override def timezone: DateTimeZone = DateTimeZone.UTC

      override def excludeBankHolidays: Boolean = false

      override def excludedHours: List[Int] = List(0, 1, 2, 3, 4, 5, 6, 7, 18, 19, 20, 21, 22, 23)

      override def excludedDates: List[LocalDate] = List(
        new LocalDate(2016, 1, 1),
        new LocalDate(2016, 3, 28),
        new LocalDate(2016, 4, 27),
        new LocalDate(2016, 5, 5),
        new LocalDate(2016, 5, 16),
        new LocalDate(2016, 12, 25),
        new LocalDate(2016, 12, 26)
      )
    }
  }
}
