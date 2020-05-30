import java.util.Random;
import java.util.Scanner;

public class jocdelavida {
	
	public Scanner teclat = new Scanner(System.in);
	public Random random = new Random();


	// Defineix el nombre de c�l�lules amb les que es vol jugar
	public final int NUMCELLULES = 5;


	// Aquest m�tode s'encarrega d'arrancar el programa, iniciar� el launch
	public static void main(String[] args) throws InterruptedException {
		jocdelavida launcher = new jocdelavida();
		launcher.launch();
	}


	// M�tode principal on es troba tot el programa
	public void launch() throws InterruptedException {

	    int[] midesTauler = demanarMidesTauler();
		int midaTaulerX = midesTauler[0];
		int midaTaulerY = midesTauler[1];

		int[] condicions = demanarCondicions();
		int sobreviureMin = condicions[0] - 48;
		int sobreviureMax = condicions[1] - 48;
		int reneixer = condicions[2] - 48;

        boolean[][] tauler = new boolean[midaTaulerY][midaTaulerX];

		imprimirEspais();

		tauler = menuIntroduccioCel_lules(tauler);

		imprimirEspais();

		jocPrincipal(tauler, sobreviureMin, sobreviureMax, reneixer);

	}


	// Permet demanar i tractar les mides del tauler, introduides per l'usuari
	public int[] demanarMidesTauler() {

	    System.out.println("Introdueix les mides que vols que tingui el tauler");

	    int x = 0;
	    int y = 0;
        boolean correcte = false;

	    do {

	        correcte = false;

	        System.out.print("Introdueix el nombre de files: ");
	        String files = teclat.nextLine();

	        System.out.print("Introdueix el nombre de columnes: ");
	        String columnes = teclat.nextLine();

			if (files.matches("\\d*") && columnes.matches("\\d*")) {
				y = Integer.parseInt(files);
				x = Integer.parseInt(columnes);
				if ((x >= 3) && (y >= 3) && (x * y > NUMCELLULES))
					correcte = true;
			}

	        if(!correcte)
	            System.out.println("Introdueix valors vàlids!");

        }
	    while(!correcte);

        int[] midesTauler = {x, y};

	    return(midesTauler);

    }


	// M�tode que s'encarrega de demanar les condicions de vida del joc
	public int[] demanarCondicions() {
		
		System.out.println("Introdueix les condicions de sobreviure i de reenaxeinça:");
		System.out.println("*El format és AA/B");
		
		String condicions = teclat.nextLine();
		
		int[] condicionsRecuperades = comprovarCondicions(condicions);
		
		return(condicionsRecuperades);
		
	}
	
	
	// Comprova que les condicions introduides s�n les correctes, sin� tornar� a demanar-les
	public int[] comprovarCondicions(String condicions) {
		
		boolean correcte = false;
		
		if(condicions.length() == 4)
		{
			int condicionsRecuperades[] = {(int) condicions.charAt(0), (int) condicions.charAt(1), (int) condicions.charAt(3)};
			
			for(int i = 0; i < condicionsRecuperades.length; i++) {
				
				if(condicionsRecuperades[i] >= 48 && condicionsRecuperades[i] <= 57)
					correcte = true;
				else {
					correcte = false;
					i = condicionsRecuperades.length;
				}
			}

			if(correcte && condicionsRecuperades[0] > condicionsRecuperades[1])
			    correcte = false;
		}
		
		if(!correcte)
			demanarCondicions();

		int[] condicionsRecuperades = {(int) condicions.charAt(0), (int) condicions.charAt(1), (int) condicions.charAt(3)};
		return(condicionsRecuperades);
	}


	// Mostra el men� per decidir com ser� la introducci� de les c�l�lules i demana a l'usuari que esculli com vol fer-ho.

	public boolean[][] menuIntroduccioCel_lules(boolean[][] tauler) {

		char decisio = ' ';

		do {
			System.out.println("Escull com vols que s'introdueixin les c�l�lules:");
			System.out.println("1 - Introducci� manual");
			System.out.println("2 - Introducci� autom�tica");
			decisio = teclat.nextLine().charAt(0);
		}
		while(decisio != '1' && decisio != '2');

		switch(decisio) {
            case '1': tauler = introduccioCel_lulesManualment(tauler);
            break;

            case '2': tauler = introduccioCel_lulesAutomaticament(tauler);
            break;
        }

        return(tauler);
	}


	// Permet la introduccio de c�l�lules manualment
	public boolean[][] introduccioCel_lulesManualment(boolean[][] tauler) {
		for(int i = 0; i < NUMCELLULES; i++) {
			imprimirEspais();
			mostrarTaulerUsuari(tauler);
			tauler = comprovarIntroduccioCel_lula(tauler);
		}
		return(tauler);
	}


	// Valida que la c�l�lula introduida es troba dins de les coordenades correctes
	public boolean[][] comprovarIntroduccioCel_lula(boolean[][] tauler) {

		System.out.println("Introdueix les coordenades d'on vols que hi hagi una c�l�lula viva");
		System.out.println("Recoda que el tauler �s de: " + tauler[0].length + "/" + tauler.length);

		boolean correcte = false;
		int x = 0;
		int y = 0;

		do {

			do {

				correcte = false;

				System.out.println("Introdueix la fila:");
				String coordenada = teclat.nextLine();

				if(coordenada.matches("\\d*")) {
					y = (Integer.parseInt(coordenada)) - 1;

					if(y >= 0 && y < tauler[0].length)
						correcte = true;
				}

				if(!correcte)
					System.out.println("Introdueix una fila v�lida!");

			}
			while(!correcte);

			do {

				correcte = false;

				System.out.println("Introdueix la columna:");
				String coordenada = teclat.nextLine();

				if(coordenada.matches("\\d*")) {
					x = (Integer.parseInt(coordenada)) - 1;

					if(x >= 0 && x < tauler.length)
						correcte = true;
				}

				if(!correcte)
					System.out.println("Introdueix una columna v�lida!");

			}
			while(!correcte);

			if(tauler[y][x])
				System.out.println("Aquesta posici� ja ha estat escollida!");
		}
		while (tauler[y][x]);

		tauler[y][x] = true;

		return (tauler);
	}


	// Introdueix les c�l�lules de manera autom�tica
	public boolean[][]  introduccioCel_lulesAutomaticament(boolean[][] tauler) {

		System.out.println("Quantes colònies de cèl·lules vols crear?");
		System.out.println("*Recorda que el tauler és de " + tauler[0].length + "/" + tauler.length + "*");
		int numColonies = 0;

		boolean correcte = false;

		do {

			correcte = false;

			String introduccio = teclat.nextLine();

			if(introduccio.matches("\\d*")) {
				numColonies = Integer.parseInt(introduccio);
				if(numColonies > 0)
					correcte = true;
			}

			if(!correcte)
				System.out.println("Introdueix valors vàlids!");

		}
		while(!correcte);

		for(int i = 0; i < numColonies; i++) {
			int x = (int) (Math.random() * (tauler.length - 2) + 1);
			int y = (int) (Math.random() * (tauler[0].length - 2) + 1);

			if(tauler[y][x])
				i--;
			else {
				tauler[y][x] = true;
				tauler = introduccioCel_lulesAutomaticament_Voltant(tauler, y, x);
			}

		}

		return(tauler);
	}


	public boolean[][] introduccioCel_lulesAutomaticament_Voltant(boolean[][] tauler, int y, int x) {

		for(int i = 0; i < NUMCELLULES; i++) {

            int posicio = (int) (Math.random() * 7);
            int coordenadaY = 0;
            int coordenadaX = 0;

            switch(posicio) {
                case 0: coordenadaY = y - 1;
                        coordenadaX =  x - 1;
                        break;

                case 1: coordenadaY = y - 1;
                    coordenadaX = x;
                    break;

                case 2: coordenadaY = y - 1;
                    coordenadaX = x + 1;
                    break;

                case 3: coordenadaY = y;
                    coordenadaX = x + 1;
                    break;

                case 4: coordenadaY = y + 1;
                    coordenadaX = x + 1;
                    break;

                case 5: coordenadaY = y + 1;
                    coordenadaX = x;
                    break;

                case 6: coordenadaY = y + 1;
                    coordenadaX = x - 1;
                    break;

                case 7: coordenadaY = y;
                    coordenadaX = x - 1;
                    break;
            }

			if(tauler[coordenadaY][coordenadaX])
				i--;

			tauler[coordenadaY][coordenadaX] = true;
		}

		return(tauler);
	}


	// Part principal del joc, s'encarrega d'anar mostrant l'evoluci� de cada generaci�
	public void jocPrincipal(boolean[][] tauler, int sobreviureMin, int sobreviureMax, int reneixer) throws InterruptedException {

	    int generacio = 0;

        do {
            imprimirEspais();
            if(generacio > 0)
            	tauler = evolucionarTauler(tauler, sobreviureMin, sobreviureMax, reneixer);
            mostrarTaulerUsuari(tauler);
            System.out.println();
            generacio ++;
            System.out.print("Generació: " + generacio);
            Thread.sleep(1000);
        }
        while(true);

    }


    public boolean[][] evolucionarTauler(boolean[][] taulerOriginal, int sobreviureMin, int sobreviureMax, int reneixer) {

	    boolean[][] taulerModificat = clonarTauler(taulerOriginal);

	    for(int y = 0; y < taulerModificat[0].length; y++) {
	        for(int x = 0; x < taulerModificat.length; x++) {

	            int cel_lulesAlVoltant = 0;

	            if(x == 0) {
                    if(y == 0)
                        cel_lulesAlVoltant = comprovarVoltant_SuperiorEsquerra(taulerOriginal, y, x);
                    else if(y == taulerModificat[0].length - 1)
                        cel_lulesAlVoltant = comprovarVoltant_InferiorEsquerra(taulerOriginal, y, x);
                    else
                        cel_lulesAlVoltant = comprovarVoltant_Esquerra(taulerOriginal, y, x);
                }
	            else if(x == taulerModificat.length - 1) {
                    if(y == 0)
                        cel_lulesAlVoltant = comprovarVoltant_SuperiorDreta(taulerOriginal, y, x);
                    else if(y == taulerModificat[0].length - 1)
                        cel_lulesAlVoltant = comprovarVoltant_InferiorDreta(taulerOriginal, y, x);
                    else
                        cel_lulesAlVoltant = comprovarVoltant_Dreta(taulerOriginal, y, x);
                }
	            else if(y == 0)
                    cel_lulesAlVoltant = comprovarVoltant_Superior(taulerOriginal, y, x);
	            else if(y == taulerModificat[0].length - 1)
	                cel_lulesAlVoltant = comprovarVoltant_Inferior(taulerOriginal, y, x);
	            else
	                cel_lulesAlVoltant = comprovarVoltant_Mig(taulerOriginal, y, x);

	            if(taulerOriginal[y][x] && !(cel_lulesAlVoltant >= sobreviureMin && cel_lulesAlVoltant <= sobreviureMax))
	                taulerModificat[y][x] = false;
                else if(!taulerOriginal[y][x] && cel_lulesAlVoltant == reneixer)
                    taulerModificat[y][x] = true;
            }
        }

        return(taulerModificat);

    }


    // Comprovar� els voltants de la cela que es troba al inferior dret
    public int comprovarVoltant_InferiorDreta(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY - 1; y <= coordenadaY; y++) {
            for(int x = coordenadaX - 1; x <= coordenadaX; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela que es troba al inferior esquerra
    public int comprovarVoltant_InferiorEsquerra(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY - 1; y <= coordenadaY; y++) {
            for(int x = coordenadaX; x <= coordenadaX + 1; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela que es troba al superior dret
    public int comprovarVoltant_SuperiorDreta(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY; y <= coordenadaY + 1; y++) {
            for(int x = coordenadaX - 1; x <= coordenadaX; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela que es troba al superior esquerra
    public int comprovarVoltant_SuperiorEsquerra(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY; y <= coordenadaY + 1; y++) {
            for(int x = coordenadaX; x <= coordenadaX + 1; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela dreta analitzada
    public int comprovarVoltant_Dreta(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY - 1; y <= coordenadaY + 1; y++) {
            for(int x = coordenadaX - 1; x <= coordenadaX; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela esquerra analitzada
    public int comprovarVoltant_Esquerra(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY - 1; y <= coordenadaY + 1; y++) {
            for(int x = coordenadaX; x <= coordenadaX + 1; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }



    // Comprovar� els voltants de la cela inferior analitzada
    public int comprovarVoltant_Inferior(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY - 1; y <= coordenadaY; y++) {
            for(int x = coordenadaX - 1; x <= coordenadaX; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    // Comprovar� els voltants de la cela superior analitzada
    public int comprovarVoltant_Superior(boolean[][] tauler, int coordenadaY, int coordenadaX) {

        int comptador = 0;

        for(int y = coordenadaY; y <= coordenadaY + 1; y++) {
            for(int x = coordenadaX - 1; x <= coordenadaX + 1; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }

    // Comprovar� els voltants de la cela que s'est� analitzant
    public int comprovarVoltant_Mig(boolean[][] tauler, int coordenadaY, int coordenadaX) {

	    int comptador = 0;

	    for(int y = coordenadaY - 1; y <= coordenadaY + 1; y++) {
	        for(int x = coordenadaX - 1; x <= coordenadaX + 1; x++) {
                if(tauler[y][x])
                    comptador++;
            }
        }

        if(tauler[coordenadaY][coordenadaX])
            comptador--;

        return(comptador);
    }


    public boolean[][] clonarTauler(boolean[][] origen) {

		boolean[][] desti = new boolean[origen[0].length][origen.length];

		for(int y = 0; y < desti[0].length; y++) {
			for(int x = 0; x < desti.length; x++) {
				desti[y][x] = origen[y][x];
			}
		}

		return(desti);
	}


	// Imprimeix espais en blanc per "netejar" la pantalla
	public void imprimirEspais() {

		for(int i = 0; i <= 30; i++)
			System.out.println();

	}


	// Mostra el tauler per pantalla per que l'usuari l'entengui
	public void mostrarTaulerUsuari(boolean[][] tauler) {

		for (int y = 0; y < tauler[0].length; y++) {
			for (int x = 0; x < tauler.length; x++) {
				if(tauler[y][x])
					System.out.print("| O ");
				else
					System.out.print("| X ");

				if(x == tauler.length - 1)
					System.out.print("|");
			}
			System.out.println();
		}
		System.out.println();
	}
}
