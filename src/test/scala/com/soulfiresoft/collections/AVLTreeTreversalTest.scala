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

    for (i <- 1 to 9) assert(tree.prev(tree.findNode(i).get).exists(tree.findNode(i - 1).contains))
  }

  it should "return none for predecessor of the min node" in {
    val tree = new AVLTree[Int]()
    for (i <- 0 to 9) tree.insert(i)

    var max = tree.maxNode()
    assert(tree.next(max).isEmpty)

    tree.remove(max.key)
    max = tree.maxNode()
    assert(tree.next(max).isEmpty)
  }

  it should "find successor for a node" in {
    val tree = new AVLTree[Int]()

    for (i <- 0 to 9) tree.insert(i)

    for (i <- 0 to 8) assert(tree.next(tree.findNode(i).get).exists(tree.findNode(i + 1).contains))
  }

  it should "return none for successor of the max node" in {
    val tree = new AVLTree[Int]()
    for (i <- 0 to 9) tree.insert(i)

    var min = tree.minNode()
    assert(tree.prev(min).isEmpty)

    tree.remove(min.key)
    min = tree.minNode()
    assert(tree.prev(min).isEmpty)
  }

  it should "find successor and predecessor for 2-node tress" in {
    val tree = new AVLTree[Int]()
    tree.insert(5)
    tree.insert(10)

    val min = tree.minNode()
    assert(min.key == 5)
    assert(tree.prev(min).isEmpty)
    assert(tree.next(min).exists(_.key == 10))

    val max = tree.maxNode()
    assert(max.key == 10)
    assert(tree.next(max).isEmpty)
    assert(tree.prev(max).exists(_.key == 5))
  }

  it should "be able to get a node by its index"

}
