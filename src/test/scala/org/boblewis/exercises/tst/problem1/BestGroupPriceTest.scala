package org.boblewis.exercises.tst.problem1

class BestGroupPriceTest extends org.scalatest.FunSuite {

  test ("Provided sample output should match") {
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

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length === 4)
    assert(results.contains(BestGroupPrice("CA", "M1", 200, "Military")))
    assert(results.contains(BestGroupPrice("CA", "S1", 225, "Senior")))
    assert(results.contains(BestGroupPrice("CB", "M1", 230, "Military")))
    assert(results.contains(BestGroupPrice("CB", "S1", 245, "Senior")))
  }

  test ("Rates in same cabin and same rate group") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CA", "M2", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 1)
    assert(results.contains(BestGroupPrice("CA", "M2", 100.00, "Military")))
  }

  test ("Rates in same cabin but different rate groups") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 2)
    assert(results.contains(BestGroupPrice("CA", "M1", 100.01, "Military")))
    assert(results.contains(BestGroupPrice("CA", "S2", 100.00, "Senior")))
  }

  test ("Rates in different cabin but same rate code") {
    val rates = Seq(
      Rate("M1", "Military")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CB", "M1", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 2)
    assert(results.contains(BestGroupPrice("CA", "M1", 100.01, "Military")))
    assert(results.contains(BestGroupPrice("CB", "M1", 100.00, "Military")))
  }

  test ("Rate code empty string") {
    val rates = Seq(
      Rate("", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 2)
    assert(results.contains(BestGroupPrice("CA", "", 100.01, "Military")))
    assert(results.contains(BestGroupPrice("CA", "S2", 100.00, "Senior")))
  }

  test ("Rate code Null") {
    val rates = Seq(
      Rate(null, "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("Rate group empty string") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 2)
    assert(results.contains(BestGroupPrice("CA", "M1", 100.01, "Military")))
    assert(results.contains(BestGroupPrice("CA", "S2", 100.00, "")))
  }

  test ("Rate group null") {
    val rates = Seq(
      Rate("M1", null),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("Cabin code empty string") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("", "M1", 100.01),
      CabinPrice("", "S2", 100.00)
    )

    val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    assert(results.length == 2)
    assert(results.contains(BestGroupPrice("", "M1", 100.01, "Military")))
    assert(results.contains(BestGroupPrice("", "S2", 100.00, "Senior")))

  }

  test ("Cabin code null") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice(null, "M1", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("CabinPrice.price null") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", null),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("CabinPrice.rateCode null") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", null, 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("conflicting rate codes") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M1", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "M1", 100.01),
      CabinPrice("CB", "M1", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test ("conflicting prices") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", "S2", 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: IllegalArgumentException => // Expected
    }
  }

  test ("CabinPrice null") {
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", null, 100.01),
      null,
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }

  test ("Rate null") {
    val rates = Seq(
      Rate("M1", "Military"),
      null,
      Rate("S2", "Senior")
    )

    val prices = Seq(
      CabinPrice("CA", null, 100.01),
      CabinPrice("CA", "S2", 100.00)
    )

    try {
      val results = BestGroupPrice.getBestGroupPrices(rates, prices)
    } catch {
      case _: NullPointerException => // Expected
    }
  }
}
