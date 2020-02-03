package org.boblewis.exercises.tst.problem2


class PromotionComboTest extends org.scalatest.FunSuite {

  test("GetAsPromotions success") {
    val p1 = Promotion("P1", Seq("P3"))
    val p2 = Promotion("P2", Seq("P3"))
    val p3 = Promotion("P3", Seq("P1", "P2"))
    val promotions = Seq(p1, p2, p3)

    val combo = PromotionCombo(Seq("P1", "P3"))
    val comboPromotions = combo.getAsPromotions(promotions)
    assert(comboPromotions.length == 2)
    assert(comboPromotions.contains(p1))
    assert(comboPromotions.contains(p3))
  }

  test("GetAsPromotions with empty PromotionCombo returns empty Seq") {

    val combo = PromotionCombo(Seq())
    val promotions = Seq()
    val comboPromotions = combo.getAsPromotions(promotions)
    assert(comboPromotions.length == 0)
  }

  test("GetAsPromotions missing promotion throws exception") {
    val p1 = Promotion("P1", Seq("P3"))
    val p2 = Promotion("P2", Seq("P3"))
    val p3 = Promotion("P3", Seq("P1", "P2"))
    val promotions = Seq(p1, p2, p3)

    val combo = PromotionCombo(Seq("P1", "P3", "P4"))
    try {
      val comboPromotions = combo.getAsPromotions(promotions)
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test("GetAllPromotionCombos empty") {
    val promotions = Seq()
    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    assert(combos.length == 0)
  }

  test("GetAllPromotionCombos single promo") {
    val p1 = Promotion("P1", Seq("P3"))
    val promotions = Seq(p1)
    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    // one promotion is not enough for a combination
    assert(combos.length == 0)
  }

  test("GetAllPromotionCombos two promos") {
    val p1 = Promotion("P1", Seq("P3"))
    val p2 = Promotion("P2", Seq())
    val promotions = Seq(p1, p2)
    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    assert(combos.length == 1)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code))))
  }

  test("GetAllPromotionCombos 3 promos") {
    val p1 = Promotion("P1", Seq("P3"))
    val p2 = Promotion("P2", Seq("P3"))
    val p3 = Promotion("P3", Seq("P1", "P2"))

    val promotions = Seq(p1, p2, p3)

    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    assert(combos.length == 4)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code))))
    assert(combos.contains(PromotionCombo(Seq(p2.code, p3.code))))
    assert(combos.contains(PromotionCombo(Seq(p1.code, p3.code))))
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code, p3.code))))
  }

  test("allCombinablePromotionCombos empty") {
    val promotions = Seq()
    val combos: Seq[PromotionCombo] = PromotionCombo.combinablePromotions(promotions)
    assert(combos.length == 0)
  }


  test("allCombinablePromotionCombos single promo") {
    val p1 = Promotion("P1", Seq())
    val promotions = Seq(p1)
    val combos: Seq[PromotionCombo] = PromotionCombo.combinablePromotions(promotions)
    assert(combos.length == 0)
  }

  test("allCombinablePromotionCombos two combinable promos") {
    val p1 = Promotion("P1", Seq("P3"))
    val p2 = Promotion("P2", Seq())
    val promotions = Seq(p1, p2)
    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    assert(combos.length == 1)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code))))
  }

  test("allCombinablePromotionCombos two non-combinable promos") {
    val p1 = Promotion("P1", Seq("P2"))
    val p2 = Promotion("P2", Seq("P1"))
    val promotions = Seq(p1, p2)
    val combos: Seq[PromotionCombo] = PromotionCombo.combinablePromotions(promotions)
    assert(combos.length == 0)
  }

  test("allCombinablePromotionCombos 3 promos, all combinable") {
    val p1 = Promotion("P1", Seq())
    val p2 = Promotion("P2", Seq())
    val p3 = Promotion("P3", Seq())

    val promotions = Seq(p1, p2, p3)

    val combos: Seq[PromotionCombo] = PromotionCombo.allPossiblePromotionCombos(promotions)
    assert(combos.length == 4)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code))))
    assert(combos.contains(PromotionCombo(Seq(p2.code, p3.code))))
    assert(combos.contains(PromotionCombo(Seq(p1.code, p3.code))))
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code, p3.code))))
  }

  test("allCombinablePromotionCombos 3 promos, two combinable") {
    val p1 = Promotion("P1", Seq("P2"))
    val p2 = Promotion("P2", Seq("P1", "P3"))
    val p3 = Promotion("P3", Seq("P2"))

    val promotions = Seq(p1, p2, p3)

    val combos: Seq[PromotionCombo] = PromotionCombo.combinablePromotions(promotions)
    assert(combos.length == 1)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p3.code))))
  }

  test("allCombinablePromotionCombos 4 promos, all combinable") {
    val p1 = Promotion("P1", Seq())
    val p2 = Promotion("P2", Seq())
    val p3 = Promotion("P3", Seq())

    val promotions = Seq(p1, p2, p3)

    val combos: Seq[PromotionCombo] = PromotionCombo.combinablePromotions(promotions)
    assert(combos.length == 1)
    assert(combos.contains(PromotionCombo(Seq(p1.code, p2.code, p3.code))))
  }


  test("strictSubsetOfAnyPromotionCombo simple") {
    val combo1 = PromotionCombo(Seq("P1", "P2", "P3"))
    val combo2 = PromotionCombo(Seq("P1", "P2"))
    val combo3 = PromotionCombo(Seq("P5", "P2", "P3"))

    val allCombos = Seq(combo1, combo2, combo3)
    val s1 = Set(4, 12)
    val s2 = Set(12, 4)
    assert(s1.subsetOf(s2) == true)
    assert(combo1.strictSubsetOfAnyPromotionCombo(allCombos) == false)
    assert(combo2.strictSubsetOfAnyPromotionCombo(allCombos) == true)
    assert(combo3.strictSubsetOfAnyPromotionCombo(allCombos) == false)

  }

  test("Sample inputs, all combinations") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions(allPromotions)
    assert(combos.length == 4)
    assert(combos.contains(PromotionCombo(Seq("P1", "P2"))))
    assert(combos.contains(PromotionCombo(Seq("P1", "P4", "P5"))))
    assert(combos.contains(PromotionCombo(Seq("P2", "P3"))))
    assert(combos.contains(PromotionCombo(Seq("P3", "P4", "P5"))))
  }

  test("Sample inputs, combinations for P1") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions("P1", allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P1", "P2"))))
    assert(combos.contains(PromotionCombo(Seq("P1", "P4", "P5"))))
  }

  test("Sample inputs, combinations for P2") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions("P2", allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P1", "P2"))))
    assert(combos.contains(PromotionCombo(Seq("P2", "P3"))))
  }

  test("Sample inputs, combinations for P3") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions("P3", allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P3", "P4", "P5"))))
    assert(combos.contains(PromotionCombo(Seq("P2", "P3"))))
  }

  test("Sample inputs, combinations for P4") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions("P4", allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P1", "P4", "P5"))))
    assert(combos.contains(PromotionCombo(Seq("P3", "P4", "P5"))))
  }

  test("Sample inputs, combinations for P5") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P3")), // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")), // P3 is not combinable with P1
      Promotion("P4", Seq("P2")), // P4 is not combinable with P2
      Promotion("P5", Seq("P2")) // P5 is not combinable with P2
    )

    val combos = PromotionCombo.combinablePromotions("P5", allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P1", "P4", "P5"))))
    assert(combos.contains(PromotionCombo(Seq("P3", "P4", "P5"))))
  }

  test("allCombinablePromotions with no promotions") {
    val combos = PromotionCombo.combinablePromotions(Seq())
    assert(combos.length == 0)
  }

  test("PromotionCode empty string is OK") {
    val allPromotions = Seq(
      Promotion("", Seq("P3")),
      Promotion("P2", Seq()),
      Promotion("P3", Seq(""))
    )
    val combos = PromotionCombo.combinablePromotions(allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("", "P2"))))
    assert(combos.contains(PromotionCombo(Seq("P2", "P3"))))
  }

  test("PromotionCode null is not OK") {
    val allPromotions = Seq(
      Promotion(null, Seq("P3")),
      Promotion("P2", Seq()),
      Promotion("P3", Seq(null))
    )
    try {
      val combos = PromotionCombo.combinablePromotions("P2", allPromotions)
      fail()
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test("allCombinablePromotions with starting promo code, but no promotions") {
    try {
      val combos = PromotionCombo.combinablePromotions("P1", Seq())
      fail()
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test("allCombinablePromotions with starting promo code that is not in allPromotions") {
    val allPromotions = Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq()),
      Promotion("P3", Seq())
    )

    try {
      val combos = PromotionCombo.combinablePromotions("P4", allPromotions)
      fail()
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test("A promotion references a non-existant promotion") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P2")),
      Promotion("P2", Seq("P3")),
      Promotion("P3", Seq("P1", "P4"))
    )

    try {
      val combos = PromotionCombo.combinablePromotions(allPromotions)
      fail()
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test ("Multiple promotions with the same code throws an exception") {
    val allPromotions = Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq()),
      Promotion("P1", Seq())
    )

    try {
      val combos = PromotionCombo.combinablePromotions(allPromotions)
      fail()
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test("non-reciprocal combinability rules still exclude combinations") {
    val allPromotions = Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq("P1"))
    )
    val combos = PromotionCombo.combinablePromotions(allPromotions)
    assert(combos.length == 0)
  }

  test("small circular combinability yields no combinations") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P2")),
      Promotion("P2", Seq("P3")),
      Promotion("P3", Seq("P1"))
    )
    val combos = PromotionCombo.combinablePromotions(allPromotions)
    assert(combos.length == 0)
  }

  test("slightly larger circular combinability yields some combinations") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P2")),
      Promotion("P2", Seq("P3")), Promotion("P3", Seq("P4")),
      Promotion("P4", Seq("P1"))
    )
    val combos = PromotionCombo.combinablePromotions(allPromotions)
    assert(combos.length == 2)
    assert(combos.contains(PromotionCombo(Seq("P1", "P3"))))
    assert(combos.contains(PromotionCombo(Seq("P2", "P4"))))
  }

  test ("Null promotion") {
    val allPromotions = Seq(
      Promotion("P1", Seq("P2")),
      null,
      Promotion("P2", Seq("P1"))
    )
    try {
      val combos = PromotionCombo.combinablePromotions("P1", allPromotions)
      fail()
    } catch {
      case _: NullPointerException => // Expected
    }
  }
}
