package view;

import java.io.IOException;
import java.util.Scanner;

import controller.RedesController;

public class Main {

    static RedesController redesController = new RedesController();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
    	System.out.println("Bem vindo ao sistema de processos de redes. ");
    	int op;
    	do {
    		System.out.println("\nDigite:"
        			+ "\n0 Para sair."
        			+ "\n1 Para processo de ping."
        			+ "\n2 Para processo de configuração de ip. \n");
        	
        	op = input.nextInt();
    		try {
    			switch (op) {
    			case 1:
					redesController.ping();
    				break;
    			case 2:
					redesController.ip(); 
					break;
    			case 0:
    				System.out.println("Obrigado por utilizar este programa.");
    				break;
				default:
						System.out.println("Opção desconhecida. Escolha novamente");
						break;
    			}
    		}catch (IOException e) {
				System.err.println("Erro. não foi possível executar o processo. Mensagem:" + e.getMessage() );
			}
    	}while(op!=0);
    }
}
