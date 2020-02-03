package org.boblewis.exercises.tst.problem2

/**
  * A combination of Promotions.
  *
  * @param promotionCodes the set of promo codes in this combination.
  */
case class PromotionCombo(promotionCodes: Seq[String]) {

  /** Determine if this PromotionCombo is combinable given the notCombinableWith rules of each Promotion.
    *
    * @param allPromotions all of the known Promotions (used to look up combinability rules).
    * @return
    */
  def isCombinable(allPromotions: Seq[Promotion]): Boolean = {
    this.getAsPromotions(allPromotions).forall(_.isCombinable(promotionCodes))
  }

  /**
    * Get the Seq of Promotions in this PromotionCombo.
    *
    * @param allPromotions the master list of all known Promotions.
    * @return the Seq of Promotions in this PromotionCombo.
    */
  def getAsPromotions(allPromotions: Seq[Promotion]): Seq[Promotion] = {
    promotionCodes.map(Promotion.getPromotionByCode(_, allPromotions))
  }

  /**
    * Determine if this promotions in this PromotionCombo are a strict subset of the Promotions in another
    * PromotionCombo.
    *
    * @param otherCombo the other PromotionCombo to compare against.
    * @return True if this PromotionCombo is a strict subset of otherCombo.
    */
  def strictSubsetOf(otherCombo: PromotionCombo): Boolean = {
    promotionCodes.toSet.subsetOf(
      otherCombo.promotionCodes.toSet) &&
      (promotionCodes.length < otherCombo.promotionCodes.length)
  }

  /**
    * Determine if this PromotionCombo is a strict subset of any PromotionCombo in a given Seq of PromotionCombos.
    *
    * @param allCombos the list of PromotionCombos to compare against.
    * @return True if this PromotionCombo is a strict subset of any PromotionCombo in allCombos.
    */
  def strictSubsetOfAnyPromotionCombo(allCombos: Seq[PromotionCombo] ): Boolean = {
    allCombos.map(this.strictSubsetOf).foldLeft(false)(_ || _)
  }
}

object PromotionCombo {

  /** Convert a Set of Promotions into a PromotionCombo. */
  def toPromotionCombo(promotions: Set[Promotion]) : PromotionCombo = {
    PromotionCombo(promotions.map(_.code).toSeq.sorted)
  }

  /**
    * Generate all legal PromotionCombos given a set of Promotions, respecting their notCombinableWith rules.
    *
    * @param allPromotions all of the Promotions.
    * @return a Seq[PromotionCombo] containing all legal combinations of promotions with size >= 2.
    * @throws IllegalArgumentException if the input contains any references to promotion codes that are not mapped in
    *                                  allPromotions.
    */
  def combinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

    // Validate the input
    Promotion.validateAllPromotions(allPromotions)

    val allCombos = allPossiblePromotionCombos(allPromotions)           // all possible combinations
      .filter(_.isCombinable(allPromotions))                            // that are legally combinable

    allCombos.filterNot(_.strictSubsetOfAnyPromotionCombo(allCombos))   // removing any redundant smaller subsets
  }

  /**
    * Generate all legal PromotionCombos (respecting their notCombinableWith rules) given a set of Promotions and a
    * specific Promotion that should be included in every combination.
    *
    * @param promotionCode: the code identifying the Promotion that should be included in all combos.
    * @param allPromotions all of the Promotions.
    * @return a Seq[PromotionCombo] containing all legal combinations of promotions with size >= 2.
    * @throws IllegalArgumentException if the input contains any references to promotion codes that are not mapped in
    *                                  allPromotions.
    */
  def combinablePromotions(promotionCode: String,
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
    // Validate the input.
    Promotion.validateAllPromotions(allPromotions)
    Promotion.validatePromotionCodes(Seq(promotionCode), allPromotions)

    val allCombos = allPossiblePromotionCombos(allPromotions)          // all possible combinations
      .filter(_.promotionCodes.contains(promotionCode))                // containing the provided promo Code
      .filter(_.isCombinable(allPromotions))                           // that are legally combinable

    allCombos.filterNot(_.strictSubsetOfAnyPromotionCombo(allCombos))  // removing any redundant smaller subsets
  }

  /**
    * Generate all possible combinations of promotions (regardless of notCombinableWith) with a size
    * of 2 or more.
    *
    * @param allPromotions all of the Promotions.
    * @return a Seq[PromotionCombos] containing all possible combinations of promotions with size >= 2.
    */
  def allPossiblePromotionCombos(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
    allPromotions.toSet[Promotion]
      .subsets().toSet // generate all possible subsets of the set of promo codes
      .filter(_.size > 1).toSeq // Combinations of size less than 2 are not actually combinations
      .map(this.toPromotionCombo) // convert each subset into a PromotionCombo object.
  }

}
