/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibur
 */
public class ClientCommunication extends Thread {

    Socket socket;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int rand = 0;
        Random random = new Random();
        while (true) {

            rand = random.nextInt(11);

            if (rand != 0) {
                break;
            }
        }

        System.out.println(String.valueOf(rand));

        serveClient(socket, rand);
    }

    private static void serveClient(Socket socket, int rand) {
        BufferedReader br = null;

        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            Scanner sc = new Scanner(System.in);

            String line;
            String lineToSendToClient;
         

            do {
                line = br.readLine();
                System.out.println("Client said: " + line);

                if (line.equals(String.valueOf(rand))) {
                    System.out.println("Client guessed right");
                    lineToSendToClient = "Right number";
                } else {
                    System.out.println("Client guessed wrong");
                    lineToSendToClient = "Wrong number";
                }

                bw.write(lineToSendToClient);
                bw.newLine();
                bw.flush();
            } while (line != "FIN");

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
