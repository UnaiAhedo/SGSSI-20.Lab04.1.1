import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Principal {

    private static String mejorDigest = null;
    private static String mejorHexa  = null;
    private static int digestMasLargo = 0;

    private static String nombreArchivoFinal = "Resultado.txt"; // El fichero donde se va a guardar el resultado de añadir
                                                                // el valor hexadecimal al fichero original.
    private static String pathEntrada = "SGSSI-20.CB.05.txt"; // Fichero original

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String resultado = digestEmpezandoPor0s(pathEntrada);
        System.out.println(resultado);
    }

    private static String digestEmpezandoPor0s(String pPath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");

        File file;
        String digest;
        String hx;
        String hx1;

        boolean primeraVez = true;
        LocalDateTime then = LocalDateTime.now();

        File fileOriginal = new File(pPath);
        File fileCopia = new File(nombreArchivoFinal);
        copyFileUsingStream(fileOriginal, fileCopia);

        while(ChronoUnit.SECONDS.between(then, LocalDateTime.now()) <= 60) {
            hx = hexadecimalAleatorio(8); // El valor hexadecimal a modificar si se quiere hacer "valor + Gxxxx"
            hx1 = "\n" + hx;
            file = new File(pPath);
            if(primeraVez) {
                primeraVez = false;
            }else{
                borrarUltimaLinea(pPath);
            }
            try {
                Files.write(Paths.get(pPath), hx1.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
            }
            digest = crearDiget(md5Digest, file);
            mayorNumero0s(digest, hx);
        }

        borrarUltimaLinea(pPath);
        try {
            Files.write(Paths.get(nombreArchivoFinal), ("\n"+mejorHexa).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
        }

        if(mejorHexa != null && mejorDigest != null){
            return "El digest del archivo es: " + mejorDigest + " con el valor hexadecimal añadido: " + mejorHexa;
        }else{
            return "No se ha encontrado ningún digest que empiece por 0.";
        }
    }

    private static void borrarUltimaLinea(String pPath) throws IOException {
        RandomAccessFile f = new RandomAccessFile(pPath, "rw");
        long length = f.length() - 1;
        byte b;
        do {
            length -= 1;
            f.seek(length);
            b = f.readByte();
        }
        while(b != 10);
        f.setLength(length);
        f.close();
    }

    private static void copyFileUsingStream(File pSource, File pDest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(pSource);
            os = new FileOutputStream(pDest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            assert is != null;
            is.close();
            assert os != null;
            os.close();
        }
    }

    private static void mayorNumero0s(String pDigest, String hx){
        int i = 0;
        boolean salir = false;

        String digestPerfecto = "00000000000000000000000000000000";
        while(!salir){
            if(pDigest.equalsIgnoreCase(digestPerfecto) || pDigest.charAt(i) != '0'){
                salir = true;
            }else{
                i++;
            }
        }

        if(i > digestMasLargo){
            digestMasLargo = i;
            mejorDigest = pDigest;
            mejorHexa = hx;
        }
    }

    private static String hexadecimalAleatorio(int pNnumchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < pNnumchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, pNnumchars);
    }

    private static String crearDiget(MessageDigest digest, File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };
        fis.close();
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
