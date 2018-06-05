package model;

import java.util.Random;

public class Pagantes {
	public String geraPagantesAleatorio() {
		int num = 0;
		String finalNum = "";
		Random rand = new Random();
		num = rand.nextInt(50000) + 5000;
		if (num < 10000) {
			finalNum = "0" + Integer.toString(num);
		} else {
			finalNum = Integer.toString(num);
		}
		return finalNum;
	}
}
