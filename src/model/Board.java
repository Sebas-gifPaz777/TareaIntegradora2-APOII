package model;

import java.time.LocalTime;

public class Board {
	
	private Square first;
	private Square last;
	private int seeds=0;
	private int rickSeeds=0;
	private int mortySeeds=0;
	private LocalTime rickTime;
	private LocalTime mortyTime;
	private String rickName;
	private String mortyName;
	private int columns;
	private int rows;
	
	public Board(int c, int l, int s, int p, String r, String m) {
		columns=c;
		rows=l;
		seeds=s;
		rickName=r;
		mortyName=m;
		
		//Se crea el tablero con los numeros de cada casilla
		first=new Square(1);
		last=first;
		first.setPrev(last);
		last.setNext(first);
		int count=1;
		int numTable=2;
		
		while(count<(c*l)) {
			last.setNext(new Square(numTable));
			last.getNext().setPrev(last);
			last=last.getNext();
			last.setNext(first);
			first.setPrev(last);
			numTable++;
			count++;
		}
		
		//Se añaden las semillas al tablero
		count=0;
		int position=(int)(Math.random()*(c*l)+1);
		while(count<s) {
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
			count++;
			position=(int)(Math.random()*(c*l)+1);
		}
		
		//Se añaden los portales al tablero 
		/*count=0;
		while(count<p) {
			
		}*/
		
		//Se añaden los jugadores al tablero
		
		int positionM=(int)(Math.random()*(columns*rows)+1);
		int positionR=(int)(Math.random()*(columns*rows)+1);
		Square temp=first;
		int count2=1;
		
		while(count2<positionR+1) {
			temp=temp.getNext();
			count2++;
		}
		while(temp.getSeed()) {
			temp=temp.getNext();
		}
		temp.setRick(true);
		temp=first;
		count2=1;
		
		while(count2<positionM+1) {
			temp=temp.getNext();
			count2++;
		}
		while(temp.getSeed()) {
			temp=temp.getNext();
		}
		temp.setMorty(true);
		
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
	
	//Impresión del tablero
	public void toStrings() {
		Square temp=first;
		for(int i=0;i<rows;i++) {
			System.out.println("");
			for(int j=0;j<columns;j++) {
				
				if(temp.getRick() && temp.getMorty()) {
					System.out.print("["+"R"+"M" + "] ");
				}
				else if(temp.getRick()) {
					System.out.print("["+"R"+"] ");
				}
				else if(temp.getMorty()){
					System.out.print("["+"M"+"] ");
				}
				else if(temp.getSeed()) {
					System.out.print("["+"*"+"] ");
				}
				else {
					System.out.print("["+temp.getId()+"] ");
				}
				temp=temp.getNext();
			}
		}
		System.out.println("");
	}
	
	//Impresion de tableros con portales
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
	//Pasos hacía adelante
	public void stepForward(int steps, int turn) {
		
		int count=0;
		Square player= findPlayer(turn);
		if(turn==0)
			player.setRick(false);
		else
			player.setMorty(false);
		
		while(count<steps) {
			player=player.getNext();
			count++;
		}
		
		if(player.getSeed()) {
			takeSeed(turn);
			player.setSeed(false);
		}
		if(player.getNext2()!=null) {
			player=player.getNext2();
			
			if(player.getSeed()) {
				takeSeed(turn);
				player.setSeed(false);
			}
		}
		
		if(turn==0)
			player.setRick(true);
		else
			player.setMorty(true);
	}
	
	//Pasos hacía atras
		public void stepBackward(int steps, int turn) {
			
			int count=0;
			Square player= findPlayer(turn);
			if(turn==0)
				player.setRick(false);
			else
				player.setMorty(false);
			
			while(count<steps) {
				player=player.getPrev();
				count++;
			}
			
			if(player.getSeed()) {
				takeSeed(turn);
				player.setSeed(false);
			}
			if(player.getNext2()!=null) {
				player=player.getNext2();
				
				if(player.getSeed()) {
					takeSeed(turn);
					player.setSeed(false);
				}
			}
			
			if(turn==0)
				player.setRick(true);
			else
				player.setMorty(true);
		}
	
	//Buscar jugador
	private Square findPlayer(int turn) {
		Square temp=first;
		if(turn==0) {
			while(!temp.getRick()) {
				temp=temp.getNext();
			}
		}
		else {
			while(!temp.getMorty()) {
				temp=temp.getNext();
			}
		}
		
		return temp;
	}

	//Tomar semillas
	public void takeSeed(int turn) {
		if(turn==0) {
			rickSeeds++;
			seeds--;
		}
		else {
			mortySeeds++;
			seeds--;
		}
	}
	
	//Cuando se tira el dado, se necesita información del Main
	public boolean throwDice(int turn, int conti, int dice) {
		
		if(conti==1) {
			stepForward(dice,turn);
		}
		else {
			stepBackward(dice,turn);
		}
		
		if(seeds==0)
			return false;
		else
			return true;
	}

	public void showScoreBoard() {
		
		
	}
}
