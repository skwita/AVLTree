import java.util.*;

public class AVLTree <T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {
    private Node<T> root;
    int size = 0;

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = this.right = null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private int height(Node<T> node) {
        return updateHeight(node);
    }

    private int updateHeight (Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left),height(node.right));
    }

    public boolean add(T value) {
        root = add(root, value);
        size++;
        return contains(value);
    }

    private Node<T> add(Node<T> node, T value) {
        if (node == null) return new Node<>(value);
        int comparison = node.value.compareTo(value);
        if (comparison > 0) {
            node.left = add(node.left, value);
        } else if (comparison < 0) {
            node.right = add(node.right, value);
        } else {
            throw new RuntimeException("duplicate Value!");
        }
        node = rebalanceTree(node);
        return node;
    }

    @Override
    public boolean remove(Object o) {
        if (root == null) return false; // doesn't contain or root is null
        @SuppressWarnings("unchecked")
        T t = (T) o;
        root = remove(root, t);
        if (!contains(t)) {
            size--;
            return true;
        }
        return false;
    }

    private Node<T> remove(Node<T> node, T value) {
        if (node == null) return new Node<>(value);
        int comparison = node.value.compareTo(value);
        if (comparison > 0) {
            node.left = remove(node.left, value);
        } else if (comparison < 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node<T> mostLeftChild = mostLeftChild(node.right);
                node.value = mostLeftChild.value;
                node.right = remove(node.right, node.value);
            }
        }
        if (node != null) {
            node = rebalanceTree(node);
        }
        return node;
    }


    /*
          a                c
         / \              / \
        B   c     -->    a   E
           / \          / \
          D   E        B   D

    */
    private Node<T> rotateLeftSmall (Node<T> node) {
        Node<T> b = node.left;
        Node<T> c = node.right;
        Node<T> d = node.right.left;
        Node<T> e = node.right.right;

        Node<T> n = new Node<>(c.value);
        n.left = node;
        n.right = e;
        n.left.left = b;
        n.left.right = d;

        return n;
    }

    /*
          a                   d
         / \                 / \
        B   c               a   c
           / \     -->     / \ / \
          d   E           B  F G  E
         / \
        F   G

     */
    private Node<T> rotateLeftLarge (Node<T> node) {
        Node<T> b = node.left;
        Node<T> c = node.right;
        Node<T> d = node.right.left;
        Node<T> e = node.right.right;
        Node<T> f = node.right.left.left;
        Node<T> g = node.right.left.right;

        Node<T> n = new Node<>(d.value);
        n.left = node;
        n.right = c;
        n.left.left = b;
        n.left.right = f;
        n.right.left = g;
        n.right.right = e;

        return n;
    }

    /*
              a                b
             / \              / \
            b   C     -->    D   a
           / \                  / \
          D   E                E   C

    */
    private Node<T> rotateRightSmall (Node<T> node) {
        Node<T> b = node.left;
        Node<T> c = node.right;
        Node<T> d = node.left.left;
        Node<T> e = node.left.right;

        Node<T> n = new Node<>(b.value);
        n.right = node;
        n.left = d;
        n.right.left = e;
        n.right.right = c;

        return n;
    }

    /*
                  a               e
                 / \             / \
                b   C           b   a
               / \     -->     / \ / \
              D   e           D  F G  C
                 / \
                F   G

         */
    private Node<T> rotateRightLarge (Node<T> node) {
        Node<T> b = node.left;
        Node<T> c = node.right;
        Node<T> d = node.left.left;
        Node<T> e = node.left.right;
        Node<T> f = node.left.right.left;
        Node<T> g = node.left.right.right;

        Node<T> n = new Node<>(e.value);
        n.left = b;
        n.right = node;
        n.left.left = d;
        n.left.right = f;
        n.right.left = g;
        n.right.right = c;

        return n;
    }

    private Node<T> rebalanceTree (Node<T> node) {
        if (node.right != null) {
            if (height(node.right) - height(node.left) == 2) {
                if (height(node.right.left) <= height(node.right.right)) {
                    node = rotateLeftSmall(node);
                } else {
                    node = rotateLeftLarge(node);
                }
            }
        }
        if (node.left != null) {
            if (height(node.left) - height(node.right) == 2) {
                if (height(node.left.right) <= height(node.left.left)) {
                    node = rotateRightSmall(node);
                }
                else {
                    node =  rotateRightLarge(node);
                }
            }
        }
        return node;
    }

    private Node<T> mostLeftChild(Node<T> node) {
        Node<T> current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public Iterator<T> iterator() {
        return new BinarySearchTreeIterator();
    }

    public class BinarySearchTreeIterator implements Iterator<T> {
        private final Stack<Node<T>> nodesStack = new Stack<>();
        Node<T> currentNode;

        private BinarySearchTreeIterator() {
            addLeftBranch(root);
        }

        private void addLeftBranch(Node<T> n) {
            if (n != null) {
                nodesStack.push(n);
                addLeftBranch(n.left);
            }
        }

        @Override
        public boolean hasNext() {
            return !nodesStack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            currentNode = nodesStack.pop();
            Node<T> workOutNode = currentNode;

            addLeftBranch(workOutNode.right);
            return workOutNode.value;
        }

        @Override
        public void remove() {
            if (currentNode == null) throw new IllegalStateException();
            AVLTree.this.remove(currentNode, currentNode.value);
            currentNode = null;
            size--;
        }
    }

    @Override
    public Comparator<? super T> comparator() {
        return (Comparator<T>) Comparable::compareTo;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c){
            if (!contains(element)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c){
            this.add(t);
        }
        return containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (this.containsAll(c)) {
            for (Object t : this) {
                if (!c.contains(t)) remove(t);
            }
            return true;
        } else return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (this.containsAll(c)) {
            for (Object t : c) remove(t);
            return true;
        } else return false;

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        if (fromElement.compareTo(toElement) >= 0) throw new IllegalArgumentException();
        return new SubAVLTree(fromElement, toElement, this);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubAVLTree(null, toElement, this);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubAVLTree(fromElement, null, this);
    }

    private class SubAVLTree extends AVLTree<T> {
        final T bottom;
        final T top;
        AVLTree<T> avlTree;

        private SubAVLTree(T bottom, T top, AVLTree<T> avlTree) {
            this.bottom = bottom;
            this.top = top;
            this.avlTree = avlTree;
        }

        private boolean check(T value) {
            return (bottom != null && top != null &&
                    value.compareTo(bottom) >= 0 && value.compareTo(top) < 0) ||
                    (bottom == null && value.compareTo(top) < 0) ||
                    (top == null && value.compareTo(bottom) >= 0);
        }

        @Override
        public int size() {
            if (avlTree == null) return 0;
            int count = 0;
            for (T value : avlTree)
                if (check(value)) count++;
            return count;
        }

        @Override
        public boolean contains(Object o) {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            return check(t) && avlTree.contains(t);
        }

        @Override
        public boolean add(T value) {
            if (!check(value))
                throw new IllegalArgumentException();
            return avlTree.add(value);
        }

        @Override
        public boolean remove(Object o) {
            @SuppressWarnings("unchecked")
            T value = (T) o;
            if (!check(value))
                throw new IllegalArgumentException();
            return avlTree.remove(value);
        }

        public Iterator<T> iterator() {
            return new SubAVLTree.BinarySearchTreeIterator();
        }

        public class BinarySearchTreeIterator implements Iterator<T> {
            private final Stack<Node<T>> nodesStack = new Stack<>();
            Node<T> currentNode;

            private BinarySearchTreeIterator() {
                addLeftBranch(root);
            }

            private void addLeftBranch(Node<T> n) {
                if (n != null && check(n.value)) {
                    nodesStack.push(n);
                    addLeftBranch(n.left);
                }
            }

            @Override
            public boolean hasNext() {
                return !nodesStack.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();

                currentNode = nodesStack.pop();
                Node<T> workOutNode = currentNode;

                addLeftBranch(workOutNode.right);
                return workOutNode.value;
            }

            @Override
            public void remove() {
                if (currentNode == null) throw new IllegalStateException();
                avlTree.remove(currentNode, currentNode.value);
            }

        }
        @Override
        public T first() {
            for (T value : avlTree)
                if (check(value)) return value;
            throw new NoSuchElementException();
        }

        @Override
        public T last() {
            T result = null;
            for (T value : avlTree)
                if (check(value)) result = value;
            if (result == null) throw new NoSuchElementException();
            return result;
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> currentNode = root;
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> currentNode = root;
        while (currentNode.right != null) {
            currentNode = currentNode.right;
        }
        return currentNode.value;
    }

}