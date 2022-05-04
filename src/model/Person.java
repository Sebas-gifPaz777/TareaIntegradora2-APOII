package model;

public class Person {
	
	private String nickname;
	private int score;
	
	public Person(String nickname, int score) {	
		this.nickname = nickname;
		this.score = score;
	}
	
	public String getNickname() {
		return nickname;
	}
	public int getScore() {
		return score;
	}
}
