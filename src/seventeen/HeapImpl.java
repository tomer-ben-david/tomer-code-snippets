package seventeen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Patterns:
// 1. Key use a new data structure for building the heap from
// 1. Use ArrayList as the new data structure, you can insert things inside.
// 1. heapup and heapdown methods.

public class HeapImpl {
    int[] arr = new int[] {3, 1, 2, 6, 4, 2};
    List<Integer> heap = new ArrayList<>();

    public List<Integer> buildHeap(int[] arr) {
        for (int v: arr) {
            insert(heap, v);
        }
        return heap;
    }

    public int leftChildIndex(List<Integer> heap, int parentIndex) { return parentIndex * 2 + 2; }
    public int rightChildIndex(List<Integer> heap, int parentIndex) { return parentIndex * 2 + 2; }
    public int parentIndex(int childIndex) { return childIndex / 2; }

    public List<Integer> insert(List<Integer> heap, int v) {
        heap.add(v);
        return upheap(heap, heap.size() - 1);
    }

    public List<Integer> upheap(List<Integer> heap, int index) {
        int curIndex = index;
        while (parentIndex(curIndex) >= 0 && heap.get(curIndex) > heap.get(parentIndex(curIndex))) {
            heap = swap(heap, curIndex, parentIndex(curIndex));
            curIndex = parentIndex(curIndex);
        }
        return heap;
    }

    public List<Integer> swap(List<Integer> heap, int i, int j) {
        int tmp = heap.get(j);
        heap.set(j, heap.get(i));
        heap.set(i, tmp);
        return heap;
    }

    public static void main(String[] args) {
        HeapImpl heap = new HeapImpl();
        heap.buildHeap(heap.arr);
        System.out.println(heap.toString());
    }
}
