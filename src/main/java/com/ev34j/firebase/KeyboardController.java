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
    final JLabel actionLabel = new JLabel();
    final JLabel steeringLabel = new JLabel();
    final JLabel power1Label = new JLabel();
    final JLabel power2Label = new JLabel();
    final JLabel position1Label = new JLabel();
    final JLabel position2Label = new JLabel();

    keyPressed.setText(" " + KEY_PRESSED_PREFIX);
    actionLabel.setText(" " + ACTION_PREFIX);
    steeringLabel.setText(" " + STEERING_PREFIX);
    power1Label.setText(" " + POWER1_PREFIX);
    power2Label.setText(" " + POWER2_PREFIX);
    position1Label.setText(" " + POSITION1_PREFIX);
    position2Label.setText(" " + POSITION2_PREFIX);

    panel.add(keyPressed);
    panel.add(actionLabel);
    panel.add(steeringLabel);
    panel.add(power1Label);
    panel.add(power2Label);
    panel.add(position1Label);
    panel.add(position2Label);

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
                              System.err.println(format("Data not writter: %s", error.getMessage()));
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
                          steeringLabel.setText(format(" %s %s", STEERING_PREFIX, value));
                          break;
                        case POWER1:
                          power1Label.setText(format(" %s %s", POWER1_PREFIX, value));
                          break;
                        case POWER2:
                          power2Label.setText(format(" %s %s", POWER2_PREFIX, value));
                          break;
                        case POSITION1:
                          position1Label.setText(format(" %s %s", POSITION1_PREFIX, value));
                          break;
                        case POSITION2:
                          position2Label.setText(format(" %s %s", POSITION2_PREFIX, value));
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
                    final RobotAction action = dataSnapshot.getValue(RobotAction.class);
                    if (action != null)
                      actionLabel.setText(format(" %s %s", ACTION_PREFIX, action.getAction()));
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
