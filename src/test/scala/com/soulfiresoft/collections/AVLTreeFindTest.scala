package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeFindTest extends FlatSpec {

  it should "return none when empty" in {
    val tree = new AVLTree[Int]()
    assert(tree.findNode(0).isEmpty)
  }

  it should "return root when size is one" in {
    val tree = new AVLTree[Int]()
    tree.insert(10)
    assert(tree.findNode(10).contains(tree.root))
  }

  it should "return key as the result of search" in {
    val tree = new AVLTree[Int]()
    assert(tree.findNode(1).isEmpty)
    assert(tree.findNode(2).isEmpty)
    assert(tree.findNode(3).isEmpty)
    tree.insert(1)
    tree.insert(2)
    tree.insert(3)
    assert(tree.findNode(1).isDefined)
    assert(tree.findNode(2).isDefined)
    assert(tree.findNode(3).isDefined)
    assert(tree.findNode(8).isEmpty)
  }

}
