public class Node<T> {
	T data;
	Node<T> next;
	
	public Node(T value) {
		data = value;
		next = null;
	}
}
