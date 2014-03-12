
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class plp1 {
	public static void main(String[] args) {
//		TraductorDR tdr;
		AnalizadorLexico al;
		RandomAccessFile entrada = null;
		if (args.length == 1)		{
			try {
				entrada = new RandomAccessFile(args[0],"r");
				al = new AnalizadorLexico(entrada);
				tdr = new TraductorDRPrev(al);
				String traduccion = tdr.Fun(); // simbolo inicial de la gramatica
				tdr.comprobarFinFichero();
				System.out.println(traduccion);
			}
			catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado: " + args[0]);
			}
		} else {
			System.out.println("Error, uso: java plp1 <nomfichero>");
		}
	}
}
