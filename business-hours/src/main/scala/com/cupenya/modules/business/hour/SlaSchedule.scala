package com.cupenya.modules.business.hour


import org.joda.time.{DateTimeZone, LocalDate}
import spray.json._

trait SlaSchedule {
  def timezone: DateTimeZone
  def excludeBankHolidays: Boolean
  def excludedDates: List[LocalDate]
  def excludedWeekdays: List[Int]
  def excludedHours: List[Int]
}

object SlaSchedule {
  case object OfficeHours extends SlaSchedule {
    override def timezone = DateTimeZone.forID("CET")
    override def excludeBankHolidays = false
    // TODO: Introduce proper calendar
    override def excludedDates = List(
      new LocalDate(2016, 1, 1),
      new LocalDate(2016, 3, 28),
      new LocalDate(2016, 4, 27),
      new LocalDate(2016, 5, 5),
      new LocalDate(2016, 5, 16),
      new LocalDate(2016, 12, 25),
      new LocalDate(2016, 12, 26)
    )
    override def excludedWeekdays = List(6, 7)
    override def excludedHours = List(0, 1, 2, 3, 4, 5, 6, 7, 17, 18, 19, 20, 21, 22, 23)
  }

  case object BusinessHours extends SlaSchedule {
    override def timezone = DateTimeZone.forID("CET")
    override def excludeBankHolidays = false
    // TODO: Introduce proper calendar
    override def excludedDates = List(
      new LocalDate(2016, 1, 1),
      new LocalDate(2016, 3, 28),
      new LocalDate(2016, 4, 27),
      new LocalDate(2016, 5, 5),
      new LocalDate(2016, 5, 16),
      new LocalDate(2016, 12, 25),
      new LocalDate(2016, 12, 26)
    )
    override def excludedWeekdays = List(6, 7)
    override def excludedHours = List(0, 1, 2, 3, 4, 5, 6, 7, 18, 19, 20, 21, 22, 23)
  }

  case object ExtBusinessHours extends SlaSchedule {
    override def timezone = DateTimeZone.forID("CET")
    override def excludeBankHolidays = false
    // TODO: Introduce proper calendar
    override def excludedDates = List(
      new LocalDate(2016, 1, 1),
      new LocalDate(2016, 3, 28),
      new LocalDate(2016, 4, 27),
      new LocalDate(2016, 5, 5),
      new LocalDate(2016, 5, 16),
      new LocalDate(2016, 12, 25),
      new LocalDate(2016, 12, 26)
    )
    override def excludedWeekdays = List(6, 7)
    override def excludedHours = List(0, 1, 2, 3, 4, 5, 6, 7, 22, 23)
  }

  implicit object SlaScheduleFormat extends JsonFormat[SlaSchedule] {

    def write(obj: SlaSchedule): JsValue = obj match {
      case OfficeHours => JsString("officeHours")
      case BusinessHours => JsString("businessHours")
      case ExtBusinessHours => JsString("extBusinessHours")
    }

    def read(json: JsValue): SlaSchedule = json match {
      case JsString("officeHours") => OfficeHours
      case JsString("businessHours") => BusinessHours
      case JsString("extBusinessHours") => ExtBusinessHours
      case o => deserializationError(s"Unknown value ${o}")
    }
  }
}
