package com.XB_Accounts;


import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class Custom_HindiKeyboard extends InputMethodService implements
		OnKeyboardActionListener {

	private KeyboardView kv;
	private Keyboard keyboard;
	private Keyboard keyboardcaps;
	private boolean caps = false;

	// private boolean Selected;
	@SuppressLint("InflateParams") @Override
	public View onCreateInputView() {
		// Selected = true;
		kv = (KeyboardView) getLayoutInflater()
				.inflate(R.layout.keyboard, null);
		keyboard = new Keyboard(this, R.xml.qwerty);
		keyboardcaps = new Keyboard(this, R.xml.qwertycaps);
		kv.setKeyboard(keyboard);
		kv.setOnKeyboardActionListener(this);
		return kv;

		// return super.onCreateInputView();
	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub
		// Selected = true;
	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub
		// Selected = false;
		InputConnection ic = getCurrentInputConnection();
		switch (primaryCode) {
		case Keyboard.KEYCODE_DELETE:
			ic.deleteSurroundingText(1, 0);
			break;
		case Keyboard.KEYCODE_SHIFT:
			caps = !caps;

			// kv.invalidateAllKeys();
			// if(Selected==true){
			// int i = KeyboardView.generateViewId();

			Keyboard key = kv.getKeyboard();
			if (key == keyboard) {
				kv.setKeyboard(keyboardcaps);
			 } else {
				kv.setKeyboard(keyboard);
				kv.setShifted(caps);
			}
			break;
		case Keyboard.KEYCODE_DONE:
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
					KeyEvent.KEYCODE_ENTER));
			break;
		default:
			char code = (char) primaryCode;
			if (Character.isLetter(code) && caps) {
				code = Character.toUpperCase(code);
			}
			ic.commitText(String.valueOf(code), 1);
		}
	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

}
