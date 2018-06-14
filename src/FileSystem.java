package interpretor.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {

	public void deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.delete()) {
			if (file.isDirectory())
				InterpretorInterface.tExecute.append("Folder " + fileName + " is deleted.\n");
			else
				InterpretorInterface.tExecute.append("File " + fileName + " is deleted.\n");
		} else {
			if (file.isDirectory())
				InterpretorInterface.tExecute
						.append("Folder " + fileName + " is not detected or can not be deleted.\n");
			else
				InterpretorInterface.tExecute.append("File " + fileName + " is not detected or can not be deleted.\n");
		}
	}

	public void showDirectory(String directoryName, ArrayList<String> list) {
		File dir = new File(directoryName);
		File[] files = dir.listFiles();
		if (dir.exists()) {
			if (dir.isDirectory()) {
				if (files.length != 0)
					for (int i = 0; i < files.length; i++) {

						list.add(files[i].getName() + "\n");
					}
				else
					InterpretorInterface.tExecute.append("Directory " + directoryName + " is empty!\n");
			} else
				InterpretorInterface.tExecute.append("Directory " + directoryName + " is not a directory!\n");
		} else
			InterpretorInterface.tExecute.append("Directory " + directoryName + " does not exist!\n");
	}

	public void copyFile(String sourceFileName, String destFileName) throws IOException {
		File sourceFile = new File(sourceFileName);
		File destFile = new File(destFileName);
		if (!sourceFile.exists())
			throw new FileNotFoundException("File " + sourceFileName + " does not exist!\n");
		else

			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		InterpretorInterface.tExecute.append("File " + sourceFileName + " was copied into file " + destFileName + ".");
	}

	public void ifConstruction(String condition, String fileName, String action, String command) throws IOException {
		File file = new File(fileName);
		if (condition.equals("if")) {
			if (action.equals("!exists()")) {
				if (command.equals("create()")) {
					if (!file.exists()) {
						file.createNewFile();
						InterpretorInterface.tExecute.append("File " + fileName + " is created.\n");
					} else
						InterpretorInterface.tExecute.append("File " + fileName + " has already been created!\n");
				} else
					InterpretorInterface.tExecute.append(command + " is not valid!\n");

			} else
				InterpretorInterface.tExecute.append(action + " is not valid!\n");

		}

	}

	public void whileConstruction(String condition, String fileName, String action, String command)
			throws FileNotFoundException {
		String line = null;
		if (!new File(fileName).exists()) {
			InterpretorInterface.tExecute.append("File " + fileName + " is not found!\n");
			throw new FileNotFoundException(fileName + " is not found!\n");
		}

		FileReader reader = new FileReader(fileName);
		Scanner scanner = new Scanner(reader);
		if (condition.equals("while")) {
			if (action.equals("hasNextLine()")) {
				if (command.equals("read()")) {
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();
						InterpretorInterface.tExecute.append(line + "\n");
					}
					scanner.close();
				} else
					InterpretorInterface.tExecute.append(command + " is not valid!\n");
			} else
				InterpretorInterface.tExecute.append(action + " is not valid!\n");

		}

	}

	public void findSameFile(File dir, String name) {
		File result = null;
		File[] dirlist = dir.listFiles();
		if (!dir.exists())
			InterpretorInterface.tExecute.append("Folder " + dir.toString() + " not found!\n");
		for (int i = 0; i < dirlist.length; i++) {
			if (dirlist[i].isDirectory()) {
				findSameFile(dirlist[i], name);
				continue;
			} else if (dirlist[i].getName().equals(name)) {
				InterpretorInterface.tExecute.append(dirlist[i].getPath() + "\n");
			}
		}
	}

	public void addToBackup(String fileName) throws IOException {
		File folder = new File("BackupFolder");
		folder.mkdir();
		File file = new File(fileName);
		if (!file.exists())
			InterpretorInterface.tExecute.append("File " + fileName + " is not found!\n");
		FileWriter fileWriter = new FileWriter("settings.txt");
		fileWriter.write(fileName);
		fileWriter.close();
		Files.copy(file.toPath(), folder.toPath().resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
		InterpretorInterface.tExecute.append("File " + fileName + " is added to Backup.\n");
	}

	public void getFromBackup(String fileName) throws IOException {

		try {
			FileReader fileReader = new FileReader("settings.txt");
			Scanner scn = new Scanner(fileReader);
			String oldDirectory = scn.nextLine();
			scn.close();
			File destFolder = new File(oldDirectory);
			Files.copy(new File("BackupFolder\\" + fileName).toPath(), destFolder.toPath());
			InterpretorInterface.tExecute.append("File " + fileName + " is gotten from Backup.\n");

		} catch (Exception e) {
			throw new NullPointerException("This file was not added to backup!\n");
		}

	}

	static class MyFileFilter implements FilenameFilter {
		private String ext;

		public MyFileFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(File dir, String name) {
			int p = name.indexOf(ext);
			return ((p >= 0) && (p + ext.length() == name.length()));
		}
	}

	public void findFilesByMask(String srcPath, String ext, ArrayList<String> list) throws IOException {
		File dir = new File(srcPath);

		File[] files = dir.listFiles(new MyFileFilter(ext));
		if (files.length != 0)
			for (int i = 0; i < files.length; i++) {
				list.add(files[i].getName() + "\n");
			}
		else
			InterpretorInterface.tExecute.append("Files in " + srcPath + " with mask " + ext + " not found!\n");

	}

}
