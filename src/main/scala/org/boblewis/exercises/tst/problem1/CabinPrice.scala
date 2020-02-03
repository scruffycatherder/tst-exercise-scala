package org.boblewis.exercises.tst.problem1

import scala.collection.mutable

/**
  * A Price for a specific cabin and rate code.
  *
  * @param cabinCode the cabin code this price applies to.
  * @param rateCode the rate code used to obtain this price.
  * @param price the price.
  */
case class CabinPrice(cabinCode: String, rateCode: String, price: BigDecimal) {

  /**
    * Validates the contents of this CabinPrice.
    * @throws NullPointerException if a null value is found.
    */
  def validate(): Unit = {
    if (cabinCode == null || rateCode == null || price == null) {
      throw new NullPointerException("CabinPrice contained a null value.")
    }
  }
}


object CabinPrice {

  /**
    * Validate all CabinPrices.
    *
    * @param cabinPrices the CabinPrices to validate.
    * @throws NullPointerException if a null value is encountered.
    * @throws IllegalArgumentException if invalid data is encountered.
    */
  def validateCabinPrices(cabinPrices: Seq[CabinPrice] ): Unit = {
    val cabinRatePairs = new mutable.HashSet[(String, String)]()

    for (cabinPrice <- cabinPrices) {
      cabinPrice.validate()
      val cabinRatePair = (cabinPrice.cabinCode, cabinPrice.rateCode)
      if (cabinRatePairs.contains(cabinRatePair)) {
        throw new IllegalArgumentException(s"Multiple prices encountered with the same Cabin Code " +
          "(${cabinPrice.cabinCode}) and Rate Code (${cabinPrice.rateCode}).")
      }
      cabinRatePairs.add(cabinRatePair)
    }
  }
}