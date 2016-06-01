# ev34j-mindstorms Firebase Demo

[![Build Status](https://travis-ci.org/ev34j/ev34j-mindstorms-firebase.svg?branch=master)](https://travis-ci.org/ev34j/ev34j-mindstorms-firebase)

## System setup

The setup is the same as the
[ev34j-mindstorms-tutorial repo](https://github.com/ev34j/ev34j-mindstorms-tutorial#system-setup).

## Scenario

In this demo, a robot is controlled by keyboard commands via Firebase messages. Similarly,
the robot reports its metrics back to an app via Firebase.

## Running the app

Run the robot app with:

```bash
$ # Build the jars
$ make clean build
$ # Copy it to the EV3
$ make scp
$ # Run the app on the EV3
$ make run
```

Run the keyboard controller with:
```bash
$ # Build the uber-jars
$ make clean build
$ java -jar target/keyboardcontroller-jar-with-dependencies.jar
```

## Keyboard commands

The robot is controlled with these keystrokes:

| Keystroke            | Action                          |
|:---------------------|:--------------------------------|
| Up-Arrow             | Increase power by 10%           |
| Down-Arrow           | Decrease power by 10%           |
| Left-Arrow           | Increase steering by 10%        |
| Right-Arrow          | Decrease steering right by 10%  |
| Shift-Up-Arrow       | Increase power by 20%           |
| Shift-Down-Arrow     | Decrease power by 20%           |
| Shift-Left-Arrow     | Increase steering by 20%        |
| Shift-Right-Arrow    | Decrease steering right by 20%  |
| s/S                  | Set steering to straight        |
| h/H                  | Halt motors                     |
| r/R                  | Reset motors                    |
| q/Q twice            | Robot app exits                 |


## Warnings

### Slow performance

The robot app startup time on a EV3 is painfully slow.
After starting the app you will hear it say "initialized" after ~35 secs.
Eventually (~3 minutes) it will say "processing keystrokes" when it is ready for input.
The latency for sending the keystrokes and robot metrics is very good.

The slow startup time is due to negotiating the https connection with Firebase.
Performance on a BrickPi+ with a Raspi 2 is much better -- less than 10 secs)

### Zombie processes

If you do not cleanly exit the robot app with the keyboard controller (by typing q q),
you will need to ssh into the EV3 and kill the robot java process manually:

```bash
$ ssh robot@ev3dev
robot@ev3dev:~$ ps -aef | grep java
robot     1069  1067 78 19:32 ?        00:00:54 java -jar firebaserobot-jar-with-dependencies.jar
robot     1108  1101  0 19:34 pts/0    00:00:00 grep java
robot@ev3dev:~$ kill -9 1069
robot@ev3dev:~$ # Confirm process was stopped
robot@ev3dev:~$ ps -aef | grep java
robot     1108  1101  0 19:34 pts/0    00:00:00 grep java
```

In this case the robot java process PID was 1069.