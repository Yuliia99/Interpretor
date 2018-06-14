package interpretor.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.*;

public class InterpretorInterface extends JFrame {
	static Done done = new InterpretorInterface().new Done();
	static JButton bExecute = new JButton("Execute");
	static JTextArea tExecute = new JTextArea();
	static JTextArea tRead = new JTextArea();
	static JScrollPane scrollRead = new JScrollPane(tRead);
	static JScrollPane scrollExecute = new JScrollPane(tExecute);
	static Map<String, String> commands = new TreeMap<String, String>();
	static Map<String, String> variable = new TreeMap<String, String>();

	private static List<String> listOfArguments = new ArrayList<>();
	private static ArrayList<String> list = new ArrayList<String>();

	public InterpretorInterface() {
		super("Yulia");
	}

	private class Done implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			commands.put("commandDeleteFile", "deleteFile");
			commands.put("commandFindFileByMask", "findFileByMask");
			commands.put("commandFindSameFile", "findSameFile");
			commands.put("commandShowDirectory", "showDirectory");
			commands.put("commandAddToBackup", "addToBackup");
			commands.put("commandGetFromBackup", "getFromBackup");
			commands.put("commandCopyFile", "copyFile");
			commands.put("commandIf", "if");
			commands.put("commandWhile", "while");
			String text = null;
			text = tRead.getText();
			listOfArguments = Arrays.asList(text.split("\n"));
			for (String i : listOfArguments) {
				String[] mas = i.split(" ");
				FileSystem fileSystem = new FileSystem();
				if (!commands.containsValue(mas[0])) {
					variable.put(mas[0], mas[1]);
				} else if (mas[0].equals(commands.get("commandDeleteFile"))) {
					if (!variable.containsKey(mas[1]))
						tExecute.append("The variable " + mas[1] + " was not declared!\n");
					else
						fileSystem.deleteFile(variable.get(mas[1]));
				}

				else if (mas[0].equals(commands.get("commandFindfileSystemystemByMask"))) {
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else if (!variable.containsKey(mas[2]))
							tExecute.append("The variable " + mas[2] + " was not declared!\n");
						else {
							tExecute.append("Files found in directory " + variable.get(mas[1]) + " with mask "
									+ variable.get(mas[2]) + " :\n");
							fileSystem.findFilesByMask(variable.get(mas[1]), variable.get(mas[2]), list);
						}
						for (String s : list)
							tExecute.append(s);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else if (mas[0].equals(commands.get("commandShowDirectory"))) {
					if (!variable.containsKey(mas[1]))
						tExecute.append("The variable " + mas[1] + " was not declared!\n");
					else {
						tExecute.append("Files and\\or folders in the directory " + variable.get(mas[1]) + " :\n");
						fileSystem.showDirectory(variable.get(mas[1]), list);
					}
					for (String s : list)
						tExecute.append(s);
				} else if (mas[0].equals(commands.get("commandAddToBackup")))
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else
							fileSystem.addToBackup(variable.get(mas[1]));
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				else if (mas[0].equals(commands.get("commandGetFromBackup")))
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else
							fileSystem.getFromBackup(variable.get(mas[1]));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				else if (mas[0].equals(commands.get("commandCopyFile")))
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else if (!variable.containsKey(mas[2]))
							tExecute.append("The variable " + mas[2] + " was not declared!\n");
						else
							fileSystem.copyFile(variable.get(mas[1]), variable.get(mas[2]));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				else if (mas[0].equals(commands.get("commandFindSameFile"))) {
					if (!variable.containsKey(mas[1]))
						tExecute.append("The variable " + mas[1] + " was not declared!\n");
					else if (!variable.containsKey(mas[2]))
						tExecute.append("The variable " + mas[2] + " was not declared!\n");
					else {
						tExecute.append("Files " + variable.get(mas[2]) + " found in directory and its subdirectories "
								+ variable.get(mas[1]) + " :\n");
						fileSystem.findSameFile(new File(variable.get(mas[1])), variable.get(mas[2]));
					}
				} else if (mas[0].equals(commands.get("commandIf")))
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else
							fileSystem.ifConstruction(mas[0], variable.get(mas[1]), mas[2], mas[3]);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				else if (mas[0].equals(commands.get("commandWhile")))
					try {
						if (!variable.containsKey(mas[1]))
							tExecute.append("The variable " + mas[1] + " was not declared!\n");
						else
							fileSystem.whileConstruction(mas[0], variable.get(mas[1]), mas[2], mas[3]);

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

				else
					tExecute.append("Not exist command!\n");
			}
		}

	}

	public static void main(String[] args) throws IOException {
		InterpretorInterface interpretorInterface = new InterpretorInterface();
		interpretorInterface.setLayout(null);
		InterpretorInterface.scrollRead.setBounds(25, 20, 220, 300);
		InterpretorInterface.scrollExecute.setBounds(280, 20, 380, 300);
		InterpretorInterface.bExecute.setBounds(25, 360, 150, 60);
		interpretorInterface.setName("Yulia");
		interpretorInterface.add(bExecute);
		interpretorInterface.add(scrollExecute);
		interpretorInterface.add(scrollRead);
		bExecute.addActionListener(done);
		interpretorInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		interpretorInterface.setVisible(true);
		interpretorInterface.setSize(700, 500);
		interpretorInterface.setResizable(false);
		interpretorInterface.setLocationRelativeTo(null);

	}

}
