package org.alphacreative.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
    private boolean[] keys = new boolean[256];

    // Definición de todas las teclas
    public static boolean W, S, A, D, SPACE, SHIFT, CTRL, O;
    public static boolean UP, DOWN, LEFT, RIGHT;
    public static boolean Q, E, R, F, Z, X, C, V, B, N, M, L, K, J, H, G, Y, U, I, P, T;
    public static boolean ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO;
    public static boolean ENTER, BACK_SPACE, TAB, ESCAPE, DELETE, END, HOME, INSERT, PAGE_UP, PAGE_DOWN;

    public KeyBoard() {
        // Inicialización de todas las teclas
        W = S = A = D = SPACE = SHIFT = CTRL = O = false;
        UP = DOWN = LEFT = RIGHT = false;
        Q = E = R = F = Z = X = C = V = B = N = M = L = K = J = H = G = Y = U = I = P = T = false;
        ONE = TWO = THREE = FOUR = FIVE = SIX = SEVEN = EIGHT = NINE = ZERO = false;
        ENTER = BACK_SPACE = TAB = ESCAPE = DELETE = END = HOME = INSERT = PAGE_UP = PAGE_DOWN = false;
    }

    public void Update() {
        W = keys[KeyEvent.VK_W];
        S = keys[KeyEvent.VK_S];
        A = keys[KeyEvent.VK_A];
        D = keys[KeyEvent.VK_D];
        SPACE = keys[KeyEvent.VK_SPACE];
        SHIFT = keys[KeyEvent.VK_SHIFT];
        CTRL = keys[KeyEvent.VK_CONTROL];
        O = keys[KeyEvent.VK_O];

        UP = keys[KeyEvent.VK_UP];
        DOWN = keys[KeyEvent.VK_DOWN];
        LEFT = keys[KeyEvent.VK_LEFT];
        RIGHT = keys[KeyEvent.VK_RIGHT];

        Q = keys[KeyEvent.VK_Q];
        E = keys[KeyEvent.VK_E];
        R = keys[KeyEvent.VK_R];
        F = keys[KeyEvent.VK_F];
        Z = keys[KeyEvent.VK_Z];
        X = keys[KeyEvent.VK_X];
        C = keys[KeyEvent.VK_C];
        V = keys[KeyEvent.VK_V];
        B = keys[KeyEvent.VK_B];
        N = keys[KeyEvent.VK_N];
        M = keys[KeyEvent.VK_M];
        L = keys[KeyEvent.VK_L];
        K = keys[KeyEvent.VK_K];
        J = keys[KeyEvent.VK_J];
        H = keys[KeyEvent.VK_H];
        G = keys[KeyEvent.VK_G];
        Y = keys[KeyEvent.VK_Y];
        U = keys[KeyEvent.VK_U];
        I = keys[KeyEvent.VK_I];
        P = keys[KeyEvent.VK_P];
        T = keys[KeyEvent.VK_T];

        ONE = keys[KeyEvent.VK_1];
        TWO = keys[KeyEvent.VK_2];
        THREE = keys[KeyEvent.VK_3];
        FOUR = keys[KeyEvent.VK_4];
        FIVE = keys[KeyEvent.VK_5];
        SIX = keys[KeyEvent.VK_6];
        SEVEN = keys[KeyEvent.VK_7];
        EIGHT = keys[KeyEvent.VK_8];
        NINE = keys[KeyEvent.VK_9];
        ZERO = keys[KeyEvent.VK_0];

        ENTER = keys[KeyEvent.VK_ENTER];
        BACK_SPACE = keys[KeyEvent.VK_BACK_SPACE];
        TAB = keys[KeyEvent.VK_TAB];
        ESCAPE = keys[KeyEvent.VK_ESCAPE];
        DELETE = keys[KeyEvent.VK_DELETE];
        END = keys[KeyEvent.VK_END];
        HOME = keys[KeyEvent.VK_HOME];
        INSERT = keys[KeyEvent.VK_INSERT];
        PAGE_UP = keys[KeyEvent.VK_PAGE_UP];
        PAGE_DOWN = keys[KeyEvent.VK_PAGE_DOWN];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("Key Pressed: " + e.getKeyCode());
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("Key Unpressed: " + e.getKeyCode());
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
