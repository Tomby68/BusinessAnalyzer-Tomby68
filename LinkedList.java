public class LinkedList<T> implements ListInterface<T> {
	Node<T> head;
	int size;
	
	public LinkedList() {
		head = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public T remove(int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		
		Node<T> node = head;
		for (int i = 0; i < pos - 1; i++) {
			node = node.next;
		}
		Node<T> tmp = node.next;
		node.next = tmp.next;
		size--;
		return tmp.data;
	}

	public T get(int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		Node<T> node = head;
		for (int i = 0; i < pos; i++) {
			node = node.next;
		}
		return node.data;
	}

	public boolean add(T item) {
		if (size == 0) {
			head = new Node<T>(item);
			size++;
			return true;
		}
		Node<T> node = head;
		for (int i = 0; i < size - 1; i++) {
			node = node.next;
		}
		node.next = new Node<T>(item);
		size++;
		return true;
	}

	public void add(T item, int pos) throws Exception {
		if (pos < 0 || pos >= size) {
			throw new Exception();
		}
		if (size == 0) {
			head = new Node<T>(item);
			size++;
			return;
		}
		
		Node<T> node = head;
		for (int i = 0; i < pos - 1; i++) {
			node = node.next;
		}
		Node<T> newNode = new Node<T>(item);
		newNode.next = node.next;
		node.next = newNode;
		size++;
		
	}
	
	public Iterator<T> iterator() {
		return new LLIterator<T>();
	}

	private class LLIterator<T> implements Iterator<T> {
		private Node<T> node = (Node<T>) head;
		
		public boolean hasNext() {
			if (node == null) {
				return false;
			} 
			return node.next != null;
		}

		public T next() {
			Node<T> prev = node;
			node = node.next;
			return prev.data;
		}

	}

}
