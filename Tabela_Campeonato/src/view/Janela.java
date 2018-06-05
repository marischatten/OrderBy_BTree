package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import control.FileHandler;

public class Janela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JPanel contentPane;
	private JTextField textField;
	public static JTextArea textArea = new JTextArea();
	public static JScrollPane scrollPane = new JScrollPane(textArea);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Janela frame = new Janela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Janela() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Janela.class.getResource("/image/02_Soccer-512.png")));
		setTitle("Times de Futebol - Avaliação 02");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1039, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		textArea.setEditable(false);
		textArea.setBounds(20, 56, 993, 351);

		JButton btnRecuperar = new JButton("Ler Jogos");
		btnRecuperar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("");
				FileHandler fileHandler = new FileHandler();
				String t = textField.getText();
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (t.equals("") || t.equals("0"))
					fileHandler.recuperarBancoDeDados(500000);
				else
					fileHandler.recuperarBancoDeDados(Integer.parseInt(t));
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnRecuperar.setIcon(new ImageIcon(Janela.class
				.getResource("/image/usb-memory.png")));
		btnRecuperar.setBounds(162, 11, 109, 23);
		contentPane.add(btnRecuperar);

		JButton btnGerar = new JButton("Criar Jogos");
		btnGerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("");
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				FileHandler fileHandler = new FileHandler();
				try {
					String t = textField.getText();
					if (t.equals("") || t.equals("0")) {
						fileHandler.criarBancoDeDados(10000);
					} else
						fileHandler.criarBancoDeDados(Integer
								.parseInt(textField.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnGerar.setIcon(new ImageIcon(Janela.class
				.getResource("/image/hard-disk.png")));
		btnGerar.setBounds(20, 11, 132, 23);
		contentPane.add(btnGerar);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 45, 793, 2);
		contentPane.add(separator);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setToolTipText("Entre com o numero de registros");
		textField.setBounds(423, 12, 69, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Quantidade de Registros: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(281, 15, 132, 14);

		JLabel lblNewLabel_2 = new JLabel("Exportar por: ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(640, 15, 132, 14);

		String[] categoriesList = { "Cidade", "Número de Pagantes", "Times",
				"Placar", "Data" };
		String[] categoriesList2 = { "Número de Pagantes", "Times", "Placar",
				"Data", "Cidade" };
		final JComboBox export_1 = new JComboBox(categoriesList);
		// petList.setHorizontalAlignment(SwingConstants.RIGHT);
		export_1.setBounds(780, 07, 163, 14);
		final JComboBox export_2 = new JComboBox(categoriesList2);
		// petList.setHorizontalAlignment(SwingConstants.RIGHT);
		export_2.setBounds(780, 27, 163, 14);

		JButton btnExportar = new JButton();
		btnExportar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String category1 = (String) export_1.getSelectedItem();
				String category2 = (String) export_2.getSelectedItem();

				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				FileHandler fileHandler = new FileHandler();
				try {
					fileHandler.exportarOrdenacao(category1, category2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		JButton btnOrderTree = new JButton("Ordenar por Árvore");
		btnOrderTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				FileHandler fileHandler = new FileHandler();
				fileHandler.ordenarArvore();
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		btnExportar.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Janela.class.getResource("/image/export.png"))));
		btnExportar.setBounds(960, 7, 50, 35);
		contentPane.add(btnExportar);

		btnOrderTree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				Janela.class.getResource("/image/export.png"))));
		btnOrderTree.setBounds(510, 7, 170, 35);
		contentPane.add(btnOrderTree);

		contentPane.add(lblNewLabel_1);
		contentPane.add(lblNewLabel_2);
		contentPane.add(export_1);
		contentPane.add(export_2);
		scrollPane.setAutoscrolls(true);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 58, 993, 349);
		contentPane.add(scrollPane);

	}
}
