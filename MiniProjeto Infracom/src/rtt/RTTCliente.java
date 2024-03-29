package rtt;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JTextPane;

public class RTTCliente extends Thread {
	private String endereco;
	private JTextPane Rtt;
	
	public RTTCliente(String endereco, JTextPane rtt){
		this.endereco = endereco;
		Rtt = rtt;
	}

	public void run(){
		double estRtt = 0;
		String retorna;
		try {
			@SuppressWarnings("resource")
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPServer = InetAddress.getByName(endereco);
			byte[] sendData;
			sendData = ("1").getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, 5000);
			boolean primeiro = true;
			double valor;
			while(true){
				long tempoAtual = System.currentTimeMillis();
				clientSocket.send(sendPacket); 
				byte[] receiveData = new byte[1];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				valor =  ((System.currentTimeMillis() - tempoAtual));
				if(primeiro){//primeiro valor do estRtt
					estRtt = valor;
					primeiro = false;//so iguala uma vez
				}
				estRtt = (0.8755)*estRtt + 0.125*valor;
				retorna = String.valueOf(estRtt);
				Rtt.setText(retorna + " ms");
				Thread.sleep(500);
			}
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
			
		}
	}



}

