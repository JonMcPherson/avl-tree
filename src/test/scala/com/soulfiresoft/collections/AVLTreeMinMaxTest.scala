package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeMinMaxTest extends FlatSpec {

  it should "return the maximum key in the tree" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(5)
    tree.insert(1)
    tree.insert(4)
    tree.insert(2)
    assert(tree.max == 5)
  }

  it should "throw exception for max if the tree is empty" in {
    val tree = new AVLTree[Int]()
    assertThrows[UnsupportedOperationException](tree.max)
  }

  it should "return the minimum key in the tree" in {
    val tree = new AVLTree[Int]()
    tree.insert(5)
    tree.insert(3)
    tree.insert(1)
    tree.insert(4)
    tree.insert(2)
    assert(tree.min == 1)
  }

  it should "throw exception for min if the tree is empty" in {
    val tree = new AVLTree[Int]()
    assertThrows[UnsupportedOperationException](tree.min)
  }

  it should "return the maximum node in the tree" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(5)
    tree.insert(1)
    tree.insert(4)
    tree.insert(2)
    assert(tree.maxNode().key == 5)
  }

  it should "return null for maximum node if tree is empty" in {
    val tree = new AVLTree[Int]()
    assert(tree.maxNode == null)
  }

  it should "return the minimum node in the tree" in {
    val tree = new AVLTree[Int]()
    tree.insert(5)
    tree.insert(3)
    tree.insert(1)
    tree.insert(4)
    tree.insert(2)
    assert(tree.minNode().key == 1)
  }

  it should "return null for minimum nodr if tree is empty" in {
    val tree = new AVLTree[Int]()
    assert(tree.minNode == null)
  }

}
