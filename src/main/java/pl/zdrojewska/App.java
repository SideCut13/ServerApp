package pl.zdrojewska;

import pl.zdrojewska.service.Service;
import pl.zdrojewska.service.ServiceImpl;
import pl.zdrojewska.tables.Client;
import pl.zdrojewska.tables.Meal;

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
        //Service service = new ServiceImpl();
       // service.addMealService("Sniadanie","Bulka z serem", "Posmarowac bulke maslem,dodac ser", "12341234", "", 1L);

        try {
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
        }


    }

}
class ClientConnection implements Runnable
{

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader bufferedReader;
    private Service service = new ServiceImpl();

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

    public void login(String login,String password) throws IOException {
        List<Client> clients = service.getAllClientsService();
        Long idClient = null;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getLogin().equals(login) && clients.get(i).getPassword().equals(password)) {
                writer.println("OK");
                idClient = Long.valueOf(i);
            }
        }
        String status = bufferedReader.readLine();
        if (status.equals("Sync")) {
            List<Long> idsList = new ArrayList<>();
            writer.println("OK");
            status = bufferedReader.readLine();
            if (status.equals("Send")) {
                String readId = bufferedReader.readLine();
                while (!readId.equals("Endsend")) {
                    idsList.add(Long.parseLong(readId));
                    readId = bufferedReader.readLine();
                }
                writer.println("OK");
                writer.println("Send");
                //sending first meal
                List<Meal> meals = service.getAllMealsService();

                for (int i = 0; i <meals.size() ; i++) {
                    if(meals.get(i).getClient().equals(idClient)){
                        writer.println(meals.get(i).getId());
                        writer.println(meals.get(i).getTitle());
                        writer.println(meals.get(i).getSummary());
                        writer.println(meals.get(i).getDescription());
                        writer.println(meals.get(i).getDate());
                        writer.println(meals.get(i).getImagePath());
                        writer.println("isNext");
                    }
                }
                writer.println("Endsend");

            } else {
                status = null;
            }

        } else {
            writer.println("NOK");
        }

               /* writer.println("2");    //id
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

                writer.println("Endsend");*/

    }



}