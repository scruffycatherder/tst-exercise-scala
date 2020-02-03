package org.boblewis.exercises.tst.problem1

import scala.collection.mutable.HashSet

case class Rate(rateCode: String, rateGroup: String) {

  /**
    * validates the contents of this Rate.
    * @throws NullPointerException if a null value is found.
    */
  def validate(): Unit = {
    if (rateCode == null || rateGroup == null) {
      throw new NullPointerException("Rate code or group had a null value")
    }
  }
}

object Rate {

  /**
    * Validate all Rates.
    *
    * @param rates the Rates to validate.
    * @throws NullPointerException if a null value is encountered.
    * @throws IllegalArgumentException if invalid data is encountered.
    */
  def validateRates(rates: Seq[Rate] ): Unit = {
    val rateCodes = new HashSet[String]()

    for (rate <- rates) {
      rate.validate()
      if (rateCodes.contains(rate.rateCode)) {
        throw new IllegalArgumentException(s"Rate code $rate.rateCode encountered more than once.")
      }
      rateCodes.add(rate.rateCode)
    }
  }
}
