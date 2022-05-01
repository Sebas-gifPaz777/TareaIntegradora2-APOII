package model;

public class Portal extends Square {
	
	private Portal next2;

	public Portal(int id) {
		super(id);
	
	}

	public Portal getNext2() {
		return next2;
	}

	public void setNext2(Portal next2) {
		this.next2 = next2;
	}

}
