
import java.io.File;
import java.util.Scanner;

public class Lecteur {
    
    File doc;
    Scanner obj;

    Lecteur(String name) throws Exception {

        this.doc = new File(name);
        this.obj = new Scanner(this.doc);
    }
  
    public int nbLine() throws Exception {
        int cnt = 0;
        while(obj.hasNextLine()){
            obj.nextLine();
            cnt ++;
        }
        obj = new Scanner(this.doc);
        return cnt;
    }

    public String getLine(){
        String line = obj.nextLine();
        return line;
    }

    public File getDoc() {
        return this.doc;
    }

    public void setDoc(File doc) {
        this.doc = doc;
    }

    public Scanner getObj() {
        return this.obj;
    }

    public void setObj(Scanner obj) {
        this.obj = obj;
    }
    
}
