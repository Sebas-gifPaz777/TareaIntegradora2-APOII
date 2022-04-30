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
					System.out.println("Se profujo un erro en entrada de datos, solo numeros");
				}
			}
		}
	}
	
	public static void menu() throws NumberFormatException, IOException {
		System.out.println("Bienvenido\nEscribe un número de columnas");
		int c=Integer.parseInt(br.readLine());
		System.out.println("Escribe un número de filas");
		int l=Integer.parseInt(br.readLine());
		System.out.println("Escribe la cantidad de semillas, debe de ser menor a: "+(c*l));
		int s=Integer.parseInt(br.readLine());
		
		while(s>(c*l)) {
			System.out.println("La cantidad de semillas es muy grande, ingresa otro valor");
			s=Integer.parseInt(br.readLine());
		}
		
		System.out.println("Escribe la cantidad de portale, debe ser menor a: "+((c*l)*0.5));
		int p=Integer.parseInt(br.readLine());
		
		while(p>((c*l)*0.5)) {
			System.out.println("La cantidad de portales es muy grande, ingresa otro valor");
			p=Integer.parseInt(br.readLine());
		}
		
		board= new Board(c,l,s,p);
		
		while()
	}
}
