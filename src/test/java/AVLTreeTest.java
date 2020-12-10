import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AVLTreeTest {

    @Test
    public void addTest(){
        SortedSet<Integer> avl = new AVLTree<>();

        assertTrue(avl.add(5));
        assertTrue(avl.add(3));
        assertTrue(avl.add(7));
        assertTrue(avl.add(2));
        assertTrue(avl.add(4));

        assertEquals(5, avl.size());

        avl.clear();

        assertTrue(avl.add(1));
        assertTrue(avl.add(2));
        assertTrue(avl.add(3));
        assertTrue(avl.add(4));
        assertTrue(avl.add(5));

        assertEquals(5, avl.size());

        avl.clear();

        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(7);

        avl.addAll(list);

        assertEquals(5, avl.size());
    }

    @Test
    public void removeTest(){
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(5);
        avl.add(3);
        avl.add(7);
        avl.add(2);
        avl.add(4);

        avl.remove(4);
        assertEquals(4, avl.size());
        avl.remove(2);
        assertEquals(3, avl.size());
        avl.remove(7);
        assertEquals(2, avl.size());
        avl.remove(3);
        assertEquals(1, avl.size());
        avl.remove(5);
        assertEquals(0, avl.size());

        assertTrue(avl.isEmpty());

        avl.add(1);
        avl.add(2);
        avl.add(3);
        avl.add(4);
        avl.add(5);

        avl.remove(1);
        assertEquals(4, avl.size());
        avl.remove(2);
        assertEquals(3, avl.size());
        avl.remove(3);
        assertEquals(2, avl.size());
        avl.remove(4);
        assertEquals(1, avl.size());
        avl.remove(5);
        assertEquals(0, avl.size());

        assertTrue(avl.isEmpty());

        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(7);

        avl.addAll(list);

        avl.removeAll(list);

        assertTrue(avl.isEmpty());
    }

    @Test
    public void containsTest(){
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(5);
        avl.add(3);
        avl.add(7);
        avl.add(2);
        avl.add(4);

        assertTrue(avl.contains(2));
        assertTrue(avl.contains(3));
        assertTrue(avl.contains(4));
        assertTrue(avl.contains(5));
        assertTrue(avl.contains(7));
        assertFalse(avl.contains(1));
        assertFalse(avl.contains(6));
        assertFalse(avl.contains(8));
        assertFalse(avl.contains(9));
        assertFalse(avl.contains(11));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(7);

        assertTrue(avl.containsAll(list));
    }

    @Test
    public void isEmptyTest(){
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(1);
        assertFalse(avl.isEmpty());
        avl.remove(1);
        assertTrue(avl.isEmpty());
    }


    @Test
    public void iteratorTest(){
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(5);
        avl.add(3);
        avl.add(7);
        avl.add(2);
        avl.add(4);

        Iterator<Integer> iterator1 = avl.iterator();
        Iterator<Integer> iterator2 = avl.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator2.next(), iterator1.next());
        }

        Integer value;
        Integer[] values = new Integer[5];
        avl.toArray(values);

        ArrayList<Integer> list = new ArrayList<>();

        Iterator<Integer> avlIterator = avl.iterator();
        while (avlIterator.hasNext()) {
            value = avlIterator.next();
            list.add(value);
            avlIterator.remove();
        }
        assertTrue(avl.isEmpty());
        assertArrayEquals(values, list.toArray(new Integer[5]));
    }

    @Test
    public void smallLeftRotationTest() {
        /*
        1            2
         \          / \
          2   -->  1   3
           \
            3


         */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        SortedSet<Integer> avlSet = new AVLTree<>();
        avlSet.addAll(list);
        SortedSet<Integer> controlSet = new TreeSet<>(list);

        Iterator<Integer> iterator1 = controlSet.iterator();
        Iterator<Integer> iterator2 = avlSet.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }
    }

    @Test
    public void smallRightRotationTest() {
        /*
             3          2
            /          / \
           2   -->    1   3
          /
         1

         */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(1);

        SortedSet<Integer> avlSet = new AVLTree<>();
        avlSet.addAll(list);
        SortedSet<Integer> controlSet = new TreeSet<>(list);

        Iterator<Integer> iterator1 = controlSet.iterator();
        Iterator<Integer> iterator2 = avlSet.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }
    }

    @Test
    public void largeLeftRotationTest() {
        /*
           10                    13
          /  \                  /  \
         5    15              10    15
             /  \     -->     /    /  \
           13    16          5   14    16
            \
             14

         */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(5);
        list.add(15);
        list.add(13);
        list.add(16);
        list.add(14);

        SortedSet<Integer> avlSet = new AVLTree<>();
        avlSet.addAll(list);
        SortedSet<Integer> controlSet = new TreeSet<>(list);

        Iterator<Integer> iterator1 = controlSet.iterator();
        Iterator<Integer> iterator2 = avlSet.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }
    }

    @Test
    public void largeRightRotationTest() {
        ArrayList<Integer> list = new ArrayList<>();
        /*
             10             8
            /  \           / \
           5    15        5   10
          / \      -->   / \    \
         4   8          4   7   15
             /
            7

         */

        list.add(10);
        list.add(5);
        list.add(15);
        list.add(4);
        list.add(8);
        list.add(7);

        SortedSet<Integer> avlSet = new AVLTree<>();
        avlSet.addAll(list);
        SortedSet<Integer> controlSet = new TreeSet<>(list);

        Iterator<Integer> iterator1 = controlSet.iterator();
        Iterator<Integer> iterator2 = avlSet.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }
    }

    @Test
    public void subAVLTreeTest() {
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(13);
        avl.add(10);
        avl.add(15);
        avl.add(14);
        avl.add(5);
        avl.add(16);

        SortedSet<Integer> subAvl = avl.subSet(10, 16);

        Iterator<Integer> iterator1 = subAvl.iterator();
        Iterator<Integer> iterator2 = subAvl.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator2.next(), iterator1.next());
        }

        Integer value;
        Integer[] values = new Integer[4];
        subAvl.toArray(values);

        ArrayList<Integer> list = new ArrayList<>();

        Iterator<Integer> subAvlIterator = subAvl.iterator();
        while (subAvlIterator.hasNext()) {
            value = subAvlIterator.next();
            list.add(value);
            subAvlIterator.remove();
        }
        assertTrue(subAvl.isEmpty());
        assertArrayEquals(values, list.toArray(new Integer[4]));
    }

    @Test
    public void headAndTailAVLTreeTest() {
        SortedSet<Integer> avl = new AVLTree<>();

        avl.add(13);
        avl.add(10);
        avl.add(15);
        avl.add(14);
        avl.add(5);
        avl.add(16);

        SortedSet<Integer> subAvl = avl.headSet(14);

        assertEquals(3, subAvl.size());
        assertTrue(subAvl.contains(5));
        assertTrue(subAvl.contains(10));
        assertTrue(subAvl.contains(13));
        assertFalse(subAvl.contains(14));
        assertFalse(subAvl.contains(15));
        assertFalse(subAvl.contains(16));

        subAvl = avl.tailSet(14);

        assertEquals(3, subAvl.size());
        assertFalse(subAvl.contains(5));
        assertFalse(subAvl.contains(10));
        assertFalse(subAvl.contains(13));
        assertTrue(subAvl.contains(14));
        assertTrue(subAvl.contains(15));
        assertTrue(subAvl.contains(16));
    }
}