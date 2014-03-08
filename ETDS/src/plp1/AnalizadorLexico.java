
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class AnalizadorLexico {
	private static final char EOF = '$';
	private RandomAccessFile fichero;
	private int fila;
	private int columna;
	private String lexema;
	private Token currentToken;
	private LinkedList<Character> buffer;
	
	public AnalizadorLexico(RandomAccessFile fichero){
		this.fichero = fichero;
		this.fila = 1; // Inicializo fila y columna
		this.columna = 1;
		buffer = new LinkedList<>();
	}
	
	public Token siguienteToken(){
		Token token = new Token();
		this.lexema = "";
		token.fila = this.fila;
		token.columna = this.columna;
		char input = ' ';
		int estado = 1; // Inicializo en el estado 1
		
		while(estado > 0){
			// Obtengo el siguiente caracter de entrada
			input = leerCaracter();
			// Construyo el lexema con el input quitando carácteres especiales
			if (input != ' ' && input != '\t' && input != '\n' && buffer.isEmpty())
				lexema += input;
			// Desplazo los punteros
			if(estado == 1){
				token.fila = this.fila;
				if(buffer.size() != 0)
					token.columna -=1;
				else token.columna = this.columna;
			}
			// Obtengo el siguiente estado
			estado = delta(estado, input);
			// Trato de forma especial los casos: if, int, float
		}
		if(estado == 13){
			if(lexema.contentEquals("if")) estado = 12;
			if(lexema.contentEquals("int")) estado = 10;
			if(lexema.contentEquals("float")) estado = 11;	
		}
		// Asigno el lexema y el estado al token y lo devuelvo
		token.tipo = estado*-1;
		token.lexema = this.lexema;
		this.currentToken = token; // Copio el token actual en caso de error
		return token;
	}

	private char leerCaracter()	{
		char currentChar;
		try {
			if(this.buffer.isEmpty()){
				currentChar = (char)fichero.readByte();
				if(currentChar == '\n'){
					this.fila++;
					this.columna = 1;
				} else this.columna++;
			} else currentChar = this.buffer.poll();
			return currentChar;
		}
		catch (EOFException e) {
			return EOF; // constante estática de la clase
		}
		catch (IOException e) { // error lectura
		}
		return ' ';
	}
	
	/**
	 * Función de transición. 
	 * Explicar lo de los números negativos
	 * @param estado Estado actual
	 * @param c Caracter actual
	 * @return Estado de transición
	 */
	private int delta(int estado, int c) {
		switch (estado) {
		case 1:
			if (c == ' ' || c == '\t' || c == '\n')
				return 1;
			else if (c == '(')
				return -1;
			else if (c == ')')
				return -2;
			else if (c == ',')
				return -3;
			else if (c == '&')
				return -4;
			else if (c == '{')
				return -5;
			else if (c == '}')
				return -6;
			else if (c == ':')
				return 2;
			else if (c == ';')
				return -8;
			else if (c == '+' || c == '-')
				return -9;
			else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				return 3;
			else if (c >= '0' && c <= '9')
				return 4;
			else if (c == EOF){
				return -16;
			}
			else{
				System.err.println("Error lexico (" + (this.fila) + "," + (this.columna-1-(buffer.size())) + "): caracter '" + (char)c + "' incorrecto");
				System.exit(-1);
			}
		case 2:
			if (c == '=')
				return -7;
			else{
				System.err.println("Error lexico (" + (this.currentToken.fila) + "," + (this.currentToken.columna) + "): caracter ':' incorrecto");
				System.exit(-1);
			}
		case 3:
			if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))
				return 3;
			else
				buffer.add((char) c);
				return -13;
		case 4:
			if (c >= '0' && c <= '9')
				return 4;
			else if (c == '.')
				return 5;
			else
				buffer.add((char) c);
				return -14;
				
		case 5:
			if (c >= '0' && c <= '9')
				return 6;
			else{
				lexema = lexema.substring(0, lexema.length() - 1);
				buffer.add('.');
				buffer.add((char)c);
				return -14;
			}
		case 6:
			if (c >= '0' && c <= '9')
				return 6;
			else
				buffer.add((char)c);
				return -15;
		default: /* error fatal */
		}
		return 0;
	}
}
