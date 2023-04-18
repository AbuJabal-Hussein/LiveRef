package tests.Quicksort;

public class QuickSort {
	
	private int[] arr;

	public QuickSort(int[] arr) {
		this.arr = arr;
	}
	
	public int[] sort() {
		sort(0, arr.length);
		return arr;
	}

	private void sort(int left, int right) {
        if (left < right) {
            int pivotValue = arr[right];
            int i = left - 1;
            for (int j = left; j < right; j++) {
                if (arr[j] <= pivotValue) {
                    i++;
                    swap(i, j);
                }
            }
            swap(i + 1, right);
            int pivotIndex = i + 1;
            sort(left, pivotIndex - 1);
            sort(pivotIndex + 1, right);
        }
    }

    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
