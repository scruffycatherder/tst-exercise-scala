package org.boblewis.exercises.tst.problem2


class Main {
}

object Main extends App {

  val allPromotions = Seq(
    Promotion("P1", Seq("P3")), // P1 is not combinable with P3
    Promotion("P2", Seq("P4", "P5")), // P2 is not combinable with P4 and P5
    Promotion("P3", Seq("P1")), // P3 is not combinable with P1
    Promotion("P4", Seq("P2")), // P4 is not combinable with P2
    Promotion("P5", Seq("P2")) // P5 is not combinable with P2
  )

  println("All possible combinations")
  PromotionCombo.combinablePromotions(allPromotions).foreach(println)
  println("-------------------")
  println("Combinations for P1")
  PromotionCombo.combinablePromotions("P1", allPromotions).foreach(println)
  println("-------------------")
  println("Combinations for P2")
  PromotionCombo.combinablePromotions("P2", allPromotions).foreach(println)
  println("-------------------")
  println("Combinations for P3")
  PromotionCombo.combinablePromotions("P3", allPromotions).foreach(println)
  println("-------------------")
  println("Combinations for P4")
  PromotionCombo.combinablePromotions("P4", allPromotions).foreach(println)
  println("-------------------")
  println("Combinations for P5")
  PromotionCombo.combinablePromotions("P5", allPromotions).foreach(println)



}
