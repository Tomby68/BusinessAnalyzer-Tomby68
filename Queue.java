public class Queue<T> {
	private Node<T> front;
	private Node<T> back;
	
	public Queue() {
		front = null;
		back = null;
	}
	
	
	public boolean empty() {
		return front == null;
	}
	
	public void enqueue(T item) {
		Node<T> node = new Node<T>(item);
		if (empty()) {
			front = node;
			back = node;
		} else {
			back.next = node;
			back = back.next;
		}
	}
	
	public T dequeue() throws Exception {
		if (empty()) {
			throw new Exception();
		}
		Node<T> node = front;
		front = front.next;
		if (empty()) {
			back = null;
		}
		return node.data;
	}
}
