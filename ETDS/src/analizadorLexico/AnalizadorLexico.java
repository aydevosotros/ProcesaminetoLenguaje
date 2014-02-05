package analizadorLexico;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AnalizadorLexico {
	private static final char EOF = '$';
	private RandomAccessFile fichero;
	
	public AnalizadorLexico(RandomAccessFile fichero){
		this.fichero = fichero;
	}
	
	public Token siguienteToken(){
		return new Token();
	}

	private char leerCaracter()	{
		char currentChar;
		try {
			currentChar = (char)fichero.readByte();
			return currentChar;
		}
		catch (EOFException e) {
			return EOF; // constante estática de la clase
		}
		catch (IOException e) { // error lectura
		}
		return ' ';
	}
}
