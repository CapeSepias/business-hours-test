package com.cupenya.modules.business.hour.calculation

import com.cupenya.modules.business.hour.SlaSchedule
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.runner._
import org.openjdk.jmh.runner.options._

class BusinessHourCalculationBenchmark {
  import BusinessHourCalculationBenchmarkMethods._

  @Benchmark
  def defaultImplementation() = {
    defaultImplementation0()
  }

  @Benchmark
  def anotherImplementation() = {
    //defaultImplementation0()
  }

}



// extracted because of JMH not getting traits in benachmarks
object BusinessHourCalculationBenchmarkMethods extends BusinessHourCalculation {
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

  def defaultImplementation0() = {
    val start = new  DateTime(2016, 4, 1, 17, 50, 0, 0, DateTimeZone.UTC)
    val end = new  DateTime(2016, 4, 22, 19, 30, 0, 0, DateTimeZone.UTC)
    val businessTime = calculateBusinessElapsedTime(start.millis, end.millis, BusinessHoursTest)
  }
}

