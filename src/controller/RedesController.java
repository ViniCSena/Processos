package controller;

import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.UnexpectedException;

public class RedesController {

    private final String os;

    public RedesController() {
        this.os = os();
    }

    private String os() {
        return System.getProperty("os.name");
    }

    private Process executeProcess(String process) {
        try {
            return Runtime.getRuntime().exec(process);
        } catch (IOException e) {
            if (e.getMessage().contains("2")) {
                System.err.println("Digite o processo corretamente!");
            } else if (e.getMessage().contains("740")) {
                System.err.println("Você não tem acesso de administrador para executar este comando.");
            }
        }
        throw new RuntimeException("Não foi possível executar este comando: " + process);
    }

    public void ip() throws IOException {
        if (os.contains("Windows")) {
            Process process = executeProcess(Constants.IP_CONFIG_WINDOWS);
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();
            String adaptador = "";
            String ipv4 = "";
            while (linha != null) {
                if (linha.contains("Adaptador")) {
                    adaptador = linha.substring(0, linha.length() - 2);
                }
                if (linha.contains("IPv4")) {
                    ipv4 = linha.split(":")[1];
                    System.out.println("\nAdaptador : " + adaptador + "\n Endereço ipv4:" + ipv4);
                }
                linha = bufferedReader.readLine();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } else if (os.contains("Linux")) {
            Process process = executeProcess(Constants.IP_CONFIG_LINUX);
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();
            String adaptador = "";
            String ipv4 = "";
            while (linha != null) {
                if (linha.contains("flags")) {
                    adaptador = linha.split(":")[0];
                }
                if (linha.contains("inet ")) {
                    ipv4 = linha.split(" ")[1];
                    System.out.println("\nAdaptador : " + adaptador + "\n Endereço ipv4:" + ipv4);
                }
                linha = bufferedReader.readLine();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }else{
            System.err.println("Sai daqui, usuário de Mac grrr");
            throw new UnexpectedException("Quem usa Mac não tem lugar aqui");
        }
    }

    public void ping() throws IOException {
        String media = "";
        if (os.contains("Windows")) {
            Process process = executeProcess(Constants.PING_GOOGLE_WINDOWS);
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();
            String[] var;
            while (linha != null) {
                if (linha.contains("M�dia")) {
                    var = linha.split("=");
                    media = var[(var.length - 1)];
                    break;
                }
                linha = bufferedReader.readLine();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } else if (os.contains("Linux")) {
            Process process = executeProcess(Constants.PING_GOOGLE_LINUX);
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();
            String[] var;
            String aux;
            while (linha != null) {
                if (linha.contains("min/avg/max/mdev")) {
                    var = linha.split("=");
                    aux = var[(var.length - 1)];
                    media = aux.split("/")[1] + "ms";
                    break;
                }
                linha = bufferedReader.readLine();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }else{
            System.err.println("Sai daqui, usuário de Mac grrr");
            throw new UnexpectedException("Quem usa Mac não tem lugar aqui");
        }

        System.out.println("A média de tempo é" + media);
    }
}
