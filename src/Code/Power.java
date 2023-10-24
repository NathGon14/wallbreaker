package Code;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Random;

public class Power {


        private String powers[] = {"large_ball","multiply_ball"};
        public LocalDateTime duration;
    public String currentPower;
    private  int ball_prev_diameter;
        public Random random = new Random();
            public Power(){
            LocalDateTime updatedTime;
            LocalDateTime now = LocalDateTime.now();
            duration =now.plusSeconds(random.nextInt(3,10));
            currentPower =  powers[random.nextInt(0,powers.length-1)];
        }
    public  void  selectPower(){



    }



}
