package propias.dominio.clases;
import java.util.*;

public class Stadistics implements java.io.Serializable {
 
    public long numEasyMatches;
    public long numMediumMatches;
    public long numHardMatches;
    public long timeEasyMatches;
    public long timeMediumMatches;
    public long timeHardMatches;
    public long bestTimeEasyMatches;
    public long bestTimeMediumMatches;
    public long bestTimeHardMatches;

    
    public Stadistics(List<Long> l) {

        this.numEasyMatches = l.get(0);
        this.numMediumMatches = l.get(1);
        this.numHardMatches = l.get(2);
        this.timeEasyMatches = l.get(3);
        this.timeMediumMatches = l.get(4);
        this.timeHardMatches = l.get(5);
        this.bestTimeEasyMatches = l.get(6);
        this.bestTimeMediumMatches = l.get(7);
        this.bestTimeHardMatches = l.get(8);

    }
    public void afegirNumPartides(long num, int dif) { 
         if (dif == 0) this.numEasyMatches += num;
         else if (dif == 1) this.numMediumMatches += num;
         else if (dif == 2) this.numHardMatches += num;
    }
    public void addTime(long time, int dificultat) { 

         if (dificultat == 0) timeEasyMatches += time;
         else if (dificultat == 1) timeMediumMatches += time;
         else if (dificultat == 2) timeHardMatches += time;
         if (dificultat == 0) calcBestTime(time,0);
         else if (dificultat == 1) calcBestTime(time,1);
         else if (dificultat == 2) calcBestTime(time,2);
    }
    
    public long min(long a, long b) {
    	if (a <=b) return a;
    	return b;
    }
    
    public void calcBestTime(long time,int dificultat) {
       if (bestTimeEasyMatches == 0 && dificultat == 0) bestTimeEasyMatches = time;
       else if (dificultat == 0) bestTimeEasyMatches = min(bestTimeEasyMatches,time); 
       if (bestTimeMediumMatches == 0 && dificultat == 1) bestTimeMediumMatches = time;
       else if(dificultat == 1) bestTimeMediumMatches = min(bestTimeMediumMatches, time);
       if (bestTimeHardMatches == 0 && dificultat == 2) bestTimeHardMatches = time;
       else if (dificultat == 2) bestTimeHardMatches = min(bestTimeHardMatches, time);
    }
    
}
