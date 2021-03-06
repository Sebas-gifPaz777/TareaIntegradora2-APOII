package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Board {

	private Square first;
	private Square last;
	private int seeds;
	private int rickSeeds;
	private int mortySeeds;
	private int rickTime;
	private int mortyTime;
	private String rickName;
	private String mortyName;
	private int columns;
	private int rows;
	
	private ArrayList<Person> winners;
	


	public Board(int c, int l, int s, int p, String r, String m) {
		columns = c;
		rows = l;
		seeds = s;
		rickName = r;
		mortyName = m;

		rickSeeds = 0;
		mortySeeds = 0;
		
		rickTime = 0;
		mortyTime = 0;
		
		winners = new ArrayList<>();

		try {
			BufferedReader explorer = new BufferedReader(new FileReader("files/winners.txt"));
			String currentLine;

			while ((currentLine = explorer.readLine()) != null) {
				String[] atributes = currentLine.split(":");
				winners.add(new Person(atributes[0],atributes[1]));
			}
			explorer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Se crea el tablero con los numeros de cada casilla
		first = new Square(1);
		last = first;
		first.setPrev(last);
		last.setNext(first);
		int count = 1;
		int numTable = 2;

		while (count < (c * l)) {
			last.setNext(new Square(numTable));
			last.getNext().setPrev(last);
			last = last.getNext();
			last.setNext(first);
			first.setPrev(last);
			numTable++;
			count++;
		}

		// Se a?aden las semillas al tablero
		count = 0;
		int position = (int) (Math.random() * (c * l) + 1);
		while (count < s) {
			if (position == 1) {
				if (!first.getSeed()) {
					first.setSeed(true);
				} else {
					addSeed(position, first.getNext());
				}
			} else {
				addSeed(position - 1, first.getNext());
			}
			count++;
			position = (int) (Math.random() * (c * l) + 1);
		}

		// Se a?aden los portales al tablero
		count = 0;
		char portalId = 'A';
		while (count < p) {
			int countPos1 = 1;
			int countPos2 = 1;
			int position1 = 0;
			int position2 = 0;
			do {
				position1 = ((int) (Math.random() * ((c * l) - 1))) + 1;
				position2 = ((int) (Math.random() * ((c * l) - 1))) + 1;
			} while (position1 == position2 || position1 == position2 + 1 || position1 == position2 - 1);

			Square temp = first;
			Square temp2 = first;
			
			while (countPos1 < position1) {
				temp = temp.getNext();
				countPos1++;
			}
			
			while (countPos2 < position2) {
				temp2 = temp2.getNext();
				countPos2++;
			}
			while (temp.getNext2() != null || temp.getNext() == temp2 || temp == temp2 || temp.getPrev() == temp2) {
				temp = temp.getNext();
			}
			
			while (temp2.getNext2() != null || temp2.getNext() == temp || temp2 == temp || temp2.getPrev() == temp) {
				temp2 = temp2.getNext();
			}
			
			temp.setNext2(temp2);
			temp.setIdPortal(portalId);

			
			temp2.setNext2(temp);
			temp2.setIdPortal(portalId);

			portalId++;
			count++;
		}

		// Se a?aden los jugadores al tablero

		int positionM = (int) (Math.random() * (columns * rows) + 1);
		int positionR = (int) (Math.random() * (columns * rows) + 1);
		Square temp = first;
		int count2 = 1;

		while (count2 < positionR + 1) {
			temp = temp.getNext();
			count2++;
		}
		while (temp.getSeed()) {
			temp = temp.getNext();
		}
		temp.setRick(true);
		temp = first;
		count2 = 1;

		while (count2 < positionM + 1) {
			temp = temp.getNext();
			count2++;
		}
		while (temp.getSeed()) {
			temp = temp.getNext();
		}
		temp.setMorty(true);

	}

	// Metodo recursivo para ubicar la semilla en el tablero, se llama en la l?nea
	// 46 y 50
	public boolean addSeed(int position, Square place) {

		if (position == 1) {
			if (!place.getSeed()) {
				place.setSeed(true);
				return true;
			} else {
				return addSeed(position, place.getNext());
			}
		}

		else {
			return addSeed(position - 1, place.getNext());
		}
	}

	// Impresi?n del tablero
	public void toStrings() {
		Square temp = first;
		for (int i = 0; i < rows; i++) {
			System.out.println("");
			for (int j = 0; j < columns; j++) {

				if (temp.getRick() && temp.getMorty()) {
					System.out.print("[" + "R" + "M" + "] ");
				} else if (temp.getRick()) {
					System.out.print("[" + "R" + "] ");
				} else if (temp.getMorty()) {
					System.out.print("[" + "M" + "] ");
				} else if (temp.getSeed()) {
					System.out.print("[" + "*" + "] ");
				} else {
					System.out.print("[" + temp.getId() + "] ");
				}
				temp = temp.getNext();
			}
		}
		System.out.println("");
	}

	// Impresion de tableros con portales
	public void showPortals() {
		Square temp = first;
		for (int i = 0; i < rows; i++) {
			System.out.println("");
			for (int j = 0; j < columns; j++) {
				System.out.print("[" + temp.getIdPortal() + "] ");
				temp = temp.getNext();
			}
		}
		System.out.println("");
	}

	// Pasos hac?a adelante
	public void stepForward(int steps, int turn) {

		Square player = findPlayer(turn);
		if (turn == 0)
			player.setRick(false);
		else
			player.setMorty(false);

		if(steps==1) {
			player=player.getNext();
		}
		else
			player=player.advance(player.getNext(),steps-1);

		if (player.getSeed()) {
			takeSeed(turn);
			player.setSeed(false);
		}
		if (player.getNext2() != null) {
			player = player.getNext2();

			if (player.getSeed()) {
				takeSeed(turn);
				player.setSeed(false);
			}
		}

		if (turn == 0)
			player.setRick(true);
		else
			player.setMorty(true);
	}

	// Pasos hac?a atras
	public void stepBackward(int steps, int turn) {

		Square player = findPlayer(turn);
		if (turn == 0)
			player.setRick(false);
		else
			player.setMorty(false);

		if(steps==1) {
			player=player.getPrev();
		}
		else
			player= player.back(player.getPrev(),steps-1);

		if (player.getSeed()) {
			takeSeed(turn);
			player.setSeed(false);
		}
		if (player.getNext2() != null) {
			player = player.getNext2();

			if (player.getSeed()) {
				takeSeed(turn);
				player.setSeed(false);
			}
		}

		if (turn == 0)
			player.setRick(true);
		else
			player.setMorty(true);
	}

	// Buscar jugador
	public Square findPlayer(int turn) {
		Square temp = first;
		if (turn == 0) {
			while (!temp.getRick()) {
				temp = temp.getNext();
			}
		} else {
			while (!temp.getMorty()) {
				temp = temp.getNext();
			}
		}

		return temp;
	}
	
	// Tomar semillas
	public void takeSeed(int turn) {
		if (turn == 0) {
			rickSeeds++;
			seeds--;
		} else {
			mortySeeds++;
			seeds--;
		}
	}

	// Cuando se tira el dado, se necesita informaci?n del Main
	public boolean throwDice(int turn, int conti, int dice) {

		if (conti == 1) {
			stepForward(dice, turn);
		} else {
			stepBackward(dice, turn);
		}

		if (seeds == 0)
			return false;
		else
			return true;
	}

	public String scoreBoard() {
		String text = "";
		text += " Rick: " + rickSeeds + " semillas \n" ;
		text += " Morty: " + mortySeeds + " semillas \n" ;
		
		return text;
	}
	public String winner() {
		String text = "";
		int position = 0;
		boolean found = false;
		for (int i = 0; i<winners.size() && !found; i++) {
			if(winners.get(i).getNickname().equalsIgnoreCase(mortyName) || winners.get(i).getNickname().equalsIgnoreCase(rickName)) {
				found=true;
				position = i;
			}
		}
		
		
		if(rickSeeds > mortySeeds) {
			text = " Rick ha ganado recolectando: " + rickSeeds + " semillas";
			
			if(found) {
				int prevScore = Integer.valueOf(winners.get(position).getScore());
				String score = String.valueOf(rickSeeds * 120 - rickTime + prevScore);
				winners.get(position).setScore(score);
			}else {
				String score = String.valueOf(rickSeeds * 120 - rickTime);
				winners.add(new Person(this.rickName, score));
			}
		}else {
			text = " Morty ha ganado recolectando: " + mortySeeds + " semillas";
			
			if(found) {
				int prevScore = Integer.valueOf(winners.get(position).getScore());
				String score =  String.valueOf(mortySeeds * 120 - mortyTime + prevScore) ;
				winners.get(position).setScore(score);
			}else {
				String score =  String.valueOf(mortySeeds * 120 - mortyTime) ;
				winners.add(new Person(this.mortyName, score));
			}
			
		}
		
		Collections.sort(winners);
		
		text += "\n------ TOP 5 -------\n"; 
		for (int i = 0; i<winners.size() && i<5; i++) {
				text+= (i+1) +"." + winners.get(i).getNickname() + " => " + winners.get(i).getScore() + "\n";
		}
		
		writeFile();
		return text;
	}
	
	public int getRickTime() {
		return rickTime;
	}
	public int getMortyTime() {
		return mortyTime;
	}
	public void addRickTime(int newTime) {
		this.rickTime += newTime;
	}
	public void addMortyTime(int newTime) {
		this.mortyTime += newTime;
	}
	
	public void writeFile(){
		File file = new File("files/winners.txt");
		
		FileWriter fw;
		try {
			fw = new FileWriter(file);

			BufferedWriter output = new BufferedWriter(fw);
			
			
			for (int i = 0; i < winners.size(); i++) {
				output.write(winners.get(i).getNickname() + ":" + winners.get(i).getScore() + "\n");
			}

			output.close();
			fw.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
}
