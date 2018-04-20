object Test extends App {
  def powerSet(set: Set[Int]): Set[Set[Int]] = {

    set.toList match {
      case Nil => Set(Set.empty[Int])
      case x :: xs => powerSet(xs.toSet).map(xsi => xsi + x) ++ powerSet(xs.toSet)
    }
  }

  println(powerSet(Set(1,2,3)))
}
