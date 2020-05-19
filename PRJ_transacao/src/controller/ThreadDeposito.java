package controller;

import java.util.concurrent.Semaphore;

public class ThreadDeposito extends Thread{

	Semaphore semaforo;
	int codigoConta;
	double saldo, valorDeposito;
	
	public ThreadDeposito(Semaphore semaforo, int codigoConta, double saldo, double valorDeposito) {
		this.semaforo = semaforo;
		this.codigoConta = codigoConta;
		this.saldo = saldo;
		this.valorDeposito = valorDeposito;
	}
	
	@Override
	public void run() {
		IniciandoDeposito(); //Exibe mensagem que a transação está na fila do semaforo
		try {
			semaforo.acquire(); //entra no semaforo
			RealizandoDeposito();     //transação propriamente dita
		} catch(InterruptedException e) {
			e.printStackTrace();	//erro de interrupção
		} finally {
			semaforo.release();	//sai do semáforo
			FinalizandoDeposito(); //mostra mensagem de finalização
		}
	}
	
	private void IniciandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " (R$" + valorDeposito + ")(Saldo Atual: R$" + saldo + ") na fila");
	}
	
	private void RealizandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " (R$" + valorDeposito + ")(Saldo Atual: R$" + saldo + ") está sendo processado");
		saldo += valorDeposito;
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void FinalizandoDeposito() {
		System.out.println("Deposito na conta " + codigoConta + " realizado: Valor depositado = " + valorDeposito + ", Saldo atual = " + saldo);
	}
}
