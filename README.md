  # TST exercises, Scala version:
  
  ## Notes:
  
  Given that this is my first introduction to Scala, this is is very much a brute force implementation.  The current implementation of problem 2 runs in exponential time, and not been optimized at all. 
  
  ## Assumptions:
  - The data structures provided in the problem description were to be used without modification.  Given a choice, I would likely have made changes that might clean the code up a bit, or possibly improve performance. 
    For example, I could have stored the reference to the full Promotion objects within PromotionCombo, so we would not have to pass allPromotions around from function to function. Or, I may have used Set instead of Seq throughout, since ordering seems unimportant.
    Alternatively, I could have stored the list of all known Promotions in a Singleton, but I felt like the provided interface lent itself more towards a stateless implementation.
  
  - Order does not matter in the output.
  - Invalid input should result in an Exception.
  - Null values in inputs will just throw a NullPointerException
  - EmptyString values are OK
  - In Problem 1, if there are two CabinPrices with different rates for the same rate group and identical prices, one will be selected (selection criteria is undefined)
  
  ## Usage:
  
  Execute Problem1 with sample input:  `sbt "runMain org.boblewis.exercises.tst.problem1.Main"`
  
  Execute Problem2 with sample input:  `sbt "runMain org.boblewis.exercises.tst.problem2.Main"`
  
  Execute unit tests: `sbt test`