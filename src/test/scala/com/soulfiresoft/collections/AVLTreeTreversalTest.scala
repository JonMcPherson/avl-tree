package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeTreversalTest extends FlatSpec {

  it should "traverse the tree in order" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(1)
    tree.insert(0)
    tree.insert(2)

    var i = 0
    tree.foreach(key => {
      assert(key == i)
      i += 1
    })
  }

  it should "find predecessor for a node" in {
    val tree = new AVLTree[Int]()

    for (i <- 0 to 9) tree.insert(i)

    for (i <- 0 to 9) assert(tree.prev(tree.findNode(i)) == tree.findNode(i - 1))
  }

  it should "return null for predecessor of the min node" in {
    val tree = new AVLTree[Int]()
    for (i <- 0 to 9) tree.insert(i)

    var max = tree.maxNode()
    assert(tree.next(max) == null)

    tree.remove(max.key)
    max = tree.maxNode()
    assert(tree.next(max) == null)
  }

  it should "find successor for a node" in {
    val tree = new AVLTree[Int]()

    for (i <- 0 to 9) tree.insert(i)

    for (i <- 0 to 9) assert(tree.next(tree.findNode(i)) == tree.findNode(i + 1))
  }

  it should "return null for successor of the max node" in {
    val tree = new AVLTree[Int]()
    for (i <- 0 to 9) tree.insert(i)

    var min = tree.minNode()
    assert(tree.prev(min) == null)

    tree.remove(min.key)
    min = tree.minNode()
    assert(tree.prev(min) == null)
  }

  it should "find successor and predecessor for 2-node tress" in {
    val tree = new AVLTree[Int]()
    tree.insert(5)
    tree.insert(10)

    val min = tree.minNode()
    assert(min.key == 5)
    assert(tree.prev(min) == null)
    assert(tree.next(min).key == 10)

    val max = tree.maxNode()
    assert(max.key == 10)
    assert(tree.next(max) == null)
    assert(tree.prev(max).key == 5)
  }

  it should "be able to get a node by its index"

}
