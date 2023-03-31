package for_assignment2;

public interface ListInterface<T> {
	public int size();
	public T remove(int pos) throws Exception;
	public T get(int pos) throws Exception;
	public boolean add(T item);
	public void add(T item, int pos) throws Exception; 
	public Iterator<T> iterator();
}
