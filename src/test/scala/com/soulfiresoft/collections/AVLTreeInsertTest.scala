package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeInsertTest extends FlatSpec {

  it should "return the size of the tree" in {
    val tree = new AVLTree[Int]()
    tree.insert(1)
    tree.insert(2)
    tree.insert(3)
    tree.insert(4)
    tree.insert(5)
    assert(tree.size == 5)
  }

  it should "correctly balance the left left case" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(2)
    tree.insert(1)
    assert(tree.root.key == 2)
  }

  it should "correctly balance the left right case" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(1)
    tree.insert(2)
    assert(tree.root.key == 2)
  }

  it should "correctly balance the right right case" in {
    val tree = new AVLTree[Int]()
    tree.insert(1)
    tree.insert(2)
    tree.insert(3)
    assert(tree.root.key == 2)
  }

  it should "correctly balance the right left case" in {
    val tree = new AVLTree[Int]()
    tree.insert(1)
    tree.insert(3)
    tree.insert(2)
    assert(tree.root.key == 2)
  }

}
