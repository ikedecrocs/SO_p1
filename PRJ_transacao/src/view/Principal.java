package view;

import java.util.concurrent.Semaphore;

import controller.ThreadTransacao;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Semaphore semaforoSaque = new Semaphore(1);		//1 saque por vez
		Semaphore semaforoDeposito = new Semaphore(1);	//1 depósito por vez
		Semaphore[] semaforos = { semaforoSaque, semaforoDeposito };	//semaforos a serem utilizados por saque/deposito
		double saldo = 0, valor = 0;
		int opcao = 0;
		
		for(int i = 0; i < 20; i++) {	//rodar 20x
			opcao = (int) ((Math.random() * 2) + 1);	//opcao de 1 a 2 (1 - saque, 2 - deposito)
			valor = Math.random() * 500;	// geração de valor (0 a 500)
			valor = Math.round(valor * 100.0) / 100.0;	//2 casas decimais em valor
			saldo = Math.random() * 2000;	//geração de saldo (0 a 2000)
			saldo = Math.round(saldo * 100.0) / 100.0;	//2 casas decimais em valor
			Thread tTransacao = new ThreadTransacao(semaforos[opcao-1], i, saldo, valor, opcao);
			tTransacao.start();		//inicio da thread
		}
	}

}
