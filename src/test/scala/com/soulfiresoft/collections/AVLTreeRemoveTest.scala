package com.soulfiresoft.collections

import org.scalatest.FlatSpec

class AVLTreeRemoveTest extends FlatSpec {

  it should "not change the size of a tree with no root" in {
    val tree = new AVLTree[Int]()
    tree.remove(1)
    assert(tree.size == 0)
  }

  it should "remove a single key" in {
    val tree = new AVLTree[Int]()
    assert(tree.isEmpty)
    tree.insert(1)
    assert(!tree.isEmpty)
    tree.remove(1)
    assert(tree.isEmpty)
  }

  /** {{{
    *       _4_                       _2_
    *      /   \                     /   \
    *     2     6  -> remove(6) ->  1     4
    *    / \                             /
    *   1   3                           3
    * }}}
    */
  it should "correctly balance the left left case" in {
    val tree = new AVLTree[Int]()
    tree.insert(4)
    tree.insert(2)
    tree.insert(6)
    tree.insert(3)
    tree.insert(5)
    tree.insert(1)
    tree.insert(7)
    tree.remove(7)
    tree.remove(5)
    tree.remove(6)
    assert(tree.root.key == 2)
    assert(tree.root.left.key == 1)
    assert(tree.root.right.key == 4)
    assert(tree.root.right.left.key == 3)
  }

  /** {{{
    *       _4_                       _6_
    *      /   \                     /   \
    *     2     6  -> remove(2) ->  4     7
    *          / \                   \
    *         5   7                  5
    * }}}
    */
  it should "correctly balance the right right case" in {
    val tree = new AVLTree[Int]()
    tree.insert(4)
    tree.insert(2)
    tree.insert(6)
    tree.insert(3)
    tree.insert(5)
    tree.insert(1)
    tree.insert(7)
    tree.remove(1)
    tree.remove(3)
    tree.remove(2)
    assert(tree.root.key == 6)
    assert(tree.root.left.key == 4)
    assert(tree.root.left.right.key == 5)
    assert(tree.root.right.key == 7)
  }

  /** {{{
    *       _6_                       _4_
    *      /   \                     /   \
    *     2     7  -> remove(8) ->  2     6
    *    / \     \                 / \   / \
    *   1   4     8               1   3 5   7
    *      / \
    *     3   5
    * }}}
    */
  it should "correctly balance the left right case" in {
    val tree = new AVLTree[Int]()
    tree.insert(6)
    tree.insert(2)
    tree.insert(7)
    tree.insert(1)
    tree.insert(8)
    tree.insert(4)
    tree.insert(3)
    tree.insert(5)
    tree.remove(8)
    assert(tree.root.key == 4)
    assert(tree.root.left.key == 2)
    assert(tree.root.left.left.key == 1)
    assert(tree.root.left.right.key == 3)
    assert(tree.root.right.key == 6)
    assert(tree.root.right.left.key == 5)
    assert(tree.root.right.right.key == 7)
  }

  /** {{{
    *       _3_                       _5_
    *      /   \                     /   \
    *     2     7  -> remove(1) ->  3     7
    *    /     / \                 / \   / \
    *   1     5   8               2   4 6   8
    *        / \
    *       4   6
    * }}}
    */
  it should "correctly balance the right left case" in {
    val tree = new AVLTree[Int]()
    tree.insert(3)
    tree.insert(2)
    tree.insert(7)
    tree.insert(1)
    tree.insert(8)
    tree.insert(5)
    tree.insert(4)
    tree.insert(6)
    tree.remove(1)
    assert(tree.root.key == 5)
    assert(tree.root.left.key == 3)
    assert(tree.root.left.left.key == 2)
    assert(tree.root.left.right.key == 4)
    assert(tree.root.right.key == 7)
    assert(tree.root.right.left.key == 6)
    assert(tree.root.right.right.key == 8)
  }

  it should "take the right child if the left does not exist" in {
    val tree = new AVLTree[Int]()
    tree.insert(1)
    tree.insert(2)
    tree.remove(1)
    assert(tree.root.key == 2)
  }

  it should "take the left child if the right does not exist" in {
    val tree = new AVLTree[Int]()
    tree.insert(2)
    tree.insert(1)
    tree.remove(2)
    assert(tree.root.key == 1)
  }

  it should "get the right child if the node has 2 leaf children" in {
    val tree = new AVLTree[Int]()
    tree.insert(2)
    tree.insert(1)
    tree.insert(3)
    tree.remove(2)
    assert(tree.root.key == 1)
  }

  it should "get the in-order successor if the node has both children" in {
    val tree = new AVLTree[Int]()
    tree.insert(2)
    tree.insert(1)
    tree.insert(4)
    tree.insert(3)
    tree.insert(5)
    tree.remove(2)
    assert(tree.root.key == 4)
  }

}
