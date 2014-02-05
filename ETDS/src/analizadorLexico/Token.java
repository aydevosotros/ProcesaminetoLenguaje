package analizadorLexico;

public class Token {
	public int fila;
	public int columna;
	public String lexema;
	public int tipo;
	// tipo es: ID, ENTERO, REAL ...
	public static final int	
		PARI = 1,
		PARD = 2,
		COMA = 3,
		AMP = 4,
		LLAVEI = 5,
		LLAVED = 6,
		OPASIG = 7,
		PYC = 8,
		OPAS = 9,
		INT = 10,
		FLOAT = 11,
		IF = 12,
		ID = 13,
		NUMENTERO = 14,
		NUMREAL = 15;
		
	@Override
	public String toString(){
		String s = "";
		switch (tipo) {
		case 1:
			s="(";
			break;
		case 2:
			s=")";
			break;
		case 3:
			s=",";
			break;
		case 4:
			s="&";
			break;
		case 5:
			s="{";
			break;
		case 6:
			s="}";
			break;
		case 7:
			s=":=";
			break;
		case 8:
			s=";";
			break;
		case 9:
			s="+-";
			break;
		case 10:
			s="'int'";
			break;
		case 11:
			s="'float'";
			break;
		case 12:
			s="'if'";
			break;
		case 13:
			s="identificador";
			break;
		case 14:
			s="numero entero";
			break;
		case 15:
			s="numero real";
			break;
		}
		return s;
	}

}
