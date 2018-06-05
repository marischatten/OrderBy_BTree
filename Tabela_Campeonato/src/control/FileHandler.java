package control;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.Cities;
import model.Dates;
import model.Pagantes;
import model.Times;
import utils.LogHandler;
import view.Janela;

public class FileHandler {
	private Path p = Paths.get("./bancodeinformacoes.txt");
	private LogHandler log = new LogHandler();
	private OutputStream out;
	@SuppressWarnings("unused")
	private long totalSize;

	public FileHandler() {
		try {
			out = new BufferedOutputStream(Files.newOutputStream(p,
					StandardOpenOption.CREATE));
			log.log("Criando o arquivo de informacoes");
		} catch (IOException e) {
			log.log("Problema ao abrir o arquivo do banco de informacoes");
			e.printStackTrace();
		}
	}

	public void criarBancoDeDados(int numRegistros) throws IOException {
		Random r = new Random();
		int golsTimeCasa;
		int golsTimeFora;
		String[] times;
		String line = null;
		String[] categorias = { "Cidade", "Número de Pagantes", "Times",
				"Data", "Placar" };
		ArrayList<String> lineList = new ArrayList<String>();
		ArrayList<String> pagantes = new ArrayList<String>();
		ArrayList<String> datas = new ArrayList<String>();
		String category1 = categorias[0];
		String category2 = categorias[1];
		int cont = 0;
		Times t = new Times();
		Pagantes p = new Pagantes();
		Cities c = new Cities();
		Dates d = new Dates();

		byte[] streamOutput;
		log.log("Iniciando processo de geracao de informacoes no banco!");
		long start = System.currentTimeMillis();
		long totalBytes = 0;
		if (numRegistros == 0) {
			do {
				golsTimeCasa = r.nextInt(5);
				golsTimeFora = r.nextInt(5);
				times = t.getTimeAleatorio();

				pagantes.add(p.geraPagantesAleatorio());
				datas.add(d.geraDataAleatoria());

				switch (category1) {
				case "Cidade":
					line = "Cidade: " + c.geraCidadeAleatoria();
					break;
				case "Número de Pagantes":
					line = "Publico Pagante: " + pagantes.get(cont);
					break;
				case "Times":
					line = "Times: " + times[0] + " x " + times[1];
					break;
				case "Data":
					line = "Data: " + datas.get(cont);
					break;
				case "Placar":
					line = "Placar (" + golsTimeCasa + ") x (" + golsTimeFora
							+ ")";
				}

				cont++;

				pagantes.add(p.geraPagantesAleatorio());
				datas.add(d.geraDataAleatoria());

				if (category1 == category2) {
					for (int i = 0; i < categorias.length; i++) {
						if (categorias[i] != category1) {
							category2 = categorias[i];
						}
					}
				}

				switch (category2) {

				case "Cidade":
					line = line + " | Cidade: " + c.geraCidadeAleatoria();
					break;
				case "Número de Pagantes":
					line = line + " | Publico Pagante: " + pagantes.get(cont);
					break;
				case "Times":
					line = line + " | Times: " + times[0] + " x " + times[1];
					break;
				case "Data":
					line = line + " | Data: " + datas.get(cont);
					break;
				case "Placar":
					line = line + " | Placar (" + golsTimeCasa + ") x ("
							+ golsTimeFora + ")";
				}

				pagantes.add(p.geraPagantesAleatorio());
				datas.add(d.geraDataAleatoria());

				cont++;

				for (int i = 0; i < categorias.length; i++) {
					pagantes.add(p.geraPagantesAleatorio());
					datas.add(d.geraDataAleatoria());
					if (categorias[i] != category1
							&& categorias[i] != category2) {
						if (categorias[i] == "Cidade")
							line = line + " | Cidade: "
									+ c.geraCidadeAleatoria() + " |";
						if (categorias[i] == "Número de Pagantes")
							line = line + " | Publico Pagante: "
									+ pagantes.get(cont - 1);
						if (categorias[i] == "Times")
							line = line + " | Times: " + times[0] + " x "
									+ times[1];
						if (categorias[i] == "Data")
							line = line + " | Data: " + datas.get(cont - 1);
						if (categorias[i] == "Placar")
							line = line + " | Placar (" + golsTimeCasa
									+ ") x (" + golsTimeFora + ") |";
						cont++;
					}
				}

				lineList.add(line + "*");

				for (String outputLine : lineList) {
					streamOutput = outputLine.getBytes();
					out.write(streamOutput, 0, streamOutput.length);
					totalBytes = totalBytes + streamOutput.length;
					if ((start - System.currentTimeMillis()) % 3000 == 0) {
						log.log("Escrevendo o arquivo... Total parcial: "
								+ totalBytes / 1000000 + "Mb!");
						Janela.textArea.update(Janela.textArea.getGraphics());
						Janela.scrollPane.revalidate();
						Janela.scrollPane.repaint();
					}
				}
			} while (totalBytes < 1073741824);
		} else {
			for (int i = 0; i < numRegistros; i++) {
				golsTimeCasa = r.nextInt(5);
				golsTimeFora = r.nextInt(5);
				times = t.getTimeAleatorio();
				line = "Times: " + times[0] + " x " + times[1] + " | Data: "
						+ d.geraDataAleatoria() + " | Placar (" + golsTimeCasa
						+ ") x (" + golsTimeFora + ")" + " | Publico Pagante: "
						+ p.geraPagantesAleatorio() + " | Cidade: "
						+ c.geraCidadeAleatoria() + " |*\r\n";
				lineList.add(line);
				streamOutput = line.getBytes();
				out.write(streamOutput, 0, streamOutput.length);
				totalBytes = totalBytes + streamOutput.length;
				if ((start - System.currentTimeMillis()) % 3000 == 0) {
					log.log("Escrevendo o arquivo... Total parcial: "
							+ totalBytes / 1000000 + "Mb!");
					Janela.textArea.update(Janela.textArea.getGraphics());
					Janela.scrollPane.revalidate();
					Janela.scrollPane.repaint();
				}
			}
		}

		this.totalSize = totalBytes;
		Long conclusao = (System.currentTimeMillis() - start) / 1000;
		log.log("Finalizado processo de geracao de informacoes no banco. Foi escrito: "
				+ totalBytes
				/ 1000000
				+ "Mb. Tempo de excucao: "
				+ conclusao
				+ "s.");

	}

	public String recuperarBancoDeDados(int numRegistros) {
		long start = System.currentTimeMillis();
		log.log("Iniciando processo de recuperacao de informacoes no banco de dados!");
		String line = "";

		/**
		 * Tenta localizar o arquivo original dos jogos
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(
				"./bancodeinformacoes.txt"))) {
			if (numRegistros == 0) {
				do {
					line = br.readLine();
					if (line != null) {
						log.log(line);
					}
				} while (line != null);
			} else {
				for (int i = 0; i < numRegistros; i++) {
					line = br.readLine();
					if (line != null) {
						log.log(line);
					}
				}
			}

		} catch (FileNotFoundException e) {
			log.log(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.log(e.toString());
			e.printStackTrace();
		}
		Long conclusao = (System.currentTimeMillis() - start) / 1000;
		log.log("Finalizado processo de recuperacao de informacoes no banco. Tempo de execucao: "
				+ conclusao + "s.");
		return conclusao.toString();
	}

	@SuppressWarnings("unused")
	public void exportarOrdenacao(String category1, String category2)
			throws IOException {
		String line = null;
		String linePart = null;
		int filePart = 0;
		int cont = 0;
		int linesQuant = 0;
		int initString = 0;
		int endString = 0;
		byte[] streamOutput;
		long totalBytes = 0;
		long start = 0;

		String[] categorias = { "Cidade", "Número de Pagantes", "Times",
				"Data", "Placar" };

		ArrayList<String> lineList = new ArrayList<String>();
		ArrayList<String> newList = new ArrayList<String>();

		OutputStream order;

		/**
		 * Le o arquivo original (desordenado)
		 */
		Path filePath = Paths.get("./bancodeinformacoes.txt");
		FileChannel bancoInformacoes = FileChannel.open(filePath);
		long fileSize = bancoInformacoes.size();

		/**
		 * Informações sobre o arquivo ordenado que será gerado
		 */
		String pathString = "./bancodeinformacoes_ordenado.txt";
		Path p2 = Paths.get(pathString);
		order = new BufferedOutputStream(Files.newOutputStream(p2,
				StandardOpenOption.CREATE));

		do {

			start = System.currentTimeMillis();

			/**
			 * Le o arquivo por partes localizar o arquivo original dos jogos
			 */
			filePart = (int) (fileSize / 10);
			try (BufferedReader br = new BufferedReader(new FileReader(
					"./bancodeinformacoes.txt"))) {
				do {
					line = br.readLine();
					lineList.add(line);
					linesQuant++;
				} while (line != null);
			} catch (FileNotFoundException e) {
				log.log(e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				log.log(e.toString());
				e.printStackTrace();
			}

			/**
			 * Define a linha como vazia para o começo da ordenação
			 */

			line = "";

			/**
			 * Verifica as categorias de ordenação que o usuário optou
			 */
			for (int i = 0; i <= ((linesQuant - 1) - 2); i++) {

				/**
				 * Busca trecho da linha entre o indice do primeiro caracter da
				 * variavel initString ate o primeiro da variavel linePart e
				 * coloca o trecho na nova linha q sera ordenada posteriormente
				 */
				switch (category1) {
				case "Cidade":
					initString = lineList.get(i).indexOf("Cidade:");
					endString = lineList.get(i).lastIndexOf("*");
					linePart = lineList.get(i).substring(initString,
							endString + 1);
					line = line + linePart;
					break;
				case "Número de Pagantes":
					initString = lineList.get(i).indexOf(" Publico Pagante:");
					endString = lineList.get(i).lastIndexOf(" | Cidade");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart;
					break;
				case "Times":
					initString = lineList.get(i).indexOf("Times:");
					endString = lineList.get(i).lastIndexOf(" | Data:");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart;
					break;
				case "Data":
					initString = lineList.get(i).indexOf("Data:");
					endString = lineList.get(i).lastIndexOf(" | Placar");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart;
					break;
				case "Placar":
					initString = lineList.get(i).indexOf("Placar");
					endString = lineList.get(i).lastIndexOf(" | Publico");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart;
					break;
				}

				/**
				 * Busca trecho da linha entre o indice do primeiro caracter da
				 * variavel initString ate o primeiro da variavel linePart e
				 * coloca o trecho na nova linha q sera ordenada posteriormente
				 */

				if (category1 == category2) {
					for (int i1 = 0; i1 < categorias.length; i1++) {
						if (categorias[i1] != category1) {
							category2 = categorias[i1];
						}
					}
				}

				switch (category2) {
				case "Cidade":
					initString = lineList.get(i).indexOf(" Cidade:");
					endString = lineList.get(i).indexOf("*");
					linePart = lineList.get(i).substring(initString, endString);
					line = line + linePart;
					break;
				case "Número de Pagantes":
					initString = lineList.get(i).indexOf(" Publico Pagante:");
					endString = lineList.get(i).indexOf("| Cidade");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart + " ";
					break;
				case "Times":
					initString = lineList.get(i).indexOf("Times:");
					endString = lineList.get(i).indexOf(" | Data");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + " " + linePart + " ";
					break;
				case "Data":
					initString = lineList.get(i).indexOf(" Data:");
					endString = lineList.get(i).indexOf("| Placar");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart + " ";
					break;
				case "Placar":
					initString = lineList.get(i).indexOf(" Placar");
					endString = lineList.get(i).lastIndexOf("| Publico");
					linePart = lineList.get(i).substring(initString,
							endString + 2);
					line = line + linePart + " ";
					break;
				}

				/**
				 * Insere o restante do conteudo da partida. Busca trecho da
				 * linha entre o indice do primeiro caracter da variavel
				 * initString ate o primeiro da variavel linePart e coloca o
				 * trecho na nova linha Linha q sera ordenada posteriormente
				 */
				for (int i1 = 0; i1 < categorias.length; i1++) {
					if (categorias[i1] != category1
							&& categorias[i1] != category2) {
						if (categorias[i1] == "Cidade") {
							initString = lineList.get(i1).indexOf(" Cidade:");
							endString = lineList.get(i1).indexOf("*");
							linePart = lineList.get(i1).substring(initString,
									endString + 1);
							line = line + linePart + " ";
						}
						if (categorias[i1] == "Número de Pagantes") {
							initString = lineList.get(i1).indexOf(
									" Publico Pagante:");
							endString = lineList.get(i1).indexOf(" | Cidade");
							linePart = lineList.get(i1).substring(
									initString + 1, endString + 2);
							line = line + linePart + " ";
						}
						if (categorias[i1] == "Times") {
							initString = lineList.get(i1).indexOf("Times:");
							endString = lineList.get(i1).lastIndexOf(" | Data");
							linePart = lineList.get(i1).substring(initString,
									endString + 2);
							line = line + linePart + " ";
						}
						if (categorias[i1] == "Data") {
							initString = lineList.get(i).indexOf("Data:");
							endString = lineList.get(i)
									.lastIndexOf(" | Placar");
							linePart = lineList.get(i).substring(initString,
									endString + 2);
							line = line + linePart + " ";
						}
						if (categorias[i1] == "Placar") {
							initString = lineList.get(i).indexOf("Placar");
							endString = lineList.get(i).lastIndexOf(
									" | Publico");
							linePart = lineList.get(i).substring(initString,
									endString + 2);
							line = line + linePart + " ";
						}
					}

				}

				/**
				 * Faz com que tenha uma quebra da linha depois de cada registro
				 */
				line = line + "\n";

				/**
				 * Adiciona a linha na ArrayList dos registros
				 */
				newList.add(line);

				cont++;

				/**
				 * Zera o conteudo da linha para que o proximo registro comece
				 * vazio e se preencha
				 */
				line = "";

			}

		} while (cont < ((linesQuant - 1) - 2));

		/**
		 * Sort que realiza a ordenação dos registros
		 */
		Collections.sort(newList);

		/**
		 * Escreve as linhas ordenadas no arquivo
		 */
		for (String outputLine : newList) {
			log.log(outputLine);
			streamOutput = outputLine.getBytes();
			order.write(streamOutput, 0, streamOutput.length);
			totalBytes = totalBytes + streamOutput.length;
			if ((start - System.currentTimeMillis()) % 3000 == 0) {
				log.log("Escrevendo o arquivo... Total parcial: " + totalBytes
						/ 1000000 + "Mb!");
				Janela.textArea.update(Janela.textArea.getGraphics());
				Janela.scrollPane.revalidate();
				Janela.scrollPane.repaint();
			}
		}

		Long conclusao = (System.currentTimeMillis() - start) / 1000;
		log.log("Finalizado processo de geracao de informacoes no banco. Tempo de execucao: "
				+ conclusao + "s.");
	}

	public void ordenarArvore() {

		Arvore tree = new Arvore();

		byte[] streamOutput;
		log.log("Iniciando processo de geracao de informacoes no banco!");
		long start = System.currentTimeMillis();
		long totalBytes = 0;
		String pathString = "./arquivoordenado_arvore.txt";

		BufferedWriter wr = null;
		try {
			wr = new BufferedWriter(new FileWriter(pathString));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		;

		String line = null;
		int linesQuant = 0;
		String informacao;
		int info;

		try (BufferedReader br = new BufferedReader(new FileReader(
				"./bancodeinformacoes.txt"))) {
			do {
				line = br.readLine();
				linesQuant++;
				informacao = line.substring(
						(line.indexOf("Publico Pagante:") + 17),
						line.lastIndexOf(" | Cidade"));
				info = Integer.parseInt(informacao);

				tree.inserir(linesQuant, info, line);

			} while (line != null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
		}

		tree.emordem(tree.raiz);

		ArrayList<String> list = tree.lineList;
		OutputStream order = null;

		/**
		 * Escreve as linhas ordenadas no arquivo
		 */
		for (String outputLine : list) {
			log.log(outputLine);
			streamOutput = outputLine.getBytes();
			try {
				wr.write(outputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
