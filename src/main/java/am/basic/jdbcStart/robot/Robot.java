package am.basic.jdbcStart.robot;

import am.basic.jdbcStart.robot.foot.Foot;
import am.basic.jdbcStart.robot.hand.Hand;
import am.basic.jdbcStart.robot.head.Head;

public class Robot {


    private Foot foot;

    private Head head;

    private Hand hand;


    public Robot(Foot foot, Head head, Hand hand) {
        this.foot = foot;
        this.head = head;
        this.hand = hand;
    }

    public void hit() {
        hand.hit();
    }


    public void speak() {
        head.speak();
    }


    public void move() {
        foot.move();
    }
}
