package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeContainsTest extends FlatSpec {

  it should "return false if the tree is empty" in {
    val tree = new AVLTree[Int]()
    assert(!tree.contains(1))
  }

  it should "return whether the tree contains a node" in {
    val tree = new AVLTree[Int]()
    assert(!tree.contains(1))
    assert(!tree.contains(2))
    assert(!tree.contains(3))
    tree.insert(3)
    tree.insert(1)
    tree.insert(2)
    assert(tree.contains(1))
    assert(tree.contains(2))
    assert(tree.contains(3))
  }

  it should "return false when the expected parent has no children" in {
    val tree = new AVLTree[Int]()
    tree.insert(2)
    assert(!tree.contains(1))
    assert(!tree.contains(3))
  }

}
