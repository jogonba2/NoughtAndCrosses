/**
 * Write a description of class Juego here.
 * 
 * @author Jesus Vieco (Juego) Jose Gonzalez(Server y Cliente)
 * @version 1.0
 */
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;
public class Juego
{
    
    private static String [][] tablero;
    public static int numJugada;
    static Scanner entradaTeclado = new Scanner(System.in);
    public Juego()
    {
        numJugada=0;
        tablero = new String [4][4];
        for(int i=0;i<tablero.length;i++){
            for(int j=0;j<tablero[0].length;j++)tablero[i][j]=" ";
        }
        tablero[0][1]="1";
        tablero[0][2]="2";
        tablero[0][3]="3";
        tablero[1][0]="1";
        tablero[2][0]="2";
        tablero[3][0]="3";
    }
    
    public static void clearWindow()
    {
        for(int i=0;i<25;i++)System.out.println();
    }
    public static void agregar(int x, int y)
    {
        try{            
            // No se sobreescribiran fichas
            if(tablero[x][y] != " " && numJugada < 6){
                System.out.println("No sobreescribas cabron ahora vuelve a empezar");
                System.exit(0);
            }
            else{
                tablero[x][y]=String.format("%s",(numJugada%2==0?"X":"O"));
            }
        }
        catch(Exception e){
            System.out.println("Chapo esto por gracioso, no intentes posiciones falsas :D");
            System.exit(0);
        }
    }
    
    public static void sustituir(int x, int y,int numberClient)
    {
        try{
                if(tablero[x][y] == " "){
                    System.out.println("No seas tramposo :D");
                    System.exit(0);
                }
                // Truco: Se pueden sustituir fichas ajenas, la funcion se deja para joder un poco al personal :D
                else tablero[x][y] = " ";
        } 
        catch(Exception e){
            System.out.println("Chapo esto por gracioso, no intentes posiciones falsas :D");
            System.exit(0);
        }
    }
    
    public static String haGanado()
    {
        //Horizontales
        if(tablero[1][1].equals(tablero[1][2]) && tablero[1][1].equals(tablero[1][3]))return tablero[1][1];
        if(tablero[2][1].equals(tablero[2][2]) && tablero[2][1].equals(tablero[2][3]))return tablero[2][1];
        if(tablero[3][1].equals(tablero[3][2]) && tablero[3][1].equals(tablero[3][3]))return tablero[3][1];
        //verticales
        if(tablero[1][1].equals(tablero[2][1]) && tablero[1][1].equals(tablero[3][1]))return tablero[1][1];
        if(tablero[1][2].equals(tablero[2][2]) && tablero[1][2].equals(tablero[3][2]))return tablero[1][2];
        if(tablero[1][3].equals(tablero[2][3]) && tablero[1][3].equals(tablero[3][3]))return tablero[1][3];
        //diagonales
        if(tablero[1][1].equals(tablero[2][2]) && tablero[1][1].equals(tablero[3][3]))return tablero[1][1];
        if(tablero[3][1].equals(tablero[2][2]) && tablero[3][1].equals(tablero[1][3]))return tablero[1][3];
        //si no hay ganador
        return "no";
        
    }
    
    public static void mostrarTablero()
    {
        for(int i=0;i<tablero.length;i++){
        for(int j=0;j<tablero[1].length;j++) System.out.print(tablero[i][j]+"|");
        System.out.println(" "); 
        }
    }
    public static void normasJuego()
    {
        String textMessage = "A ver cabrones, si vais a jugar a esto respetad las normas:\n"+
                             "1-> Las coordenadas se introducen asi: 0 0\n"+
                             "2-> No introduzcais letras ni ningun caracter raro\n"+
                             "3-> No intentes sustituir fichas antes de las primeras 6 jugadas\n"+
                             "4-> Nada mas, cada vez que metais algo raro el server se cerrara y se volvera a reiniciar solo.\n"+
                             "    TODAS ESTAS NORMAS OS LAS PODEIS PASAR POR LOS ....... PERO SI QUEREIS JUGAR EN SERIO RESPETADLAS";
                             
        JOptionPane.showMessageDialog(null,textMessage,"Normas",JOptionPane.PLAIN_MESSAGE);
    }
    public static void main(String args[])
    {
        Juego juego=new Juego(); 
        normasJuego();
        mostrarTablero();
        System.out.println("Esperando al otro jugador...");
        try{   
            Socket sc = new Socket("127.0.0.1",5005); // Sustituir aqui la IP en la que se aloja el servidor
            //creamos el flujo de datos por el que se enviara un mensaje y se recibira el enviado por el otro jugador y por mi
            DataOutputStream mensaje = new DataOutputStream(sc.getOutputStream());
            DataInputStream entrada = new DataInputStream(sc.getInputStream());
            //Recibimos el gestionador de clientes
            System.out.println(entrada.readUTF());
            int numberClient = entrada.readInt();
            boolean mostrarRepeticion = false;
            while(true){        
                if(numJugada<6){
                    if((numberClient == 0 && numJugada%2 == 0)||(numberClient == 1 && numJugada%2 == 1)){
                        System.out.println("Jugador "+(numberClient+1)+" Dime las coordenadas con un espacio entre las 2(Ej: 3 3)");
                        int sendCoordUno = entradaTeclado.nextInt();
                        int sendCoordDos = entradaTeclado.nextInt();
                        mensaje.writeInt(sendCoordUno);
                        mensaje.writeInt(sendCoordDos);
                        agregar(entrada.readInt(),entrada.readInt());           
                    }
                    else{
                        System.out.println("Turno del otro espera...");
                        agregar(entrada.readInt(),entrada.readInt());
                    }       
                    numJugada++;
                }
                else{ 
                    System.out.println(mostrarRepeticion==false?"Teneis Que Sustituir":"");
                    mostrarRepeticion = true;
                    if((numberClient == 0 && numJugada%2 == 0)||(numberClient == 1 && numJugada%2 == 1)){   
                        System.out.println("Jugador " + (numberClient+1) + " Dime las coordenadas de la ficha a retirar");
                        int sendCoordUno = entradaTeclado.nextInt();
                        int sendCoordDos = entradaTeclado.nextInt();
                        mensaje.writeInt(sendCoordUno);
                        mensaje.writeInt(sendCoordDos);
                        sustituir(entrada.readInt(),entrada.readInt(),numberClient);
                        System.out.println("Jugador "+(numberClient+1)+" Dime las coordenadas donde deseas poner la nueva ficha");
                        sendCoordUno = entradaTeclado.nextInt();
                        sendCoordDos = entradaTeclado.nextInt();
                        mensaje.writeInt(sendCoordUno);
                        mensaje.writeInt(sendCoordDos);
                        agregar(entrada.readInt(),entrada.readInt());           
                    }
                    else{
                       System.out.println("Turno del otro espera...");
                       sustituir(entrada.readInt(),entrada.readInt(),numberClient);
                       agregar(entrada.readInt(),entrada.readInt());                      
                    }
                    numJugada++;                      
                }
                // HA GANADO
                if(haGanado().equals("no") || haGanado().equals(" ")){}
                else{
                    mostrarTablero();
                    System.out.printf("Ha ganado el jugador %s",numJugada%2==0?"2":"1");
                    System.exit(0);
                }
                clearWindow();
                mostrarTablero();
            }
        }
        catch(Exception e){
            System.out.println("Se cerro, os jodeis :)");
            System.exit(0);
        }   
    }
}