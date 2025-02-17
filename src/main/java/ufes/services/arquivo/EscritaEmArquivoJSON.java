package ufes.services.arquivo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

public class EscritaEmArquivoJSON implements IArquivo {
    private static EscritaEmArquivoJSON instancia;
    private final String filePath = "src/main/java/ufes/services/arquivo/LogJSON.json";

    private FileWriter fw;
    private File file;

    private EscritaEmArquivoJSON() {
        try {
            this.file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exIO) {
            JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo");
        }
    }

    public static EscritaEmArquivoJSON getInstancia() {
        if (instancia == null) {
            EscritaEmArquivoJSON.instancia = new EscritaEmArquivoJSON();
        }
        return EscritaEmArquivoJSON.instancia;
    }

    @Override
    public void escreverArquivo(String log) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            if (file.length() != 0) {
                // Apaga a última linha que contém o caractere ']'
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                long length = raf.length();
                raf.setLength(length - 2);
                raf.close();
                
                bw.write(",\n");
            } else {
                bw.write("[\n"); 
            }

            bw.write(log);

            bw.close();

            // Adiciona o fechamento do array depois de escrever o objeto
            FileWriter fw2 = new FileWriter(file, true);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            bw2.write("\n]");
            bw2.close();
        } catch (IOException ioEx) {
            JOptionPane.showMessageDialog(null, ioEx);
        }
    }
}
