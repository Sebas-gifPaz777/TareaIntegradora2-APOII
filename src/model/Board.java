package model;

import java.time.LocalTime;

public class Board {
	
	private Square first;
	private Square last;
	private int seeds=0;
	private int rickSeeds=0;
	private int mortySeeds=0;
	private int rickPosition=0;
	private int mortyPosition=0;
	private LocalTime rickTime;
	private LocalTime mortyTime;
	private String rickName;
	private String mortyName;
	private int columns;
	private int rows;
	private Square tempRick;
	private Square tempMorty;
	
	public Board(int c, int l, int s, int p) {
		columns=c;
		rows=l;
		//Se crea el tablero con los numeros de cada casilla
		first=new Square(1);
		last=first;
		first.setPrev(last);
		last.setNext(first);
		int count=1;
		int numTable=2;
		
		while(count<((c*l))) {
			last.setNext(new Square(numTable));
			last=last.getNext();
			last.setNext(first);
			numTable++;
			count++;
		}
		
		//Se añaden las semillas al tablero
		int count2=0;
		int position=(int)(Math.random()*(c*l)+1);
		while(count2<s) {
			if(position==1) {
				if(!first.getSeed()) {
					first.setSeed(true);
				}	
				else {
					addSeed(position, first.getNext());
				}	
			}
			else {
				addSeed(position-1,first.getNext());
			}
			count2++;
			position=(int)(Math.random()*(c*l)+1);
		}
		
		int count3=0;
		while(count3<p) {
			
		}
		
		//Se añaden los portales al tablero 
	}
	
	//Metodo recursivo para ubicar la semilla en el tablero, se llama en la línea 46 y 50
	public boolean addSeed(int position, Square place) {
		
		if(position==1) {
			if(!place.getSeed()) {
				place.setSeed(true);
				return true;
			}	
			else {
				return addSeed(position,place.getNext());
			}	
		}
		
		else {
			return addSeed(position-1, place.getNext());
		}
	}
	
	public void toStrings() {
		Square temp=first;
		for(int i=0;i<rows;i++) {
			System.out.println("");
			for(int j=0;j<columns;j++) {
				
				if(temp.getRick() && temp.getMorty()) {
					System.out.print("["+temp.getRick()+temp.getMorty() + "] ");
				}
				else if(temp.getRick()) {
					System.out.print("["+temp.getRick()+"] ");
				}
				else if(temp.getMorty()){
					System.out.print("["+temp.getMorty()+"] ");
				}
				else if(temp.getSeed()) {
					System.out.print("["+temp.getSeed()+"] ");
				}
				else {
					System.out.print("["+temp.getId()+"] ");
				}
				temp=temp.getNext();
			}
		}
		System.out.println("");
	}
	
	public void showPortals() {
		Square temp=first;
		for(int i=0;i<rows;i++) {
			System.out.println("");
			for(int j=0;j<columns;j++) {
				
				if(temp.getNext2()!=null) {
					System.out.print("["+temp.getIdPortal()+ "] ");
				}
				else {
					System.out.print("["+" "+"] ");
				}
				temp=temp.getNext();
			}
		}
		System.out.println("");
	}
	
	public void stepForward(int steps, Square player, String character) {
		
		int count=0;
		while(count<steps) {
			player=player.getNext();
			if(player.getSeed()) {
				takeSeed(character);
			}
			if(player.getNext2()!=null) {
				player=player.getNext2();
				
				if(player.getSeed()) {
					takeSeed(character);
				}
			}
			count++;
		}
	}
	public void stepBackward(int steps, Square player, String character) {
		
		int count=0;
		while(count<steps) {
			player=player.getPrev();
			if(player.getSeed()) {
				takeSeed(character);
			}
			if(player.getNext2()!=null) {
				player=player.getNext2();
				
				if(player.getSeed()) {
					takeSeed(character);
				}
			}
			count++;
		}
	}
	
	private void takeSeed(String character) {
		if(character.equalsIgnoreCase("Rick")) {
			rickSeeds++;
		}
		else {
			mortySeeds++;
		}
	}
}
