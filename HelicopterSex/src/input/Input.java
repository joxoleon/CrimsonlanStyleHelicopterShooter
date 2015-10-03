package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Input
extends JPanel
implements KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Input 			input 					= new Input();
	
	private static KeyboardState 	previousKeyboardState 	= new KeyboardState();
	private static KeyboardState 	currentKeyboardState 	= new KeyboardState();
	private static KeyboardState 	nextKeyboardState 		= new KeyboardState();
	
	private static String 			currentKeysTyped 		= new String("");
	private static String 			nextKeysTyped			= new String("");
	
	public 	static Input
	Instance()
	{
		return input;
	}
	
	public static void
	SwitchStates()
	{
		previousKeyboardState = currentKeyboardState;
		currentKeyboardState = nextKeyboardState;
		nextKeyboardState = new KeyboardState(nextKeyboardState);	// Copy constructor.
		
		currentKeysTyped = nextKeysTyped;
		nextKeysTyped = new String();
	}
	
	// Keyboard methods.
	public static boolean
	isKeyDown(Keys key)
	{
		return currentKeyboardState.isKeyDown(key);
	}
	
	public static boolean
	isKeyUp(Keys key)
	{
		return currentKeyboardState.isKeyUp(key);
	}
	
	public static boolean
	isKeyPressed(Keys key)
	{
		return currentKeyboardState.isKeyDown(key) && previousKeyboardState.isKeyUp(key);
	}
	
	public static boolean
	isKeyReleased(Keys key)
	{
		return currentKeyboardState.isKeyUp(key) && previousKeyboardState.isKeyDown(key);
	}
	
	public static String
	getKeysTyped()
	{
		return new String(currentKeysTyped);
	}
	
	
	
	private
	Input()
	{
	}

	@Override
	public void
	keyPressed(KeyEvent event)
	{
		String key = KeyEvent.getKeyText(event.getKeyCode());
		
		if (key.length() == 1)
		{
			boolean isUpper = (key.charAt(0) >= 'A' && key.charAt(0) <= 'Z');
			boolean isLower = (key.charAt(0) >= 'a' && key.charAt(0) <= 'z');
			boolean isDigit = (key.charAt(0) >= '0' && key.charAt(0) <= '9');
			
			if (isUpper || isLower || isDigit)
			{
				nextKeysTyped += key;
			}
		}
		
		switch (event.getKeyCode())
        {
            case KeyEvent.VK_A:
            {
                nextKeyboardState.keyStates.remove(Keys.A);
                nextKeyboardState.keyStates.put(Keys.A, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_ADD:
            {
                nextKeyboardState.keyStates.remove(Keys.Add);
                nextKeyboardState.keyStates.put(Keys.Add, KeyState.KEY_DOWN);
            } break;
               
            case KeyEvent.VK_B:
            {
                nextKeyboardState.keyStates.remove(Keys.B);
                nextKeyboardState.keyStates.put(Keys.B, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_BACK_SPACE:
            {
                nextKeyboardState.keyStates.remove(Keys.Back);
                nextKeyboardState.keyStates.put(Keys.Back, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_C:
            {
                nextKeyboardState.keyStates.remove(Keys.C);
                nextKeyboardState.keyStates.put(Keys.C, KeyState.KEY_DOWN);
            } break;
            
            case KeyEvent.VK_CAPS_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.CapsLock);
                nextKeyboardState.keyStates.put(Keys.CapsLock, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_D:
            {
                nextKeyboardState.keyStates.remove(Keys.D);
                nextKeyboardState.keyStates.put(Keys.D, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_0:
            {
                nextKeyboardState.keyStates.remove(Keys.D0);
                nextKeyboardState.keyStates.put(Keys.D0, KeyState.KEY_DOWN);
            } break;
                    
            case KeyEvent.VK_1:
            {
                nextKeyboardState.keyStates.remove(Keys.D1);
                nextKeyboardState.keyStates.put(Keys.D1, KeyState.KEY_DOWN);
            } break;
                        
            case KeyEvent.VK_2:
            {
                nextKeyboardState.keyStates.remove(Keys.D2);
                nextKeyboardState.keyStates.put(Keys.D2, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_3:
            {
                nextKeyboardState.keyStates.remove(Keys.D3);
                nextKeyboardState.keyStates.put(Keys.D3, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_4:
            {
                nextKeyboardState.keyStates.remove(Keys.D4);
                nextKeyboardState.keyStates.put(Keys.D4, KeyState.KEY_DOWN);
            } break;
            
            case KeyEvent.VK_5:
            {
                nextKeyboardState.keyStates.remove(Keys.D5);
                nextKeyboardState.keyStates.put(Keys.D5, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_6:
            {
                nextKeyboardState.keyStates.remove(Keys.D6);
                nextKeyboardState.keyStates.put(Keys.D6, KeyState.KEY_DOWN);
            } break;
                        
            case KeyEvent.VK_7:
            {
                nextKeyboardState.keyStates.remove(Keys.D7);
                nextKeyboardState.keyStates.put(Keys.D7, KeyState.KEY_DOWN);
            } break;
                                
            case KeyEvent.VK_8:
            {
                nextKeyboardState.keyStates.remove(Keys.D8);
                nextKeyboardState.keyStates.put(Keys.D8, KeyState.KEY_DOWN);
            } break;
                                        
            case KeyEvent.VK_9:
            {
                nextKeyboardState.keyStates.remove(Keys.D9);
                nextKeyboardState.keyStates.put(Keys.D9, KeyState.KEY_DOWN);
            } break;
                                                
            case KeyEvent.VK_DELETE:
            {
                nextKeyboardState.keyStates.remove(Keys.Delete);
                nextKeyboardState.keyStates.put(Keys.Delete, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_DIVIDE:
            {
                nextKeyboardState.keyStates.remove(Keys.Divide);
                nextKeyboardState.keyStates.put(Keys.Divide, KeyState.KEY_DOWN);
            } break;
                    
            case KeyEvent.VK_DOWN:
            {
                nextKeyboardState.keyStates.remove(Keys.Down);
                nextKeyboardState.keyStates.put(Keys.Down, KeyState.KEY_DOWN);
            } break;
                        
            case KeyEvent.VK_E:
            {
                nextKeyboardState.keyStates.remove(Keys.E);
                nextKeyboardState.keyStates.put(Keys.E, KeyState.KEY_DOWN);
            } break;
                            
            case KeyEvent.VK_END:
            {
                nextKeyboardState.keyStates.remove(Keys.End);
                nextKeyboardState.keyStates.put(Keys.End, KeyState.KEY_DOWN);
            } break;
                                
            case KeyEvent.VK_ENTER:
            {
                nextKeyboardState.keyStates.remove(Keys.Enter);
                nextKeyboardState.keyStates.put(Keys.Enter, KeyState.KEY_DOWN);
            } break;
                                    
            case KeyEvent.VK_ESCAPE:
            {
                nextKeyboardState.keyStates.remove(Keys.Escape);
                nextKeyboardState.keyStates.put(Keys.Escape, KeyState.KEY_DOWN);
            } break;
                                        
            case KeyEvent.VK_F:
            {
                nextKeyboardState.keyStates.remove(Keys.F);
                nextKeyboardState.keyStates.put(Keys.F, KeyState.KEY_DOWN);
            } break;
            
            case KeyEvent.VK_F1:
            {
                nextKeyboardState.keyStates.remove(Keys.F1);
                nextKeyboardState.keyStates.put(Keys.F1, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F2:
            {
                nextKeyboardState.keyStates.remove(Keys.F2);
                nextKeyboardState.keyStates.put(Keys.F2, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F3:
            {
                nextKeyboardState.keyStates.remove(Keys.F3);
                nextKeyboardState.keyStates.put(Keys.F3, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F4:
            {
                nextKeyboardState.keyStates.remove(Keys.F4);
                nextKeyboardState.keyStates.put(Keys.F4, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F5:
            {
                nextKeyboardState.keyStates.remove(Keys.F5);
                nextKeyboardState.keyStates.put(Keys.F5, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F6:
            {
                nextKeyboardState.keyStates.remove(Keys.F6);
                nextKeyboardState.keyStates.put(Keys.F6, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F7:
            {
                nextKeyboardState.keyStates.remove(Keys.F7);
                nextKeyboardState.keyStates.put(Keys.F7, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F8:
            {
                nextKeyboardState.keyStates.remove(Keys.F8);
                nextKeyboardState.keyStates.put(Keys.F8, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F9:
            {
                nextKeyboardState.keyStates.remove(Keys.F9);
                nextKeyboardState.keyStates.put(Keys.F9, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F10:
            {
                nextKeyboardState.keyStates.remove(Keys.F10);
                nextKeyboardState.keyStates.put(Keys.F10, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F11:
            {
                nextKeyboardState.keyStates.remove(Keys.F11);
                nextKeyboardState.keyStates.put(Keys.F11, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_F12:
            {
                nextKeyboardState.keyStates.remove(Keys.F12);
                nextKeyboardState.keyStates.put(Keys.F12, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_G:
            {
                nextKeyboardState.keyStates.remove(Keys.G);
                nextKeyboardState.keyStates.put(Keys.G, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_H:
            {
                nextKeyboardState.keyStates.remove(Keys.H);
                nextKeyboardState.keyStates.put(Keys.H, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_HOME:
            {
                nextKeyboardState.keyStates.remove(Keys.Home);
                nextKeyboardState.keyStates.put(Keys.Home, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_I:
            {
                nextKeyboardState.keyStates.remove(Keys.I);
                nextKeyboardState.keyStates.put(Keys.I, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_INSERT:
            {
                nextKeyboardState.keyStates.remove(Keys.Insert);
                nextKeyboardState.keyStates.put(Keys.Insert, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_J:
            {
                nextKeyboardState.keyStates.remove(Keys.J);
                nextKeyboardState.keyStates.put(Keys.J, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_K:
            {
                nextKeyboardState.keyStates.remove(Keys.K);
                nextKeyboardState.keyStates.put(Keys.K, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_L:
            {
                nextKeyboardState.keyStates.remove(Keys.L);
                nextKeyboardState.keyStates.put(Keys.L, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_LEFT:
            {
                nextKeyboardState.keyStates.remove(Keys.Left);
                nextKeyboardState.keyStates.put(Keys.Left, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_ALT:
            {
                nextKeyboardState.keyStates.remove(Keys.LeftAlt);
                nextKeyboardState.keyStates.put(Keys.LeftAlt, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_CONTROL:
            {
                if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
                {
                    nextKeyboardState.keyStates.remove(Keys.LeftControl);
                    nextKeyboardState.keyStates.put(Keys.LeftControl, KeyState.KEY_DOWN);
                }
                else if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
                {
                    nextKeyboardState.keyStates.remove(Keys.RightControl);
                    nextKeyboardState.keyStates.put(Keys.RightControl, KeyState.KEY_DOWN);
                }
            } break;
                
            case KeyEvent.VK_SHIFT:
            {
                if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
                {
                    nextKeyboardState.keyStates.remove(Keys.LeftShift);
                    nextKeyboardState.keyStates.put(Keys.LeftShift, KeyState.KEY_DOWN);
                }
                else if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
                {
                    nextKeyboardState.keyStates.remove(Keys.RightShift);
                    nextKeyboardState.keyStates.put(Keys.RightShift, KeyState.KEY_DOWN);
                }
            } break;
                
            case KeyEvent.VK_M:
            {
                nextKeyboardState.keyStates.remove(Keys.M);
                nextKeyboardState.keyStates.put(Keys.M, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_MULTIPLY:
            {
                nextKeyboardState.keyStates.remove(Keys.Multiply);
                nextKeyboardState.keyStates.put(Keys.Multiply, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_N:
            {
                nextKeyboardState.keyStates.remove(Keys.N);
                nextKeyboardState.keyStates.put(Keys.N, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUM_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.NumLock);
                nextKeyboardState.keyStates.put(Keys.NumLock, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD0:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad0);
                nextKeyboardState.keyStates.put(Keys.NumPad0, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD1:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad1);
                nextKeyboardState.keyStates.put(Keys.NumPad1, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD2:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad2);
                nextKeyboardState.keyStates.put(Keys.NumPad2, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD3:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad3);
                nextKeyboardState.keyStates.put(Keys.NumPad3, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD4:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad4);
                nextKeyboardState.keyStates.put(Keys.NumPad4, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD5:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad5);
                nextKeyboardState.keyStates.put(Keys.NumPad5, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD6:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad6);
                nextKeyboardState.keyStates.put(Keys.NumPad6, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD7:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad7);
                nextKeyboardState.keyStates.put(Keys.NumPad7, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD8:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad8);
                nextKeyboardState.keyStates.put(Keys.NumPad8, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_NUMPAD9:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad9);
                nextKeyboardState.keyStates.put(Keys.NumPad9, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_O:
            {
                nextKeyboardState.keyStates.remove(Keys.O);
                nextKeyboardState.keyStates.put(Keys.O, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_P:
            {
                nextKeyboardState.keyStates.remove(Keys.P);
                nextKeyboardState.keyStates.put(Keys.P, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_PAGE_DOWN:
            {
                nextKeyboardState.keyStates.remove(Keys.PageDown);
                nextKeyboardState.keyStates.put(Keys.PageDown, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_PAGE_UP:
            {
                nextKeyboardState.keyStates.remove(Keys.PageUp);
                nextKeyboardState.keyStates.put(Keys.PageUp, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_PAUSE:
            {
                nextKeyboardState.keyStates.remove(Keys.Pause);
                nextKeyboardState.keyStates.put(Keys.Pause, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_PRINTSCREEN:
            {
                nextKeyboardState.keyStates.remove(Keys.PrintScreen);
                nextKeyboardState.keyStates.put(Keys.PrintScreen, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_Q:
            {
                nextKeyboardState.keyStates.remove(Keys.Q);
                nextKeyboardState.keyStates.put(Keys.Q, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_R:
            {
                nextKeyboardState.keyStates.remove(Keys.R);
                nextKeyboardState.keyStates.put(Keys.R, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_RIGHT:
            {
                nextKeyboardState.keyStates.remove(Keys.Right);
                nextKeyboardState.keyStates.put(Keys.Right, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_S:
            {
                nextKeyboardState.keyStates.remove(Keys.S);
                nextKeyboardState.keyStates.put(Keys.S, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_SCROLL_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.Scroll);
                nextKeyboardState.keyStates.put(Keys.Scroll, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_SPACE:
            {
                nextKeyboardState.keyStates.remove(Keys.Space);
                nextKeyboardState.keyStates.put(Keys.Space, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_SUBTRACT:
            {
                nextKeyboardState.keyStates.remove(Keys.Subtract);
                nextKeyboardState.keyStates.put(Keys.Subtract, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_T:
            {
                nextKeyboardState.keyStates.remove(Keys.T);
                nextKeyboardState.keyStates.put(Keys.T, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_TAB:
            {
                nextKeyboardState.keyStates.remove(Keys.Tab);
                nextKeyboardState.keyStates.put(Keys.Tab, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_U:
            {
                nextKeyboardState.keyStates.remove(Keys.U);
                nextKeyboardState.keyStates.put(Keys.U, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_UP:
            {
                nextKeyboardState.keyStates.remove(Keys.Up);
                nextKeyboardState.keyStates.put(Keys.Up, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_V:
            {
                nextKeyboardState.keyStates.remove(Keys.V);
                nextKeyboardState.keyStates.put(Keys.V, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_W:
            {
                nextKeyboardState.keyStates.remove(Keys.W);
                nextKeyboardState.keyStates.put(Keys.W, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_X:
            {
                nextKeyboardState.keyStates.remove(Keys.X);
                nextKeyboardState.keyStates.put(Keys.X, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_Y:
            {
                nextKeyboardState.keyStates.remove(Keys.Y);
                nextKeyboardState.keyStates.put(Keys.Y, KeyState.KEY_DOWN);
            } break;
                
            case KeyEvent.VK_Z:
            {
                nextKeyboardState.keyStates.remove(Keys.Z);
                nextKeyboardState.keyStates.put(Keys.Z, KeyState.KEY_DOWN);
            } break;
                
            default:
                break;
        }
	}

	@Override
	public void
	keyReleased(KeyEvent event)
	{
		switch (event.getKeyCode())
        {
            case KeyEvent.VK_A:
            {
                nextKeyboardState.keyStates.remove(Keys.A);
                nextKeyboardState.keyStates.put(Keys.A, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_ADD:
            {
                nextKeyboardState.keyStates.remove(Keys.Add);
                nextKeyboardState.keyStates.put(Keys.Add, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_B:
            {
                nextKeyboardState.keyStates.remove(Keys.B);
                nextKeyboardState.keyStates.put(Keys.B, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_BACK_SPACE:
            {
                nextKeyboardState.keyStates.remove(Keys.Back);
                nextKeyboardState.keyStates.put(Keys.Back, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_C:
            {
                nextKeyboardState.keyStates.remove(Keys.C);
                nextKeyboardState.keyStates.put(Keys.C, KeyState.KEY_UP);
            } break;
            
            case KeyEvent.VK_CAPS_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.CapsLock);
                nextKeyboardState.keyStates.put(Keys.CapsLock, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_D:
            {
                nextKeyboardState.keyStates.remove(Keys.D);
                nextKeyboardState.keyStates.put(Keys.D, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_0:
            {
                nextKeyboardState.keyStates.remove(Keys.D0);
                nextKeyboardState.keyStates.put(Keys.D0, KeyState.KEY_UP);
            } break;
                    
            case KeyEvent.VK_1:
            {
                nextKeyboardState.keyStates.remove(Keys.D1);
                nextKeyboardState.keyStates.put(Keys.D1, KeyState.KEY_UP);
            } break;
                        
            case KeyEvent.VK_2:
            {
                nextKeyboardState.keyStates.remove(Keys.D2);
                nextKeyboardState.keyStates.put(Keys.D2, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_3:
            {
                nextKeyboardState.keyStates.remove(Keys.D3);
                nextKeyboardState.keyStates.put(Keys.D3, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_4:
            {
                nextKeyboardState.keyStates.remove(Keys.D4);
                nextKeyboardState.keyStates.put(Keys.D4, KeyState.KEY_UP);
            } break;
            
            case KeyEvent.VK_5:
            {
                nextKeyboardState.keyStates.remove(Keys.D5);
                nextKeyboardState.keyStates.put(Keys.D5, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_6:
            {
                nextKeyboardState.keyStates.remove(Keys.D6);
                nextKeyboardState.keyStates.put(Keys.D6, KeyState.KEY_UP);
            } break;
                        
            case KeyEvent.VK_7:
            {
                nextKeyboardState.keyStates.remove(Keys.D7);
                nextKeyboardState.keyStates.put(Keys.D7, KeyState.KEY_UP);
            } break;
                                
            case KeyEvent.VK_8:
            {
                nextKeyboardState.keyStates.remove(Keys.D8);
                nextKeyboardState.keyStates.put(Keys.D8, KeyState.KEY_UP);
            } break;
                                        
            case KeyEvent.VK_9:
            {
                nextKeyboardState.keyStates.remove(Keys.D9);
                nextKeyboardState.keyStates.put(Keys.D9, KeyState.KEY_UP);
            } break;
                                                
            case KeyEvent.VK_DELETE:
            {
                nextKeyboardState.keyStates.remove(Keys.Delete);
                nextKeyboardState.keyStates.put(Keys.Delete, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_DIVIDE:
            {
                nextKeyboardState.keyStates.remove(Keys.Divide);
                nextKeyboardState.keyStates.put(Keys.Divide, KeyState.KEY_UP);
            } break;
                    
            case KeyEvent.VK_DOWN:
            {
                nextKeyboardState.keyStates.remove(Keys.Down);
                nextKeyboardState.keyStates.put(Keys.Down, KeyState.KEY_UP);
            } break;
                        
            case KeyEvent.VK_E:
            {
                nextKeyboardState.keyStates.remove(Keys.E);
                nextKeyboardState.keyStates.put(Keys.E, KeyState.KEY_UP);
            } break;
                            
            case KeyEvent.VK_END:
            {
                nextKeyboardState.keyStates.remove(Keys.End);
                nextKeyboardState.keyStates.put(Keys.End, KeyState.KEY_UP);
            } break;
                                
            case KeyEvent.VK_ENTER:
            {
                nextKeyboardState.keyStates.remove(Keys.Enter);
                nextKeyboardState.keyStates.put(Keys.Enter, KeyState.KEY_UP);
            } break;
                                    
            case KeyEvent.VK_ESCAPE:
            {
                nextKeyboardState.keyStates.remove(Keys.Escape);
                nextKeyboardState.keyStates.put(Keys.Escape, KeyState.KEY_UP);
            } break;
                                        
            case KeyEvent.VK_F:
            {
                nextKeyboardState.keyStates.remove(Keys.F);
                nextKeyboardState.keyStates.put(Keys.F, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F1:
            {
                nextKeyboardState.keyStates.remove(Keys.F1);
                nextKeyboardState.keyStates.put(Keys.F1, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F2:
            {
                nextKeyboardState.keyStates.remove(Keys.F2);
                nextKeyboardState.keyStates.put(Keys.F2, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F3:
            {
                nextKeyboardState.keyStates.remove(Keys.F3);
                nextKeyboardState.keyStates.put(Keys.F3, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F4:
            {
                nextKeyboardState.keyStates.remove(Keys.F4);
                nextKeyboardState.keyStates.put(Keys.F4, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F5:
            {
                nextKeyboardState.keyStates.remove(Keys.F5);
                nextKeyboardState.keyStates.put(Keys.F5, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F6:
            {
                nextKeyboardState.keyStates.remove(Keys.F6);
                nextKeyboardState.keyStates.put(Keys.F6, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F7:
            {
                nextKeyboardState.keyStates.remove(Keys.F7);
                nextKeyboardState.keyStates.put(Keys.F7, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F8:
            {
                nextKeyboardState.keyStates.remove(Keys.F8);
                nextKeyboardState.keyStates.put(Keys.F8, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F9:
            {
                nextKeyboardState.keyStates.remove(Keys.F9);
                nextKeyboardState.keyStates.put(Keys.F9, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F10:
            {
                nextKeyboardState.keyStates.remove(Keys.F10);
                nextKeyboardState.keyStates.put(Keys.F10, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F11:
            {
                nextKeyboardState.keyStates.remove(Keys.F11);
                nextKeyboardState.keyStates.put(Keys.F11, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_F12:
            {
                nextKeyboardState.keyStates.remove(Keys.F12);
                nextKeyboardState.keyStates.put(Keys.F12, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_G:
            {
                nextKeyboardState.keyStates.remove(Keys.F12);
                nextKeyboardState.keyStates.put(Keys.F12, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_H:
            {
                nextKeyboardState.keyStates.remove(Keys.F12);
                nextKeyboardState.keyStates.put(Keys.F12, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_HOME:
            {
                nextKeyboardState.keyStates.remove(Keys.F12);
                nextKeyboardState.keyStates.put(Keys.F12, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_I:
            {
                nextKeyboardState.keyStates.remove(Keys.I);
                nextKeyboardState.keyStates.put(Keys.I, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_INSERT:
            {
                nextKeyboardState.keyStates.remove(Keys.Insert);
                nextKeyboardState.keyStates.put(Keys.Insert, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_J:
            {
                nextKeyboardState.keyStates.remove(Keys.J);
                nextKeyboardState.keyStates.put(Keys.J, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_K:
            {
                nextKeyboardState.keyStates.remove(Keys.K);
                nextKeyboardState.keyStates.put(Keys.K, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_L:
            {
                nextKeyboardState.keyStates.remove(Keys.L);
                nextKeyboardState.keyStates.put(Keys.L, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_LEFT:
            {
                nextKeyboardState.keyStates.remove(Keys.Left);
                nextKeyboardState.keyStates.put(Keys.Left, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_ALT:
            {
                nextKeyboardState.keyStates.remove(Keys.LeftAlt);
                nextKeyboardState.keyStates.put(Keys.LeftAlt, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_CONTROL:
            {
                if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
                {
                    nextKeyboardState.keyStates.remove(Keys.LeftControl);
                    nextKeyboardState.keyStates.put(Keys.LeftControl, KeyState.KEY_UP);
                }
                else if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
                {
                    nextKeyboardState.keyStates.remove(Keys.RightControl);
                    nextKeyboardState.keyStates.put(Keys.RightControl, KeyState.KEY_UP);
                }
            } break;
                
            case KeyEvent.VK_SHIFT:
            {
                if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
                {
                    nextKeyboardState.keyStates.remove(Keys.LeftShift);
                    nextKeyboardState.keyStates.put(Keys.LeftShift, KeyState.KEY_UP);
                }
                else if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
                {
                    nextKeyboardState.keyStates.remove(Keys.RightShift);
                    nextKeyboardState.keyStates.put(Keys.RightShift, KeyState.KEY_UP);
                }
            } break;
                
            case KeyEvent.VK_M:
            {
                nextKeyboardState.keyStates.remove(Keys.M);
                nextKeyboardState.keyStates.put(Keys.M, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_MULTIPLY:
            {
                nextKeyboardState.keyStates.remove(Keys.Multiply);
                nextKeyboardState.keyStates.put(Keys.Multiply, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_N:
            {
                nextKeyboardState.keyStates.remove(Keys.N);
                nextKeyboardState.keyStates.put(Keys.N, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUM_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.NumLock);
                nextKeyboardState.keyStates.put(Keys.NumLock, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD0:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad0);
                nextKeyboardState.keyStates.put(Keys.NumPad0, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD1:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad1);
                nextKeyboardState.keyStates.put(Keys.NumPad1, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD2:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad2);
                nextKeyboardState.keyStates.put(Keys.NumPad2, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD3:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad3);
                nextKeyboardState.keyStates.put(Keys.NumPad3, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD4:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad4);
                nextKeyboardState.keyStates.put(Keys.NumPad4, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD5:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad5);
                nextKeyboardState.keyStates.put(Keys.NumPad5, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD6:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad6);
                nextKeyboardState.keyStates.put(Keys.NumPad6, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD7:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad7);
                nextKeyboardState.keyStates.put(Keys.NumPad7, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD8:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad8);
                nextKeyboardState.keyStates.put(Keys.NumPad8, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_NUMPAD9:
            {
                nextKeyboardState.keyStates.remove(Keys.NumPad9);
                nextKeyboardState.keyStates.put(Keys.NumPad9, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_O:
            {
                nextKeyboardState.keyStates.remove(Keys.O);
                nextKeyboardState.keyStates.put(Keys.O, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_P:
            {
                nextKeyboardState.keyStates.remove(Keys.P);
                nextKeyboardState.keyStates.put(Keys.P, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_PAGE_DOWN:
            {
                nextKeyboardState.keyStates.remove(Keys.PageDown);
                nextKeyboardState.keyStates.put(Keys.PageDown, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_PAGE_UP:
            {
                nextKeyboardState.keyStates.remove(Keys.PageUp);
                nextKeyboardState.keyStates.put(Keys.PageUp, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_PAUSE:
            {
                nextKeyboardState.keyStates.remove(Keys.Pause);
                nextKeyboardState.keyStates.put(Keys.Pause, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_PRINTSCREEN:
            {
                nextKeyboardState.keyStates.remove(Keys.PrintScreen);
                nextKeyboardState.keyStates.put(Keys.PrintScreen, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_Q:
            {
                nextKeyboardState.keyStates.remove(Keys.Q);
                nextKeyboardState.keyStates.put(Keys.Q, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_R:
            {
                nextKeyboardState.keyStates.remove(Keys.R);
                nextKeyboardState.keyStates.put(Keys.R, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_RIGHT:
            {
                nextKeyboardState.keyStates.remove(Keys.Right);
                nextKeyboardState.keyStates.put(Keys.Right, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_S:
            {
                nextKeyboardState.keyStates.remove(Keys.S);
                nextKeyboardState.keyStates.put(Keys.S, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_SCROLL_LOCK:
            {
                nextKeyboardState.keyStates.remove(Keys.Scroll);
                nextKeyboardState.keyStates.put(Keys.Scroll, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_SPACE:
            {
                nextKeyboardState.keyStates.remove(Keys.Space);
                nextKeyboardState.keyStates.put(Keys.Space, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_SUBTRACT:
            {
                nextKeyboardState.keyStates.remove(Keys.Subtract);
                nextKeyboardState.keyStates.put(Keys.Subtract, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_T:
            {
                nextKeyboardState.keyStates.remove(Keys.T);
                nextKeyboardState.keyStates.put(Keys.T, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_TAB:
            {
                nextKeyboardState.keyStates.remove(Keys.Tab);
                nextKeyboardState.keyStates.put(Keys.Tab, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_U:
            {
                nextKeyboardState.keyStates.remove(Keys.U);
                nextKeyboardState.keyStates.put(Keys.U, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_UP:
            {
                nextKeyboardState.keyStates.remove(Keys.Up);
                nextKeyboardState.keyStates.put(Keys.Up, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_V:
            {
                nextKeyboardState.keyStates.remove(Keys.V);
                nextKeyboardState.keyStates.put(Keys.V, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_W:
            {
                nextKeyboardState.keyStates.remove(Keys.W);
                nextKeyboardState.keyStates.put(Keys.W, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_X:
            {
                nextKeyboardState.keyStates.remove(Keys.X);
                nextKeyboardState.keyStates.put(Keys.X, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_Y:
            {
                nextKeyboardState.keyStates.remove(Keys.Y);
                nextKeyboardState.keyStates.put(Keys.Y, KeyState.KEY_UP);
            } break;
                
            case KeyEvent.VK_Z:
            {
                nextKeyboardState.keyStates.remove(Keys.Z);
                nextKeyboardState.keyStates.put(Keys.Z, KeyState.KEY_UP);
            } break;
                
            default:
                break;
        }
	}

	@Override
	public void
	keyTyped(KeyEvent event)
	{
	}

	
}
