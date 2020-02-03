package org.boblewis.exercises.tst.problem1

object Main extends App {

  val rates = Seq(
  Rate("M1", "Military"),
  Rate("M2", "Military"),
  Rate("S1", "Senior"),
  Rate("S2", "Senior"))


  val prices = Seq(
  CabinPrice("CA", "M1", 200.00),
  CabinPrice("CA", "M2", 250.00),
  CabinPrice("CA", "S1", 225.00),
  CabinPrice("CA", "S2", 260.00),
  CabinPrice("CB", "M1", 230.00),
  CabinPrice("CB", "M2", 260.00),
  CabinPrice("CB", "S1", 245.00),
  CabinPrice("CB", "S2", 270.00))

  BestGroupPrice.getBestGroupPrices(rates, prices).foreach(println)
}
