import java.io.*;
import java.net.*;
public class serverRaya
{
    private ServerSocket skServer;
    private Socket[] skClients;
    private DataOutputStream[] skOStream;
    private DataInputStream[] skIStream;
    
    /** Conectamos con los jugadores **/
    public serverRaya()
    {   
       this.skClients = new Socket[2];
       this.skOStream = new DataOutputStream[2];
       this.skIStream = new DataInputStream[2];
       try
       {
           this.skServer = new ServerSocket(5005);
           System.out.println("Server Iniciado...");
           System.out.println("Esperando a los jugadores...");
           for(int i=0;i<2;i++) this.skClients[i] = skServer.accept();
           System.out.println("Jugadores Conectados! :)");
           System.out.println("Atendiendo elecciones...");
           setElect();
       }
       catch(Exception e)
       {
           System.out.println("Fallaco Bueno de Conexion :P");
       }
    } 
   public void setElect()
   {   
       try
       {
           for(int j=0;j<2;j++)
           {
               this.skOStream[j] = new DataOutputStream(this.skClients[j].getOutputStream());
               this.skIStream[j] = new DataInputStream(this.skClients[j].getInputStream());
               this.skOStream[j].writeUTF("Eres el jugador "+(j+1)+(j==0?" Empiezas Tu Capullo!":" Empiezas Segundo Pringao"));
               this.skOStream[j].writeInt(j);
            }
       }
       catch(Exception e)
       {
           System.out.println("Fallaco en los Outputs :O");
       }    
       int count = 0;
       while(true)
       {
           try
           {
               System.out.println(count);
               
               if(count<6)
               {
                   // Tirada Jugador Uno
                   if(count%2==0)
                   {
                       int streamUno = this.skIStream[0].readInt();
                       int streamUnoBis = this.skIStream[0].readInt();
                       for(int j=0;j<2;j++)this.skOStream[j].writeInt(streamUno);
                       for(int x=0;x<2;x++)this.skOStream[x].writeInt(streamUnoBis);
                   }
                    // Tirada Jugador Dos
                     else
                   {
                       int streamDos = this.skIStream[1].readInt();
                       int streamDosBis = this.skIStream[1].readInt();
                       for(int j=0;j<2;j++)this.skOStream[j].writeInt(streamDos);
                       for(int x=0;x<2;x++)this.skOStream[x].writeInt(streamDosBis);
                   } 
               }
               else
               {
                   if(count%2==0)
                   {
                       int sustiUno = this.skIStream[0].readInt();
                       int sustiDos = this.skIStream[0].readInt();
                       for(int p=0;p<2;p++) this.skOStream[p].writeInt(sustiUno);
                       for(int p=0;p<2;p++) this.skOStream[p].writeInt(sustiDos);
                       int streamUno = this.skIStream[0].readInt();
                       int streamUnoBis = this.skIStream[0].readInt();
                       for(int j=0;j<2;j++)this.skOStream[j].writeInt(streamUno);
                       for(int x=0;x<2;x++)this.skOStream[x].writeInt(streamUnoBis);
                       
                   }
                   else
                   {
                       int sustiUno = this.skIStream[1].readInt();
                       int sustiDos = this.skIStream[1].readInt();
                       for(int p=0;p<2;p++) this.skOStream[p].writeInt(sustiUno);
                       for(int p=0;p<2;p++) this.skOStream[p].writeInt(sustiDos);
                       int streamDos = this.skIStream[1].readInt();
                       int streamDosBis = this.skIStream[1].readInt();  
                       for(int j=0;j<2;j++)this.skOStream[j].writeInt(streamDos);
                       for(int x=0;x<2;x++)this.skOStream[x].writeInt(streamDosBis);
                    }
                   
               }   
               count += 1;
           }
           catch(IOException e)
           {              
              break;
           }
       }
       
    } 
   public static void main(String args[])
   {
       serverRaya server = new serverRaya();
   }
}