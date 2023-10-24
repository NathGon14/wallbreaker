package Code;

import java.util.Timer;
import java.util.TimerTask;


public class CountDown{


    private long startingTime ;
    private boolean isUsed = false ;

        public CountDown(){
        }
        public long getEstimatedTime(){
            long elpasedTime =  System.currentTimeMillis() -  startingTime;
            startingTime =  System.currentTimeMillis() ;
            return  elpasedTime;
        }

    public boolean isUsed() {
        return isUsed;
    }


    public void startTime(boolean used) {
        startingTime =  System.currentTimeMillis() ;
        isUsed = used;
    }
}
