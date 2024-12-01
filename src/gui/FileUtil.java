package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {

	public static void salvarEmArquivo(String conteudo, String caminhoArquivo) {
	    try {
	        File diretorio = new File(caminhoArquivo).getParentFile();
	        if (!diretorio.exists()) {
	            diretorio.mkdirs();  
	        }
	        
	        Files.write(Paths.get(caminhoArquivo), conteudo.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	        System.out.println("Relatório salvo em: " + caminhoArquivo);  
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
