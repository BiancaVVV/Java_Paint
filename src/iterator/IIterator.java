package iterator;

public interface IIterator {
	public boolean hasNext();
	public Object next();
	public void add(Object object);
	public void first();
	public boolean done();
	public Object get();
	public void remove();
	public int getIndex();
}
