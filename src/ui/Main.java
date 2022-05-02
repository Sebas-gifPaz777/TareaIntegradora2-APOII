package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		
		System.out.println("Escribe la cantidad de semillas, debe de ser menor o igual a: "+(c*l));
		int s=Integer.parseInt(br.readLine());
		
		while(s>(c*l)) {
			System.out.println("La cantidad de semillas es muy grande, ingresa otro valor");
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
				int conti=Integer.parseInt(br.readLine());
				playing=board.throwDice(turn, conti, dice);
				
				if(turn==0)
					turn=1;
				else
					turn=0;
			break;
			
			case 2:
				board.toStrings();
			break;
			
			case 3:
				board.showPortals();
			break;
			
			case 4:
				board.showScoreBoard();
			break;
			
			default:
				System.out.println("Esta opción no está disponible");
			
			}
		}
		
		System.out.println("---------------GAME OVER---------------");
		continues=false;
		br.close();
	}
}
