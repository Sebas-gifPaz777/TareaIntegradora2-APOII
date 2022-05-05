package model;

import java.io.Serializable;

public class Person implements Serializable, Comparable<Person>{
	
	private String nickname;
	private String score;
	
	public Person(String nickname, String score) {	
		this.nickname = nickname;
		this.score = score;
	}
	
	public String getNickname() {
		return nickname;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return nickname + ":" + score; 
	}
	
	@Override
	public int compareTo(Person other) {
		int result = 0;
		int thisScore = Integer.valueOf(score);
		int otherScore = Integer.valueOf(other.getScore());
		
		result = thisScore - otherScore ;
		result *= -1;
		return result;
	}
}
