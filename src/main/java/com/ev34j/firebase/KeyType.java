package com.ev34j.firebase;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_BRACELEFT;
import static java.awt.event.KeyEvent.VK_BRACERIGHT;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SLASH;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;

public enum KeyType {
  LOWER_A(VK_A, 0),
  LOWER_B(VK_B, 0),
  LOWER_C(VK_C, 0),
  LOWER_D(VK_D, 0),
  LOWER_E(VK_E, 0),
  LOWER_F(VK_F, 0),
  LOWER_G(VK_G, 0),
  LOWER_H(VK_H, 0),
  LOWER_I(VK_I, 0),
  LOWER_J(VK_J, 0),
  LOWER_K(VK_K, 0),
  LOWER_L(VK_L, 0),
  LOWER_M(VK_M, 0),
  LOWER_N(VK_N, 0),
  LOWER_O(VK_O, 0),
  LOWER_P(VK_P, 0),
  LOWER_Q(VK_Q, 0),
  LOWER_R(VK_R, 0),
  LOWER_S(VK_S, 0),
  LOWER_T(VK_T, 0),
  LOWER_U(VK_U, 0),
  LOWER_V(VK_V, 0),
  LOWER_W(VK_W, 0),
  LOWER_X(VK_X, 0),
  LOWER_Y(VK_Y, 0),
  LOWER_Z(VK_Z, 0),

  UPPER_A(VK_A, SHIFT_MASK),
  UPPER_B(VK_B, SHIFT_MASK),
  UPPER_C(VK_C, SHIFT_MASK),
  UPPER_D(VK_D, SHIFT_MASK),
  UPPER_E(VK_E, SHIFT_MASK),
  UPPER_F(VK_F, SHIFT_MASK),
  UPPER_G(VK_G, SHIFT_MASK),
  UPPER_H(VK_H, SHIFT_MASK),
  UPPER_I(VK_I, SHIFT_MASK),
  UPPER_J(VK_J, SHIFT_MASK),
  UPPER_K(VK_K, SHIFT_MASK),
  UPPER_L(VK_L, SHIFT_MASK),
  UPPER_M(VK_M, SHIFT_MASK),
  UPPER_N(VK_N, SHIFT_MASK),
  UPPER_O(VK_O, SHIFT_MASK),
  UPPER_P(VK_P, SHIFT_MASK),
  UPPER_Q(VK_Q, SHIFT_MASK),
  UPPER_R(VK_R, SHIFT_MASK),
  UPPER_S(VK_S, SHIFT_MASK),
  UPPER_T(VK_T, SHIFT_MASK),
  UPPER_U(VK_U, SHIFT_MASK),
  UPPER_V(VK_V, SHIFT_MASK),
  UPPER_W(VK_W, SHIFT_MASK),
  UPPER_X(VK_X, SHIFT_MASK),
  UPPER_Y(VK_Y, SHIFT_MASK),
  UPPER_Z(VK_Z, SHIFT_MASK),

  KEY_0(VK_0, 0),
  KEY_1(VK_1, 0),
  KEY_2(VK_2, 0),
  KEY_3(VK_3, 0),
  KEY_4(VK_4, 0),
  KEY_5(VK_5, 0),
  KEY_6(VK_6, 0),
  KEY_7(VK_7, 0),
  KEY_8(VK_8, 0),
  KEY_9(VK_9, 0),

  SPACE(VK_SPACE, 0),
  SLASH(VK_SLASH, 0),
  QUESTION(VK_SLASH, SHIFT_MASK),
  COMMA(VK_COMMA, 0),
  PERIOD(VK_PERIOD, 0),
  LEFT_BRACK(VK_BRACELEFT, 0),
  RIGHT_BRACK(VK_BRACERIGHT, 0),
  LEFT_CURLY(VK_BRACELEFT, SHIFT_MASK),
  RIGHT_CURL(VK_BRACERIGHT, SHIFT_MASK),
  LESS_THAN(VK_COMMA, SHIFT_MASK),
  GREATER_THAN(VK_PERIOD, SHIFT_MASK),
  EQUALS(VK_EQUALS, 0),
  PLUS(VK_EQUALS, SHIFT_MASK),
  MINUS(VK_MINUS, 0),
  UNDERSCORE(VK_MINUS, SHIFT_MASK),
  LEFT_ARROW(VK_LEFT, 0),
  UP_ARROW(VK_UP, 0),
  RIGHT_ARROW(VK_RIGHT, 0),
  DOWN_ARROW(VK_DOWN, 0),
  SHIFT_LEFT_ARROW(VK_LEFT, SHIFT_MASK),
  SHIFT_UP_ARROW(VK_UP, SHIFT_MASK),
  SHIFT_RIGHT_ARROW(VK_RIGHT, SHIFT_MASK),
  SHIFT_DOWN_ARROW(VK_DOWN, SHIFT_MASK);

  private final int keyCode;
  private final int modifiers;

  KeyType(final int keyCode, final int modifiers) {
    this.keyCode = keyCode;
    this.modifiers = modifiers;
  }

  public int getKeyCode() { return this.keyCode; }

  public int getModifiers() { return this.modifiers; }
}
