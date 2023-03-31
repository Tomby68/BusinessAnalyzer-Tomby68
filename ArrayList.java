public class ArrayList<T> implements ListInterface<T> {
	private T[] arr;
	private int size;
	private int nextIndex;
	
	public ArrayList() {
		arr = (T[]) new Object[10];
		size = 0;
		nextIndex = 0;
	}
	
	public int size() {
		return size;
	}

	
	public T remove(int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		T tmp = arr[pos];
		for (int i = pos; i < size - 1; i++) {
			arr[i] = arr[i + 1];
		}
		size--;
		return tmp;
		
	}

	
	public T get(int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		return arr[pos];
	}

	
	public boolean add(T item) {
		if (size == arr.length) {
			grow_array();
		}
		arr[size++] = item;
		return true;
	}

	
	public void add(T item, int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		if (size == arr.length) {
			grow_array();
		}
		for (int i = size; i > pos; i--) {
			arr[i] = arr[i - 1];
		}
		arr[pos] = item;
		size++;
	}
	
	private void grow_array() {
		T[] newArr = (T[]) new Object[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = arr[i];
		}
		arr = newArr;
	}

	public Iterator<T> iterator() {
		return new ArrayIterator<T>();
	}
	
	private class ArrayIterator<T> implements Iterator<T> {
		int nextIndex;
		
		public boolean hasNext() {
			return nextIndex < size && nextIndex >= 0;
		}

		public T next() {
			return (T) arr[nextIndex++];
		}

	}
	
}
