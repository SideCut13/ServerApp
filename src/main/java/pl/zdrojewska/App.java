package pl.zdrojewska;

import pl.zdrojewska.service.Service;
import pl.zdrojewska.service.ServiceImpl;
import pl.zdrojewska.tables.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {


    static ServerSocket serverSocket;


    public static void main(String[] args)
    {
        Service service = new ServiceImpl();
        service.addMealService("Sniadanie","Bulka z serem", "Posmarowac bulke maslem,dodac ser", "12341234", "", 1L);

       /* try {
            ExecutorService executor = Executors.newCachedThreadPool();


            serverSocket = new ServerSocket(2500);

            while(true) {



                Socket clientSocket = serverSocket.accept();
                ClientConnection client = new ClientConnection(clientSocket);
                executor.submit(client);
            }


        }catch(IOException e)
        {
            System.out.print("Nie udalo sie utworzyc polaczenia");
            System.out.print(e.getMessage());
        }*/


    }

}
class ClientConnection implements Runnable
{

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader bufferedReader;

    public ClientConnection(Socket clientSocket) throws IOException {

        this.clientSocket = clientSocket;
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(),true);
    }

    @Override
    public void run() {

        try {
            StringBuilder message = new StringBuilder();
            String readedLine = bufferedReader.readLine();


            switch(readedLine)
            {
                case("LOGIN"):
                    String login = bufferedReader.readLine();
                    String password = bufferedReader.readLine();
                    login(login,password);
                    break;
            }

        }catch (Exception e)
        {
            // to do
        }
    }

    public void login(String login,String password) throws IOException
    {
        if(login.equals("Antoni") && password.equals("Dzik"))
        {
            writer.println("OK");

            String status = bufferedReader.readLine();
            if(status.equals("Sync"))
            {
                List idsList = new ArrayList<Integer>();
                writer.println("OK");
                status = bufferedReader.readLine();
                if(status.equals("Send")) {
                    String readId = bufferedReader.readLine();
                    while (!readId.equals("Endsend")) {
                        idsList.add(Integer.parseInt(readId));
                        readId = bufferedReader.readLine();
                    }
                    writer.println("OK");
                    writer.println("Send");

                    //sending first meal
                    writer.println("2");    //id
                    writer.println("Sniadanie"); //title
                    writer.println("Bulka z maslem"); //summary
                    writer.println("Posmarowac bulke maslem"); //description
                    writer.println("12323234"); // date
                    writer.println(""); //image path

                    //end sending first meal
                    writer.println("isNext");
                    //sending second meal

                    writer.println("3");    //id
                    writer.println("Sniadanie"); //title
                    writer.println("Bulka z serem"); //summary
                    writer.println("Posmarowac bulke maslem,dodac ser"); //description
                    writer.println("123234234"); // date
                    writer.println(""); //image path

                    //end sending second meal

                    //end sending meals

                    writer.println("Endsend");
                }
                else
                {
                    status = null;
                }

            }
        }else
        {
            writer.println("NOK");
        }
    }
}