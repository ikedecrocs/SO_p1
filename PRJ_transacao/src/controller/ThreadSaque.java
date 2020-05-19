package controller;

import java.util.concurrent.Semaphore;

public class ThreadSaque extends Thread{
	
	Semaphore semaforo;
	int codigoConta;
	double saldo, valorSaque;
	
	public ThreadSaque(Semaphore semaforo, int codigoConta, double saldo, double valorSaque) {
		this.semaforo = semaforo;
		this.codigoConta = codigoConta;
		this.saldo = saldo;
		this.valorSaque = valorSaque;
	}
	
	@Override
	public void run() {
		IniciandoSaque(); //Exibe mensagem que a transa��o est� na fila do semaforo
		try {
			semaforo.acquire(); //entra no semaforo
			RealizandoSaque();     //transa��o propriamente dita
		} catch(InterruptedException e) {
			e.printStackTrace();	//erro de interrup��o
		} finally {
			semaforo.release();	//sai do sem�foro
			FinalizandoSaque(); //mostra mensagem de finaliza��o
		}
	}
	
	private void IniciandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " (R$" + valorSaque + ")(Saldo Atual: R$" + saldo + ") na fila");
	}
	
	private void RealizandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " (Valor: R$" + valorSaque + ")(Saldo Atual: R$" + saldo + ") est� sendo processado");
		saldo -= valorSaque;
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(saldo < 0) {
			System.out.println("Empr�stimo de " + (saldo * -1) + " realizado");
		}
	}
	
	private void FinalizandoSaque() {
		System.out.println("Saque na conta " + codigoConta + " realizado: Valor sacado = " + valorSaque + ", Saldo atual = " + saldo);
	}

}
