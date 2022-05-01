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
	
	public Board(int c, int l, int s, int p) {
		
		//Se crea el tablero con los numeros de cada casilla
		first=new Square(1);
		last=first;
		first.setBack(last);
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
	
	public void toStrings(int l,int c) {
		Square temp=first;
		for(int i=0;i<l;i++) {
			System.out.println("");
			for(int j=0;j<c;j++) {
				
				if(temp.getRick() && temp.getMorty()) {
					System.out.print("["+temp.getRick()+temp.getMorty()"] ");
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
					System.out.println("["+temp.getId()+"] ");
				}
				temp=temp.getNext();
			}
		}
		System.out.println("");
	}
	
	public void stepForward() {
		
	}
	
}
