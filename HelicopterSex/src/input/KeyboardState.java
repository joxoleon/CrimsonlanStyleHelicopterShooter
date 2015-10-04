package input;

import java.util.EnumMap;
import java.util.Map;

public class KeyboardState
{
	Map<Keys, KeyState> keyStates = new EnumMap<Keys, KeyState>(Keys.class);
	
	public boolean
    isKeyDown(Keys key)
    {
        return (keyStates.get(key) == KeyState.KEY_DOWN);
    }
    
    public boolean
    isKeyUp(Keys key)
    {
        return (keyStates.get(key) == KeyState.KEY_UP);
    }
	
    KeyboardState(KeyboardState state)
    {
        keyStates = new EnumMap<Keys, KeyState>(state.keyStates);
    }
    
	KeyboardState()
    {		
        keyStates.put(Keys.A, KeyState.KEY_UP);
        keyStates.put(Keys.Add, KeyState.KEY_UP);
        keyStates.put(Keys.B, KeyState.KEY_UP);
        keyStates.put(Keys.Back, KeyState.KEY_UP);
        keyStates.put(Keys.C, KeyState.KEY_UP);
        keyStates.put(Keys.CapsLock, KeyState.KEY_UP);
        keyStates.put(Keys.D, KeyState.KEY_UP);
        keyStates.put(Keys.D0, KeyState.KEY_UP);
        keyStates.put(Keys.D1, KeyState.KEY_UP);
        keyStates.put(Keys.D2, KeyState.KEY_UP);
        keyStates.put(Keys.D3, KeyState.KEY_UP);
        keyStates.put(Keys.D4, KeyState.KEY_UP);
        keyStates.put(Keys.D5, KeyState.KEY_UP);
        keyStates.put(Keys.D6, KeyState.KEY_UP);
        keyStates.put(Keys.D7, KeyState.KEY_UP);
        keyStates.put(Keys.D8, KeyState.KEY_UP);
        keyStates.put(Keys.D9, KeyState.KEY_UP);
        keyStates.put(Keys.Delete, KeyState.KEY_UP);
        keyStates.put(Keys.Divide, KeyState.KEY_UP);
        keyStates.put(Keys.Down, KeyState.KEY_UP);
        keyStates.put(Keys.E, KeyState.KEY_UP);
        keyStates.put(Keys.End, KeyState.KEY_UP);
        keyStates.put(Keys.Escape, KeyState.KEY_UP);
        keyStates.put(Keys.Enter, KeyState.KEY_UP);
        keyStates.put(Keys.Escape, KeyState.KEY_UP);
        keyStates.put(Keys.F, KeyState.KEY_UP);
        keyStates.put(Keys.F1, KeyState.KEY_UP);
        keyStates.put(Keys.F2, KeyState.KEY_UP);
        keyStates.put(Keys.F3, KeyState.KEY_UP);
        keyStates.put(Keys.F4, KeyState.KEY_UP);
        keyStates.put(Keys.F5, KeyState.KEY_UP);
        keyStates.put(Keys.F6, KeyState.KEY_UP);
        keyStates.put(Keys.F7, KeyState.KEY_UP);
        keyStates.put(Keys.F8, KeyState.KEY_UP);
        keyStates.put(Keys.F9, KeyState.KEY_UP);
        keyStates.put(Keys.F10, KeyState.KEY_UP);
        keyStates.put(Keys.F11, KeyState.KEY_UP);
        keyStates.put(Keys.F12, KeyState.KEY_UP);
        keyStates.put(Keys.G, KeyState.KEY_UP);
        keyStates.put(Keys.H, KeyState.KEY_UP);
        keyStates.put(Keys.Home, KeyState.KEY_UP);
        keyStates.put(Keys.I, KeyState.KEY_UP);
        keyStates.put(Keys.J, KeyState.KEY_UP);
        keyStates.put(Keys.K, KeyState.KEY_UP);
        keyStates.put(Keys.L, KeyState.KEY_UP);
        keyStates.put(Keys.Left, KeyState.KEY_UP);
        keyStates.put(Keys.LeftAlt, KeyState.KEY_UP);
        keyStates.put(Keys.LeftControl, KeyState.KEY_UP);
        keyStates.put(Keys.LeftShift, KeyState.KEY_UP);
        keyStates.put(Keys.M, KeyState.KEY_UP);
        keyStates.put(Keys.Multiply, KeyState.KEY_UP);
        keyStates.put(Keys.N, KeyState.KEY_UP);
        keyStates.put(Keys.None, KeyState.KEY_UP);
        keyStates.put(Keys.NumLock, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad0, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad1, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad2, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad3, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad4, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad5, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad6, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad7, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad8, KeyState.KEY_UP);
        keyStates.put(Keys.NumPad9, KeyState.KEY_UP);
        keyStates.put(Keys.O, KeyState.KEY_UP);
        keyStates.put(Keys.P, KeyState.KEY_UP);
        keyStates.put(Keys.PageDown, KeyState.KEY_UP);
        keyStates.put(Keys.PageUp, KeyState.KEY_UP);
        keyStates.put(Keys.Pause, KeyState.KEY_UP);
        keyStates.put(Keys.PrintScreen, KeyState.KEY_UP);
        keyStates.put(Keys.Q, KeyState.KEY_UP);
        keyStates.put(Keys.R, KeyState.KEY_UP);
        keyStates.put(Keys.Right, KeyState.KEY_UP);
        keyStates.put(Keys.RightControl, KeyState.KEY_UP);
        keyStates.put(Keys.RightShift, KeyState.KEY_UP);
        keyStates.put(Keys.S, KeyState.KEY_UP);
        keyStates.put(Keys.Scroll, KeyState.KEY_UP);
        keyStates.put(Keys.Space, KeyState.KEY_UP);
        keyStates.put(Keys.Subtract, KeyState.KEY_UP);
        keyStates.put(Keys.T, KeyState.KEY_UP);
        keyStates.put(Keys.Tab, KeyState.KEY_UP);
        keyStates.put(Keys.U, KeyState.KEY_UP);
        keyStates.put(Keys.Up, KeyState.KEY_UP);
        keyStates.put(Keys.V, KeyState.KEY_UP);
        keyStates.put(Keys.W, KeyState.KEY_UP);
        keyStates.put(Keys.X, KeyState.KEY_UP);
        keyStates.put(Keys.Y, KeyState.KEY_UP);
        keyStates.put(Keys.Z, KeyState.KEY_UP);
    }
	
}
