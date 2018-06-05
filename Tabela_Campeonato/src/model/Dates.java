package model;

import java.util.Random;

public class Dates {
	public String geraDataAleatoria() {
		int dia, mes;
		String sMes = null;
		String sDia = null;
		Random random = new Random();
		mes = random.nextInt(12) + 1;
		
		sMes = Integer.toString(mes);
		
		if (mes < 10) {
			sMes = "0" + sMes;			
		}
		if (mes == 2)
			dia = random.nextInt(28) + 1;
		else if (mes % 2 == 0)
			dia = random.nextInt(30) + 1;
		else
			dia = random.nextInt(31) + 1;
		
		sDia = Integer.toString(dia);
		
		if (dia < 10) {
			sDia = "0" + sDia;
		}
		
		return sMes + "/" + sDia + "/2018";
	}
}
