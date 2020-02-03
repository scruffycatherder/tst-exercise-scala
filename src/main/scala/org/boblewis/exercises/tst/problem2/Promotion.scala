package org.boblewis.exercises.tst.problem2

import scala.collection.mutable.HashSet

/**
  * A Promotion that may be applied to various offers.
  *
  * @param code a unique identifier for this Promotion.
  * @param notCombinableWith a list of other Promotion codes that this Promotion can not be combined with.
  */
case class Promotion(code: String, notCombinableWith: Seq[String]) {

  /**
    * Determine if this promotion is Combinable with a given set of promotion codes.
    * @param codes the promotion codes to validate against.
    * @return True if this Promotion is combinable with the given promo codes.
    */
  def isCombinable(codes: Seq[String]): Boolean = {
    !codes.map(notCombinableWith.contains).foldLeft(false)(_ || _)
  }
}

object Promotion {

  /**
    * Look up a Promotion given it's code from the list of all known Promotions.
    *
    * If there is no Promotion in allPromotions for the given code, this will throw
    * an IllegalArgumentException to indicate that the system was initialized with
    * incorrect or incomplete data.
    *
    * @param code the code identifying the promotion to look up.
    * @param allPromotions all known Promotions.
    * @return the Promotion object identified by the provided code.
    * @throws IllegalArgumentException if there is no entry for the given code in allPromotions.
    */
  def getPromotionByCode(code: String, allPromotions: Seq[Promotion]): Promotion = {
    allPromotions.find(_.code == code) match {
      case Some(p) => p
      case None => throw new IllegalArgumentException(s"Missing Promotion definition for promoCode: $code")
    }
  }

  /**
    * Validate that every Promo Code in a given list has an entry in the master list of all promotions.
    *
    * @param codes the list of promo codes to validate.
    * @param allPromotions the master list of all promotions.
    * @throws IllegalArgumentException if any promo code referenced does not have a Promotion in allPromotions.
    */
  def validatePromotionCodes(codes: Seq[String], allPromotions: Seq[Promotion]): Unit = {
    codes.foreach(Promotion.getPromotionByCode(_, allPromotions))
  }

  /**
    * Validate that all promotion codes referenced in allPromotions exist in allPromotions, and no promotion codes
    * are used by multiple promotions.
    * @param allPromotions the list of promotions to validate
    * @throws IllegalArgumentException if there is any promotion code referenced in Promotion.notCombinableWith that
    *                                  does not exist in allPromotions.
    */
  def validateAllPromotions(allPromotions: Seq[Promotion]): Unit = {
    val codes = new HashSet[String]
    // ensure that no promotion has multiple entries.
    allPromotions.foreach(promotion => {
      if (codes.contains(promotion.code)) {
        throw new IllegalArgumentException(s"Multiple promotions defined with the same promotion code ${promotion.code}")
      } else {
        codes.add(promotion.code)
      }
    })
    codes.clear()

    // Ensure that all promotion code references relate to an existing Promotion in allPromotions.
    codes.addAll(allPromotions.flatMap(_.notCombinableWith))
    validatePromotionCodes(codes.toSeq, allPromotions)

  }
}
