package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;

import model.Board;

public class Main {
	
	public static BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	public static  Board board;
	public static boolean continues=true;
	
	public static void main(String[]args) {
		
		while(continues) {
			try {
				menu();
			}catch(IOException | NumberFormatException e) {
				if(e instanceof IOException) {
					System.out.println("Se pordujo un error en entrada o salida de datos");
					continues=false;
				}
				if(e instanceof NumberFormatException) {
					System.out.println("Se produjo un error en entrada de datos, solo numeros");
				}
			}
		}
	}
	
	public static void menu() throws NumberFormatException, IOException {
		System.out.println("Bienvenido\nEscribe un número de columnas");
		int c=Integer.parseInt(br.readLine());
		
		while(c<=0) {
			System.out.println("La cantidad de columnas tiene que ser mayor a 0, ingresa otro valor");
			c=Integer.parseInt(br.readLine());
		}
		System.out.println("Escribe un número de filas");
		int l=Integer.parseInt(br.readLine());
		
		while(l<=0) {
			System.out.println("La cantidad de filas tiene que ser mayor a 0, ingresa otro valor");
			l=Integer.parseInt(br.readLine());
		}
		
		System.out.println("Escribe la cantidad de semillas, debe de ser menor o igual a: "+((c*l)-2)+", y debe ser impar");
		int s=Integer.parseInt(br.readLine());
		
		while(s>((c*l)-2) || s%2==0) {
			System.out.println("La cantidad de semillas es muy grande o es par, ingresa otro valor");
			s=Integer.parseInt(br.readLine());
		}
		
		System.out.println("Escribe la cantidad de portales, debe ser menor a: "+((c*l)*0.5));
		int p=Integer.parseInt(br.readLine());
		
		while(p>((c*l)*0.5)) {
			System.out.println("La cantidad de portales es muy grande, ingresa otro valor");
			p=Integer.parseInt(br.readLine());
		}
		
		System.out.println("Nombre para el jugador 1, el cual va a ser Rick");
		String r=br.readLine();
		
		System.out.println("Nombre para el jugador 2, el cual va a ser Morty");
		String m=br.readLine();
		board= new Board(c,l,s,p,r,m);
		
		boolean playing=true;
		int turn=(int)Math.floor(Math.random()*2);
		
		if(turn==0) 
			System.out.println("Comienza jugando Rick");
		else
			System.out.println("Comienza jugando Morty");
		
		while(playing) {
			System.out.println("Escoge una opción\n1:Tirar dado\n2:Ver tablero\n3:Ver enlaces\n4:Marcador");
			int ans=Integer.parseInt(br.readLine());
			
			switch(ans) {
			case 1:
				int dice= ((int) (Math.random()*(6-1)) ) +1;
				System.out.println("Sacaste" + dice);
				System.out.println("Quieres:\n1:Avanzar\n2:Retroceder");
				
				Instant instantStarted = Instant.now();
				
				int conti=Integer.parseInt(br.readLine());
				
				Instant instantStopped = Instant.now();
				
				Duration duration = Duration.between(instantStarted, instantStopped);
				int time = (int)(duration.toMillis()/1000);
				playing=board.throwDice(turn, conti, dice);
				System.out.println(time);
				
				if(turn==0) {
					turn=1;
					board.addRickTime(time);
					System.out.println(board.getRickTime());
				}else
					turn=0;
					board.addMortyTime(time);
					System.out.println(board.getMortyTime());
			break;
			
			case 2:
				board.toStrings();
			break;
			
			case 3:
				board.showPortals();
			break;
			
			case 4:
				System.out.println(board.scoreBoard()); 
			break;
			
			default:
				System.out.println("Esta opción no está disponible");
			
			}
		}
		
		System.out.println("---------------GAME OVER---------------\n");
		
		System.out.println(board.winner());
		continues=false;
		br.close();
	}
}
