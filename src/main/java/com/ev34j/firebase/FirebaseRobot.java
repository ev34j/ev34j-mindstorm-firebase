package com.ev34j.firebase;

import com.ev34j.core.common.Platform;
import com.ev34j.mindstorm.motor.SteeringMotors;
import com.ev34j.mindstorm.sound.Ev3Sound;
import com.ev34j.mindstorm.utils.Wait;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static com.ev34j.firebase.Constants.ACTION;
import static com.ev34j.firebase.Constants.DEFAULT_ROBOT;
import static com.ev34j.firebase.Constants.DEFAULT_USER;
import static com.ev34j.firebase.Constants.LAST_KEYSTROKE;
import static com.ev34j.firebase.Constants.METRICS;
import static com.ev34j.firebase.Constants.POSITION1;
import static com.ev34j.firebase.Constants.POSITION2;
import static com.ev34j.firebase.Constants.POWER1;
import static com.ev34j.firebase.Constants.POWER2;
import static com.ev34j.firebase.Constants.STEERING;
import static java.lang.String.format;

public class FirebaseRobot {

  public static void main(final String[] args)
      throws InterruptedException {

    final FirebaseRobot robot = new FirebaseRobot();
    robot.waitUntilFinished();

    System.out.println("Exiting");
    System.exit(0);
  }

  private final AtomicLong      startTime = new AtomicLong(-1);
  private final AtomicBoolean   exit      = new AtomicBoolean(false);
  private final CountDownLatch  complete  = new CountDownLatch(1);
  private final ExecutorService executor  = Executors.newSingleThreadExecutor();

  private final Firebase.CompletionListener completionListener =
      new Firebase.CompletionListener() {
        @Override
        public void onComplete(final FirebaseError error, final Firebase firebase) {
          if (error != null)
            System.err.println(format("Data not writter: %s", error.getMessage()));
        }
      };

  private int steering      = 0;
  private int power         = 0;
  private int exitCommand   = 0;
  private int lastSteering  = Integer.MIN_VALUE;
  private int lastPower1    = Integer.MIN_VALUE;
  private int lastPower2    = Integer.MIN_VALUE;
  private int lastPosition1 = Integer.MIN_VALUE;
  private int lastPosition2 = Integer.MIN_VALUE;

  private final SteeringMotors motors;
  private final Firebase       firebase;

  public FirebaseRobot() {
    this.motors = new SteeringMotors("A", "B");
    this.firebase = new Firebase(Constants.FIREBASE_URL);
    this.firebase.getRoot()
                 .child(DEFAULT_USER)
                 .child(LAST_KEYSTROKE)
                 .addValueEventListener(
                     new ValueEventListener() {
                       @Override
                       public void onDataChange(final DataSnapshot dataSnapshot) {
                         final KeyboardData data = dataSnapshot.getValue(KeyboardData.class);
                         if (startTime.get() == -1) {
                           startTime.set(System.currentTimeMillis());
                           if (Platform.isEv3Brick())
                             Ev3Sound.say("Processing keystrokes", 100);
                           System.out.println("Processing keystrokes");
                         }

                         processKeyStroke(data);
                       }

                       @Override
                       public void onCancelled(final FirebaseError error) {
                         System.out.println(String.format("ValueEventListener.onCancelled() : %s", error.getMessage()));
                       }
                     });

    this.executor.execute(
        new Runnable() {
          @Override
          public void run() {
            while (!exit.get()) {
              // Do not send duplicate  values
              final int steering1 = motors.getSteering();
              if (steering1 != lastSteering) {
                reportMetric(STEERING, steering1);
                lastSteering = steering1;
              }

              final int power1 = motors.getPower1();
              if (power1 != lastPower1) {
                reportMetric(POWER1, power1);
                lastPower1 = power1;
              }

              final int power2 = motors.getPower2();
              if (power2 != lastPower2) {
                reportMetric(POWER2, power2);
                lastPower2 = power2;
              }

              final int position1 = motors.getPosition1();
              if (position1 != lastPosition1) {
                reportMetric(POSITION1, position1);
                lastPosition1 = position1;
              }

              final int position2 = motors.getPosition2();
              if (position2 != lastPosition2) {
                reportMetric(POSITION2, position2);
                lastPosition2 = position2;
              }

              Wait.forMillis(500);
            }

            System.out.println("Discontinue metric reporting");
            complete.countDown();
          }
        });

    if (Platform.isEv3Brick())
      Ev3Sound.say("Initialized", 100);
    this.reportAction("Initialized");
    System.out.println("Initialized");
  }

  private void reportMetric(final String metric, final int value) {
    this.firebase
        .getRoot()
        .child(DEFAULT_ROBOT)
        .child(METRICS)
        .child(metric)
        .setValue(new RobotMetric(metric, value), this.completionListener);
  }

  private void reportAction(final String action) {
    this.firebase
        .getRoot()
        .child(DEFAULT_ROBOT)
        .child(ACTION)
        .setValue(new RobotAction(action), this.completionListener);
  }

  private void processKeyStroke(final KeyboardData data) {
    // Prevent acting on keystrokes that occur before startup
    if (data.getTimeStamp() + 5000 < startTime.get())
      return;

    switch (data.getKeyType()) {
      // Exit after two Q presses in a row
      case LOWER_Q:
      case UPPER_Q:
        this.exitCommand++;
        if (this.exitCommand >= 2) {
          this.reportAction("Exiting");
          this.motors.off();
          this.exit.set(true);
        }
        break;
      case LOWER_H:
      case UPPER_H:
        this.power = 0;
        this.reportAction("Halt motors");
        this.updatePower();
        break;
      case LOWER_S:
      case UPPER_S:
        this.steering = 0;
        this.reportAction("Go straight");
        this.updatePower();
        break;
      case LOWER_R:
      case UPPER_R:
        this.steering = 0;
        this.power = 0;
        this.reportAction("Reset motors");
        this.updatePower();
        this.motors.reset();
        break;
      case UP_ARROW:
        if (this.power <= 90) {
          this.power += 10;
          this.reportAction(format("Power up 10%% to %d", this.power));
          this.updatePower();
        }
        break;
      case DOWN_ARROW:
        if (this.power >= -90) {
          this.power -= 10;
          this.reportAction(format("Power down 10%% to %d", this.power));
          this.updatePower();
        }
        break;
      case LEFT_ARROW:
        if (this.steering >= -90) {
          this.steering -= 10;
          this.reportAction(format("Steering up 10%% to %d", this.steering));
          this.updateSteering();
        }
        break;
      case RIGHT_ARROW:
        if (this.steering <= 90) {
          this.steering += 10;
          this.reportAction(format("Steering down 10%% to %d", this.steering));
          this.updateSteering();
        }
        break;
      case SHIFT_UP_ARROW:
        if (this.power <= 80) {
          this.power += 20;
          this.reportAction(format("Power up 20%% to %d", this.power));
          this.updatePower();
        }
        break;
      case SHIFT_DOWN_ARROW:
        if (this.power >= -80) {
          this.power -= 20;
          this.reportAction(format("Power down 20%% to %d", this.power));
          this.updatePower();
        }
        break;
      case SHIFT_LEFT_ARROW:
        if (this.steering >= -80) {
          this.steering -= 20;
          this.reportAction(format("Steering up 20%% to %d", this.steering));
          this.updateSteering();
        }
        break;
      case SHIFT_RIGHT_ARROW:
        if (this.steering <= 80) {
          this.steering += 20;
          this.reportAction(format("Steering down 20%% to %d", this.steering));
          this.updateSteering();
        }
        break;
      default:
        // Ignore other keys
    }
  }

  private void updateSteering() {
    this.exitCommand = 0;
    this.motors.on(this.steering, this.power);
  }

  private void updatePower() {
    this.exitCommand = 0;
    if (this.power == 0)
      this.motors.off();
    else
      this.motors.on(this.steering, this.power);
  }

  private void waitUntilFinished()
      throws InterruptedException {
    this.complete.await();
    this.executor.shutdownNow();
  }
}
