package org.boblewis.exercises.tst.problem2

import scala.collection.mutable.HashSet

class PromotionTest extends org.scalatest.FunSuite {

  test ("Simple valid") {
    val promo = Promotion("P1", Seq("P3"))
    assert(promo.isCombinable(Seq("P4", "P5")) == true)
  }

  test ("Simple not valid") {
    val promo = Promotion("P1", Seq("P3", "P4"))
    assert(promo.isCombinable(Seq("P4", "P5")) == false)
  }

  test ("Empty notCombinableWith is always valid") {
    val promo = Promotion("P1", Seq())
    assert(promo.isCombinable(Seq("P4", "P5")) == true)
  }

  test ("Empty notCombinableWith and empty input is valid") {
    val promo = Promotion("P1", Seq())
    assert(promo.isCombinable(Seq()) == true)
  }

  test ("Empty input is valid") {
    val promo = Promotion("P1", Seq("P2"))
    assert(promo.isCombinable(Seq()) == true)
  }

  test ("GetPromotionByCode found") {
    val promotions =  Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq()))

    assert(Promotion.getPromotionByCode("P1", promotions) == Promotion("P1", Seq()))
  }

  test ("GetPromotionByCode not found throws exception") {
    val promotions =  Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq()))

    try {
      Promotion.getPromotionByCode("P3", promotions)
      fail()
    } catch {
      case _: IllegalArgumentException =>  // Expected
    }
  }
}
