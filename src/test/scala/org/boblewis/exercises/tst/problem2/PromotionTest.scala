package org.boblewis.exercises.tst.problem2

class PromotionTest extends org.scalatest.FunSuite {

  test ("Simple combinable") {
    val promo = Promotion("P1", Seq("P3"))
    assert(promo.isCombinable(Seq("P4", "P5")))
  }

  test ("Simple not combinable") {
    val promo = Promotion("P1", Seq("P3", "P4"))
    assert(!promo.isCombinable(Seq("P4", "P5")))
  }

  test ("Empty notCombinableWith is always combinable") {
    val promo = Promotion("P1", Seq())
    assert(promo.isCombinable(Seq("P4", "P5")))
  }

  test ("Empty notCombinableWith and empty input is combinable") {
    val promo = Promotion("P1", Seq())
    assert(promo.isCombinable(Seq()))
  }

  test ("Empty input is combinable") {
    val promo = Promotion("P1", Seq("P2"))
    assert(promo.isCombinable(Seq()))
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
