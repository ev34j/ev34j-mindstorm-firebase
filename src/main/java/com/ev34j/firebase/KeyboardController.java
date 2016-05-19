package com.ev34j.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import static com.ev34j.firebase.Constants.ACTION;
import static com.ev34j.firebase.Constants.DEFAULT_ROBOT;
import static com.ev34j.firebase.Constants.METRICS;
import static com.ev34j.firebase.Constants.POSITION1;
import static com.ev34j.firebase.Constants.POSITION2;
import static com.ev34j.firebase.Constants.POWER1;
import static com.ev34j.firebase.Constants.POWER2;
import static com.ev34j.firebase.Constants.STEERING;
import static java.lang.String.format;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class KeyboardController {

  private static final String KEY_PRESSED_PREFIX = "Key pressed:";
  private static final String ACTION_PREFIX      = "Action:";
  private static final String STEERING_PREFIX    = "Steering:";
  private static final String POWER1_PREFIX      = "Power1:";
  private static final String POWER2_PREFIX      = "Power2:";
  private static final String POSITION1_PREFIX   = "Position1:";
  private static final String POSITION2_PREFIX   = "Position2:";

  public static void main(final String[] args) {

    final Firebase firebase = new Firebase(Constants.FIREBASE_URL);

    final JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(10, 1, 5, 5));

    final JLabel keyPressed = new JLabel();
    final JLabel action = new JLabel();
    final JLabel steering = new JLabel();
    final JLabel power1 = new JLabel();
    final JLabel power2 = new JLabel();
    final JLabel position1 = new JLabel();
    final JLabel position2 = new JLabel();

    keyPressed.setText(" " + KEY_PRESSED_PREFIX);
    action.setText(" " + ACTION_PREFIX);
    steering.setText(" " + STEERING_PREFIX);
    power1.setText(" " + POWER1_PREFIX);
    power2.setText(" " + POWER2_PREFIX);
    position1.setText(" " + POSITION1_PREFIX);
    position2.setText(" " + POSITION2_PREFIX);

    panel.add(keyPressed);
    panel.add(action);
    panel.add(steering);
    panel.add(power1);
    panel.add(power2);
    panel.add(position1);
    panel.add(position2);

    for (final KeyType type : KeyType.values()) {
      panel.getActionMap()
           .put(type,
                new AbstractAction() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    keyPressed.setText(format(" %s %s", KEY_PRESSED_PREFIX, type.name()));
                    final Firebase.CompletionListener listener =
                        new Firebase.CompletionListener() {
                          @Override
                          public void onComplete(final FirebaseError error, final Firebase firebase) {
                            if (error != null)
                              System.out.println(format("Data not writter: %s", error.getMessage()));
                          }
                        };

                    firebase.getRoot()
                            .child(Constants.DEFAULT_USER)
                            .child(Constants.LAST_KEYSTROKE)
                            .setValue(new KeyboardData(type), listener);
                  }
                });
      panel.getInputMap().put(KeyStroke.getKeyStroke(type.getKeyCode(), type.getModifiers()), type);
    }

    firebase.getRoot()
            .child(DEFAULT_ROBOT)
            .child(METRICS)
            .addValueEventListener(
                new ValueEventListener() {
                  @Override
                  public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                      final String metric = child.getKey();
                      final int value = child.getValue(RobotMetric.class).getValue();
                      switch (metric) {
                        case STEERING:
                          steering.setText(format(" %s %s", STEERING_PREFIX, value));
                          break;
                        case POWER1:
                          power1.setText(format(" %s %s", POWER1_PREFIX, value));
                          break;
                        case POWER2:
                          power2.setText(format(" %s %s", POWER2_PREFIX, value));
                          break;
                        case POSITION1:
                          position1.setText(format(" %s %s", POSITION1_PREFIX, value));
                          break;
                        case POSITION2:
                          position2.setText(format(" %s %s", POSITION2_PREFIX, value));
                          break;
                      }
                    }
                  }

                  @Override
                  public void onCancelled(final FirebaseError error) {
                    System.out.println(String.format("ValueEventListener.onCancelled() : %s", error.getMessage()));
                  }
                });

    firebase.getRoot()
            .child(DEFAULT_ROBOT)
            .child(ACTION)
            .addValueEventListener(
                new ValueEventListener() {
                  @Override
                  public void onDataChange(final DataSnapshot dataSnapshot) {
                    final String val = dataSnapshot.getValue(RobotAction.class).getAction();
                    action.setText(format(" %s %s", ACTION_PREFIX, val));
                  }

                  @Override
                  public void onCancelled(final FirebaseError error) {
                    System.out.println(String.format("ValueEventListener.onCancelled() : %s", error.getMessage()));
                  }
                });

    final JFrame frame = new JFrame();
    frame.getContentPane().add(panel);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(400, 400);
    frame.setVisible(true);
  }
}
