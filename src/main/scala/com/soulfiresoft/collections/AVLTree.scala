package com.soulfiresoft.collections

import scala.collection.mutable

class AVLTree[A](implicit val ordering: Ordering[A]) extends mutable.SortedSet[A] {

  if (ordering eq null) throw new NullPointerException("ordering must not be null")

  private var _root: AVLNode = _
  private var _size = 0

  def root: AVLNode = _root

  override def size: Int = _size


  override def foreach[U](f: (A) => U): Unit = {
    val stack = mutable.Stack[AVLNode]()
    var current = root
    var done = false

    while (!done) {
      if (current != null) {
        stack.push(current)
        current = current.left
      } else if (stack.nonEmpty) {
        current = stack.pop()
        f.apply(current.key)

        current = current.right
      } else {
        done = true
      }
    }
  }

  override def isEmpty: Boolean = root == null

  def findNode(key: A): Option[AVLNode] = {
    var node = root
    while (node != null) {
      val cmp = ordering.compare(key, node.key)
      if (cmp == 0) return Some(node)
      node = node.matchNextChild(cmp)
    }
    None
  }

  override def min[B >: A](implicit cmp: Ordering[B]): A = minNode().key

  def minNode(): AVLNode = {
    if (root == null) throw new UnsupportedOperationException("empty tree")
    var node = root
    while (node.left != null) node = node.left
    node
  }

  override def max[B >: A](implicit cmp: Ordering[B]): A = maxNode().key

  def maxNode(): AVLNode = {
    if (root == null) throw new UnsupportedOperationException("empty tree")
    var node = root
    while (node.right != null) node = node.right
    node
  }

  def next(node: AVLNode): Option[AVLNode] = {
    var successor = node
    if (successor != null) {
      if (successor.right != null) {
        successor = successor.right
        while (successor != null && successor.left != null) {
          successor = successor.left
        }
      } else {
        successor = node.parent
        var n = node
        while (successor != null && successor.right == n) {
          n = successor
          successor = successor.parent
        }
      }
    }
    Option(successor)
  }

  def prev(node: AVLNode): Option[AVLNode] = {
    var predecessor = node
    if (predecessor != null) {
      if (predecessor.left != null) {
        predecessor = predecessor.left
        while (predecessor != null && predecessor.right != null) {
          predecessor = predecessor.right
        }
      } else {
        predecessor = node.parent
        var n = node
        while (predecessor != null && predecessor.left == n) {
          n = predecessor
          predecessor = predecessor.parent
        }
      }
    }
    Option(predecessor)
  }

  def insert(key: A): AVLNode = {
    if (root == null) {
      _root = AVLNode(key)
      _size += 1
      return root
    }

    var node = root
    var parent: AVLNode = null
    var cmp = 0

    while (node != null) {
      parent = node
      cmp = ordering.compare(key, node.key)
      if (cmp == 0) return node // duplicate
      node = node.matchNextChild(cmp)
    }

    val newNode = AVLNode(key, parent, null, null)
    if (cmp <= 0) parent.left = newNode
    else parent.right = newNode

    while (parent != null) {
      cmp = ordering.compare(parent.key, key)
      if (cmp < 0) parent.balanceFactor -= 1
      else parent.balanceFactor += 1

      parent = parent.balanceFactor match {
        case -1 | 1 => parent.parent
        case x if x < -1 => {
          if (parent.right.balanceFactor == 1) rotateRight(parent.right)
          val newRoot = rotateLeft(parent)
          if (parent == root) _root = newRoot
          null
        }
        case x if x > 1 => {
          if (parent.left.balanceFactor == -1) rotateLeft(parent.left)
          val newRoot = rotateRight(parent)
          if (parent == root) _root = newRoot
          null
        }
        case _ => null
      }
    }

    _size += 1
    newNode
  }

  override def remove(key: A): Boolean = {
    var node = findNode(key).orNull
    if (node == null) return false

    if (node.left != null) {
      var max = node.left

      while (max.left != null || max.right != null) {
        while (max.right != null) max = max.right

        node._key = max.key
        if (max.left != null) {
          node = max
          max = max.left
        }
      }
      node._key = max.key
      node = max
    }

    if (node.right != null) {
      var min = node.right

      while (min.left != null || min.right != null) {
        while (min.left != null) min = min.left

        node._key = min.key
        if (min.right != null) {
          node = min
          min = min.right
        }
      }
      node._key = min.key
      node = min
    }

    var current = node
    var parent = node.parent
    while (parent != null) {
      parent.balanceFactor += (if (parent.left == current) -1 else 1)

      current = parent.balanceFactor match {
        case x if x < -1 => {
          if (parent.right.balanceFactor == 1) rotateRight(parent.right)
          val newRoot = rotateLeft(parent)
          if (parent == root) _root = newRoot
          newRoot
        }
        case x if x > 1 => {
          if (parent.left.balanceFactor == -1) rotateLeft(parent.left)
          val newRoot = rotateRight(parent)
          if (parent == root) _root = newRoot
          newRoot
        }
        case _ => parent
      }

      parent = current.balanceFactor match {
        case -1 | 1 => null
        case _ => current.parent
      }
    }

    if (node.parent != null) {
      if (node.parent.left == node) {
        node.parent.left = null
      } else {
        node.parent.right = null
      }
    }

    if (node == root) _root = null

    _size -= 1
    true
  }

  override def rangeImpl(from: Option[A], until: Option[A]): mutable.SortedSet[A] = ???

  override def +=(key: A): AVLTree.this.type = {
    insert(key)
    this
  }

  override def -=(key: A): AVLTree.this.type = {
    remove(key)
    this
  }

  override def contains(elem: A): Boolean = findNode(elem).isDefined

  override def iterator: Iterator[A] = ???

  override def keysIteratorFrom(start: A): Iterator[A] = ???


  private def rotateLeft(node: AVLNode): AVLNode = {
    val rightNode = node.right
    node.right = rightNode.left
    if (node.right != null) node.right.parent = node

    rightNode.parent = node.parent
    if (rightNode.parent != null) {
      if (rightNode.parent.left == node) {
        rightNode.parent.left = rightNode
      } else {
        rightNode.parent.right = rightNode
      }
    }

    node.parent = rightNode
    rightNode.left = node

    node.balanceFactor += 1
    if (rightNode.balanceFactor < 0) {
      node.balanceFactor -= rightNode.balanceFactor
    }

    rightNode.balanceFactor += 1
    if (node.balanceFactor > 0) {
      rightNode.balanceFactor += node.balanceFactor
    }
    rightNode
  }

  private def rotateRight(node: AVLNode): AVLNode = {
    val leftNode = node.left
    node.left = leftNode.right
    if (node.left != null) node.left.parent = node

    leftNode.parent = node.parent
    if (leftNode.parent != null) {
      if (leftNode.parent.left == node) {
        leftNode.parent.left = leftNode
      } else {
        leftNode.parent.right = leftNode
      }
    }

    node.parent = leftNode
    leftNode.right = node

    node.balanceFactor -= 1
    if (leftNode.balanceFactor > 0) {
      node.balanceFactor -= leftNode.balanceFactor
    }

    leftNode.balanceFactor -= 1
    if (node.balanceFactor < 0) {
      leftNode.balanceFactor += node.balanceFactor
    }
    leftNode
  }

  case class AVLNode(
    private[AVLTree] var _key: A,
    private[AVLTree] var parent: AVLNode = null,
    private[collections] var left: AVLNode = null,
    private[collections] var right: AVLNode = null,
    private[AVLTree] var balanceFactor: Int = 0) {

    def key: A = _key

    private[AVLTree] def selectNextChild(key: A): AVLNode = matchNextChild(ordering.compare(key, this.key))

    private[AVLTree] def matchNextChild(cmp: Int): AVLNode = cmp match {
      case x if x < 0 => left
      case x if x > 0 => right
      case _ => null
    }
  }

}
