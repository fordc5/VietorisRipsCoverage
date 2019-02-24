
public class Segment {

	
	private int head, tail;
	
	public Segment(int head, int tail) {
		this.head = head;
		this.tail = tail;
	}
	
	public int getHead() {
		return head;
	}
	
	public int getTail() {
		return tail;
	}
	
	public String toString() {
		return "(" + head + "," + tail + ")";
	}
	
	@Override
	public boolean equals(Object o) {
	    if (!(o instanceof Segment)) {
	    	return false;
	    }
	    Segment obj = (Segment) o;
	    if (obj.getHead() == head & obj.getTail() == tail) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
