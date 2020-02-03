package org.boblewis.exercises.tst.problem1

import scala.collection.mutable.HashMap

/**
  * The best possible price for a given Cabin and Rate Group.
  *
  * @param cabinCode The code identifying the Cabin of service for this price.
  * @param rateCode The rate code identifying the rate used for this price.
  * @param price The price.
  * @param rateGroup The rate group required for this price.
  */
case class BestGroupPrice(cabinCode: String, rateCode: String, price: BigDecimal, rateGroup: String) {

}

object BestGroupPrice {
  /** Alternative constructor from a CabinPrice rather than individual fields. */
  def apply(cabinPrice: CabinPrice, rateGroup: String): BestGroupPrice = {
    BestGroupPrice(cabinPrice.cabinCode, cabinPrice.rateCode, cabinPrice.price, rateGroup)
  }

  /** Comparison to sort BestGroupPrices by Cabin code and Rate Group. */
  def sortByCabinAndRateGroup(bgp1: BestGroupPrice, bgp2: BestGroupPrice) = {
    bgp1.cabinCode < bgp2.cabinCode || (bgp1.cabinCode == bgp2.cabinCode && bgp1.rateGroup < bgp2.rateGroup)
  }

  /**
    * Given a set of Rates and CabinPrices, find the best prices for each cabin/rate group combination.
    *
    * @param rates  All of the Rates available.
    * @param prices All of the CabinPrices available
    * @return a Seq[BestGroupPrice]
    */
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
    // Validate input
    Rate.validateRates(rates)
    CabinPrice.validateCabinPrices(prices)

    // Build a lookup table to find rate groups by rate code
    val rateCodesToGroupMap: Map[String, String] = mapRateCodesToGroups(rates)

    // Map to store the best prices by cabin and rate group.
    // key is (CabinCode, RateGroup)
    val bgpMap: HashMap[(String, String), BestGroupPrice] = HashMap()

    // Create a closure to easily apply the update function to each price.
    val updateClosure = (cp: CabinPrice) => maybeUpdateBestGroupPrice(rateCodesToGroupMap, bgpMap, cp)
    prices.foreach(updateClosure)

    // return the best prices as a list.
    // NOTE: The problem description did not specify a sort order, and the sample input/output could be interpreted
    // to indicate sorting by Price, CabinCode, or Cabin Code && Rate Group, input order, or no sorting applied at all.
    // I elected to sort by Cabin Code and Rate Group, as it seemed to be the most useful for this data set.
    bgpMap.values.toSeq.sortWith(BestGroupPrice.sortByCabinAndRateGroup)
  }

  /**
    * Update a Map of BestGroupPrices given a new CabinPrice.
    *
    * This will add the CabinPrice as a BestGroupPrice to bgpMap if
    * a) there is no existing entry in the map for the price's Cabin Code and Rate Group.
    * OR b) there is an existing entry, but the new CabinPrice is lower.
    *
    * @param rateCodesToGroupMap the map of all rate codes to their rate groups.
    * @param bgpMap              the current Map from (CabinCode, RateGroup) to BestGroupPrice.
    * @param cabinPrice          a new CabinPrice to potentially add to the map of BestGroupPrices.
    * @return Unit
    */
  private def maybeUpdateBestGroupPrice(rateCodesToGroupMap: Map[String, String],
                                        bgpMap: HashMap[(String, String), BestGroupPrice],
                                        cabinPrice: CabinPrice): Unit = {
    val rateGroup = getRateGroup(rateCodesToGroupMap, cabinPrice.rateCode)

    bgpMap.get((cabinPrice.cabinCode, rateGroup)) match {
      case Some(prevBgp) =>
        if (cabinPrice.price <= prevBgp.price) {
          bgpMap.put(
            (cabinPrice.cabinCode, rateGroup),
            BestGroupPrice(cabinPrice, rateGroup))
        }
      case None => bgpMap.put(
        (cabinPrice.cabinCode, rateGroup),
        BestGroupPrice(cabinPrice, rateGroup))
    }
  }

  /**
    * Create a map from Rate Code to Rate Group from a Seq of Rates.
    *
    * @param rates the Rates to be mapped.
    * @return a Map from RateCode to RateGroup.
    */
  private def mapRateCodesToGroups(rates: Seq[Rate]): Map[String, String] = {
    rates.map(rate => rate.rateCode -> rate.rateGroup).toMap
  }

  /**
    * Convenience wrapper to look up Rate Groups by Rate Code, or throw an IllegalArgumentException
    * if the requested RateCode does not exist as a key in the map.
    *
    * @param rateCodesToGroupMap The map from rate code to rate group.
    * @param rateCode            the rate code to look up.
    * @return the rate group associated with the given rate code.
    */
  private def getRateGroup(rateCodesToGroupMap: Map[String, String], rateCode: String): String = {
    rateCodesToGroupMap.get(rateCode) match {
      case Some(rateGroup) => rateGroup
      case None => throw new IllegalArgumentException(s"No known rateGroup for rateCode $rateCode")
    }
  }
}
