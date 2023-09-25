package pro.sky.IntegerList;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {

    private static final int INITIAL_SIZE = 15;

    private Integer[] data;
    private int capacity;

    public IntegerListImpl() {
        this(INITIAL_SIZE);
    }

    public IntegerListImpl(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным!");
        }
        data = new Integer[n];
        capacity = 0;
    }

    @Override
    public Integer add(Integer item) {
        return add(capacity, item);
    }

    @Override
    public Integer add(int index, Integer item) {
        if (capacity >= data.length) {
            throw new IllegalArgumentException("Список полон!");
        }
        checkNotNull(item);
        checkNonNegativeIndex(index);
        growIfNeeded(index, false);
        System.arraycopy(data, index, data, index + 1, capacity - index);
        data[index] = item;
        capacity++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkNotNull(item);
        checkNonNegativeIndex(index);
        growIfNeeded(index, true);
        return data[index] = item;
    }

    @Override
    public Integer remove(Integer item) {
        int indexForRemoving = indexOf(item);
        if (indexForRemoving == -1) {
            throw new IllegalArgumentException("Элемент не найден!");
        }
        return remove(indexForRemoving);
    }

    @Override
    public Integer remove(int index) {
        checkNonNegativeIndex(index);
        growIfNeeded(index, true);
        Integer removed = data[index];
        System.arraycopy(data, index + 1, data, index, capacity - 1 - index);
        data[--capacity] = null;
        return removed;
    }

    @Override
    public boolean contains(Integer item) {
        checkNotNull(item);

        Integer[] arrayForSearch = toArray();
        sortInsertion(arrayForSearch);

        int min = 0;
        int max = arrayForSearch.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (item.equals(arrayForSearch[mid])) {
                return true;
            }
            if (item < arrayForSearch[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Integer item) {
        checkNotNull(item);
        int index = -1;
        for (int i = 0; i < capacity; i++) {
            if (data[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Integer item) {
        checkNotNull(item);
        int index = -1;
        for (int i = capacity - 1; i >= 0; i--) {
            if (data[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public Integer get(int index) {
        checkNonNegativeIndex(index);
        growIfNeeded(index, true);
        return data[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (size() != otherList.size()) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (!data[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            data[i] = null;
        }
        capacity = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] result = new Integer[capacity];
        System.arraycopy(data, 0, result, 0, capacity);
        return result;
    }

    private void sortInsertion(Integer[] arr) {
        quickSort(arr, 0,arr.length - 1);
    }

    private void quickSort(Integer[] arr,int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }
    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }
    private static void swapElements(Integer[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    private void checkNotNull(Integer item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("Нельзя хранить в списке null-ы!");
        }
    }

    private void checkNonNegativeIndex(int index) {
        if (index < 0) {
            grow();
        }
    }

    private void growIfNeeded(int index, boolean includeEquality) {
        boolean expression = includeEquality ? index >= capacity : index > capacity;
        if (expression) {
            throw new IllegalArgumentException("Индекс: " + index + ", Размер: " + capacity);
        }
    }

    private void grow() {
        data=Arrays.copyOf(data, capacity+capacity/2);
    }
}