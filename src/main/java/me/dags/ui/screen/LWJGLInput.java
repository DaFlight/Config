package me.dags.ui.screen;

import me.dags.ui.platform.Input;
import org.lwjgl.input.Keyboard;

/**
 * @author dags <dags@dags.me>
 */
public class LWJGLInput implements Input {

    @Override
    public boolean isEscape(int i) {
        return i == Keyboard.KEY_ESCAPE;
    }

    @Override
    public boolean isReturn(int i) {
        return i == Keyboard.KEY_RETURN;
    }

    @Override
    public boolean isBackspace(int i) {
        return i == Keyboard.KEY_BACK;
    }

    @Override
    public boolean isDelete(int i) {
        return i == Keyboard.KEY_DECIMAL;
    }

    @Override
    public boolean isLeft(int i) {
        return i == Keyboard.KEY_LEFT;
    }

    @Override
    public boolean isRight(int i) {
        return i == Keyboard.KEY_RIGHT;
    }

    @Override
    public boolean isUp(int i) {
        return i == Keyboard.KEY_UP;
    }

    @Override
    public boolean isDown(int i) {
        return i == Keyboard.KEY_DOWN;
    }

    @Override
    public boolean isUndefined(char c) {
        return c == Keyboard.CHAR_NONE;
    }

    @Override
    public String keyName(int i) {
        return Keyboard.getKeyName(i);
    }

    @Override
    public int keyCode(char c) {
        return 0;
    }

    @Override
    public int shiftCode() {
        return Keyboard.KEY_LSHIFT;
    }

    @Override
    public int mouseOffset() {
        return 1;
    }
}
