package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Classe utilitária para manipulação de arquivos.
 * Contém métodos para salvar conteúdo em um arquivo especificado.
 */
public class FileUtil {

    /**
     * Salva o conteúdo fornecido em um arquivo no caminho especificado.
     * Se o diretório não existir, ele será criado.
     * 
     * @param conteudo O conteúdo a ser salvo no arquivo.
     * @param caminhoArquivo O caminho completo onde o arquivo será salvo.
     */
    public static void salvarEmArquivo(String conteudo, String caminhoArquivo) {
        try {
            // Obtém o diretório do arquivo
            File diretorio = new File(caminhoArquivo).getParentFile();
            
            // Cria o diretório, se não existir
            if (!diretorio.exists()) {
                diretorio.mkdirs();  
            }
            
            // Escreve o conteúdo no arquivo especificado
            Files.write(Paths.get(caminhoArquivo), conteudo.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            System.out.println("Relatório salvo em: " + caminhoArquivo);  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
