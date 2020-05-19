package controller;

import java.util.concurrent.Semaphore;

public class ThreadTransacao extends Thread{

	Semaphore semaforo;
	int codigoConta, opcao;
	double saldo, valor;
	
	public ThreadTransacao(Semaphore semaforo, int codigoConta, double saldo, double valor, int opcao) {
		this.semaforo = semaforo;
		this.codigoConta = codigoConta;
		this.saldo = saldo;
		this.valor = valor;
		this.opcao = opcao;
	}
	
	@Override
	public void run() {
		switch(opcao) {
		case 1:	//SAQUE
			IniciandoSaque(); 					//Exibe mensagem que a transa��o est� na fila do semaforo
			try {
				semaforo.acquire(); 			//entra no semaforo
				RealizandoSaque();     			//transa��o propriamente dita
			} catch(InterruptedException e) {
				e.printStackTrace();			//erro de interrup��o
			} finally {							//roda depois do try/catch
				semaforo.release();				//sai do sem�foro
				FinalizandoSaque(); 			//mostra mensagem de finaliza��o
			}
			break;
		case 2:									//DEPOSITO - mesma coisa que o saque, por�m com os m�todos pr�prios para dep�sito
			IniciandoDeposito();
			try {
				semaforo.acquire(); 
				RealizandoDeposito();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforo.release();	
				FinalizandoDeposito();
			}
			break;
		}
	}
	
	private void IniciandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " na fila (Valor: R$" + valor + ")(Saldo Atual: R$" + saldo + ")");
	}
	
	private void RealizandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " est� sendo processado (Valor: R$" + valor + ")(Saldo Atual: R$" + saldo + ")");
		saldo -= valor;
		try {
			sleep(4000);	//sleep de 4s (4x o tempo de deposito) para que seja mais f�cil visualizar os semaforos
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(saldo < 0) {			//caso o saque seja maior que o saldo, o programa automaticamente faz o empr�stimo do que falta
			System.out.println("Empr�stimo de R$" + (saldo * -1) + " realizado (Conta " + codigoConta + ")");
		}
	}
	
	private void FinalizandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " realizado: Valor sacado = R$" + valor + ", Saldo atual = R$" + saldo);
	}
	
	private void IniciandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " na fila (R$" + valor + ")(Saldo Atual: R$" + saldo + ")");
	}
	
	private void RealizandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " est� sendo processado (R$" + valor + ")(Saldo Atual: R$" + saldo + ")");
		saldo += valor;
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void FinalizandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " realizado: Valor depositado = R$" + valor + ", Saldo atual = R$" + saldo);
	}
	
}
