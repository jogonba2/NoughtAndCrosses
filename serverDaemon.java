public class serverDaemon
{
    public static void main(String args[])
    {
       try{
           while(true)
           {
               Process p = Runtime.getRuntime().exec("java serverRaya");
               p.waitFor();
               if(p.waitFor() == 0) continue;
               else
               {
                   System.out.println("ERROR");
                   System.exit(0);
               }
           }
       }
       catch(Exception e){
           System.out.println("ERRORZACO");
       }
    }
}