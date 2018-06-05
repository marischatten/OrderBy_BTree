package testing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Testing {
	
	public static void main(String[] args){
		
		Arvore tree = new Arvore();
		
		String line = null;
		int linesQuant = 0;
		String  informacao;
		int info;
		
		try (BufferedReader br = new BufferedReader(new FileReader("./bancodeinformacoes.txt"))) {
			do {
				line = br.readLine();
				linesQuant++;
				//System.out.println(line);
				/*
				int begin;
				int end;
				String saida;
				String check1 = "Publico Pagante:";
				String check2 = " | Cidade";
				begin=line.indexOf(check1);
				System.out.println(begin);
				end=line.lastIndexOf(check2);
				saida=line.substring(begin+17, end);
				System.out.println("TESTE"+saida);
				*/
				
				informacao= line.substring((line.indexOf("Publico Pagante:")+17),line.lastIndexOf(" | Cidade"));
				//System.out.println("informacao:"+informacao);
				info = Integer.parseInt(informacao);
				tree.inserir(linesQuant, info,line);
				
			} while (line != null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			//e.printStackTrace();
		}
		//teste
		
		/*
		tree.inserir(1,12 ,line);
		tree.inserir(2,10 ,line);
		tree.inserir(3,8 ,line);
		tree.inserir(4,25 ,line);
		tree.inserir(5,13 ,line);
		tree.inserir(6,39 ,line);
		tree.inserir(7,42 ,line);
		tree.inserir(8,75 ,line);
		tree.inserir(9,4 ,line);
		tree.inserir(10,13 ,line);
		*/
		tree.emordem(tree.raiz);
		
	
		


	}
}
