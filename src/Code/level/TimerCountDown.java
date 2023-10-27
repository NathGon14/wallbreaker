package Code.level;


public class TimerCountDown {


    private long startingTime ;
    private boolean stop = true ;
        private int milisecond = 1000;
    private  long duration;

        public TimerCountDown(){


        }
        public long getDuration(){
            //if the counter stopped
            if(stop) return duration;


            long elpasedTime =  System.currentTimeMillis() -  startingTime;
            duration =  duration - elpasedTime;

            // if duration is less than zero then done counting
            if(duration <= 0 ){
                duration =0;
                setStop(true);
            }else  startingTime =  System.currentTimeMillis() ;
            return  duration;
        }


    public void setTime(int duration) {
        this.duration = duration * milisecond;
        startingTime =  System.currentTimeMillis() ;
        setStop(false);
    }
    public void start() {
        startingTime =  System.currentTimeMillis() ;
        setStop(false);
    }
    public boolean isStopped() {
      return stop;
    }
    public void setStop(boolean stop) {
            this.stop = stop;
    }
    public void stop() {
        setStop(true);
    }
    public void destroy() {
        this.duration = 0;
        setStop(false);
    }

    public void addDuration(int sec) {
        this.duration += (long) sec * milisecond;
    }
}
