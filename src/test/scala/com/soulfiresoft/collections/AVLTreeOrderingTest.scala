package com.soulfiresoft.collections

import org.scalatest.FlatSpec

import scala.util.Random

class AVLTreeOrderingTest extends FlatSpec {

  it should "function correctly given a non-reverse ordering" in {
    val tree = new AVLTree[Int]()(Ordering[Int].on((x: Int) => -x))
    tree.insert(2)
    tree.insert(1)
    tree.insert(3)
    assert(tree.size == 3)
    assert(tree.min == 3)
    assert(tree.max == 1)
    tree.remove(3)
    assert(tree.size == 2)
    assert(tree.root.key == 2)
    assert(tree.root.left == null)
    assert(tree.root.right.key == 1)
  }

  it should "support custom keys" in {
    val ordering: Ordering[SomeType] = Ordering.by(_.value)
    val tree = new AVLTree[SomeType]()(ordering)
    val keys = Random.shuffle(0 to 9 map (i => SomeType(i)))

    keys.foreach(tree.insert)

    assert(tree.keySet.toSeq.map(_.value).equals(keys.sorted(ordering).map(_.value)))
  }

  private case class SomeType(value: Int)

}
