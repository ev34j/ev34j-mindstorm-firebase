# EV34J Mindstorm Firebase Example

## System setup

The setup is the same as described in the
[Ev34J Mindstorm Tutorial](https://github.com/ev34j/ev34j-mindstorm-tutorial#system-setup).

## Running the app

Run the robot with:

```bash
$ # Build the uber-jars
$ make clean build
$ # Copy it to your EV3
$ make scp
$ # Run the app on your EV3
$ make run

```

Run the keyboard controller with:
```bash
$ # Build the uber-jars
$ java -jar target/keyboardcontroller-jar-with-dependencies.jar
```

## Keyboard commands

The robot is controlled with these keystrokes:

| Keystoke             | Action                          |
| -------------------- | ------------------------------- |
| Up-Arrow             | Increase power by 10%           |
| Down-Arrow           | Decrease power by 10%           |
| Left-Arrow           | Increase steering by 10%        |
| Right-Arrow          | Decrease steering right by 10%  |
| Shift-Up-Arrow       | Increase power by 20%           |
| Shift-Down-Arrow     | Decrease power by 20%           |
| Shift-Left-Arrow     | Increase steering by 20%        |
| Shift-Right-Arrow    | Decrease steering right by 20%  |
| s or S               | Steering set to straight        |
| h or H               | Halts motors                    |
| r or R               | Reset motors                    |
| x or X twice         | Robot exits                     |


