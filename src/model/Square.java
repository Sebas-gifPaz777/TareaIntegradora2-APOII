package model;


public class Square {
	
	private char idPortal=' ';
	private int id;
	private boolean morty;
	private boolean rick;
	private boolean seed;
	
	private Square prev;
	private Square next;
	private Square next2;
	
	public Square(int id) {
		this.id = id;
		morty = false;
		rick = false;
		seed = false; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getMorty() {
		return morty;
	}

	public void setMorty(boolean morty) {
		this.morty = morty;
	}

	public boolean getRick() {
		return rick;
	}

	public void setRick(boolean rick) {
		this.rick = rick;
	}

	public boolean getSeed() {
		return seed;
	}

	public void setSeed(boolean seed) {
		this.seed = seed;
	}

	public Square getPrev() {
		return prev;
	}

	public void setPrev(Square prev) {
		this.prev = prev;
	}

	public Square getNext() {
		return next;
	}

	public void setNext(Square next) {
		this.next = next;
	}
	public Square getNext2() {
		return next2;
	}
	
	public void setNext2(Square next2) {
		this.next2 = next2;
	}

	public char getIdPortal() {
		return idPortal;
	}
	
	public void setIdPortal(char newIdP) {
		idPortal=newIdP;
	}

}
