package io.github.tomaso2468.netengine.input;

public enum Key {
	SPACE(' ', '\0', '\0'),
	APOSTROPHE('\'', '"', '@'),
	COMMA(',', '<', '\0'),
	MINUS('-', '_', '\0'),
	PERIOD('.', '>', '\0'),
	SLASH('/', '?', '\0'),
	NUMBER_0('0', '!', '!'),
	NUMBER_1('1', '@', '"'),
	NUMBER_2('2', '#', '£'),
	NUMBER_3('3', '$', '$'),
	NUMBER_4('4', '%', '%'),
	NUMBER_5('5', '^', '^'),
	NUMBER_6('6', '&', '&'),
	NUMBER_7('7', '*', '*'),
	NUMBER_8('8', '(', '('),
	NUMBER_9('9', ')', ')'),
	SEMICOLON(';', ':', '\0'),
	EQUALS('=', '+', '\0'),
	A('a', 'A', '\0'),
	B('b', 'B', '\0'),
	C('c', 'C', '\0'),
	D('d', 'D', '\0'),
	E('e', 'E', '\0'),
	F('f', 'F', '\0'),
	G('g', 'G', '\0'),
	H('h', 'H', '\0'),
	I('i', 'I', '\0'),
	J('j', 'J', '\0'),
	K('k', 'K', '\0'),
	L('l', 'L', '\0'),
	M('m', 'M', '\0'),
	N('n', 'N', '\0'),
	O('o', 'O', '\0'),
	P('p', 'P', '\0'),
	Q('q', 'Q', '\0'),
	R('r', 'R', '\0'),
	S('s', 'S', '\0'),
	T('t', 'T', '\0'),
	U('u', 'U', '\0'),
	V('v', 'V', '\0'),
	W('w', 'W', '\0'),
	X('x', 'X', '\0'),
	Y('y', 'Y', '\0'),
	Z('z', 'Z', '\0'),
	LEFT_BRACKET('[', '{', '\0'),
	BACKSLASH('\\', '|', '\0'),
	RIGHT_BRACKET(']', '}', '\0'),
	GRAVE('`', '~', '¬'),
	WORLD_1('\0', 161, '\0', '\0'),
	WORLD_2('\0', 162, '\0', '\0'),
	
	ESCAPE('\0', 256, '\0', '\0'),
	ENTER('\n', 257, '\n', '\0'),
	TAB('\t', 258, '\t', '\0'),
	BACKSPACE('\0', 259, '\0', '\0'),
	INSERT('\0', 260, '\0', '\0'),
	DELETE('\0', 261, '\0', '\0'),
	RIGHT('\0', 262, '\0', '\0'),
	LEFT('\0', 263, '\0', '\0'),
	DOWN('\0', 264, '\0', '\0'),
	UP('\0', 265, '\0', '\0'),
	PAGE_UP('\0', 266, '\0', '\0'),
	PAGE_DOWN('\0', 267, '\0', '\0'),
	HOME('\0', 268, '\0', '\0'),
	END('\0', 269, '\0', '\0'),
	CAPS_LOCK('\0', 280, '\0', '\0'),
	SCROLL_LOCK('\0', 281, '\0', '\0'),
	NUM_LOCK('\0', 282, '\0', '\0'),
	PRINT_SCREEN('\0', 283, '\0', '\0'),
	PAUSE('\0', 284, '\0', '\0'),
	F1('\0', 290, '\0', '\0'),
	F2('\0', 291, '\0', '\0'),
	F3('\0', 292, '\0', '\0'),
	F4('\0', 293, '\0', '\0'),
	F5('\0', 294, '\0', '\0'),
	F6('\0', 295, '\0', '\0'),
	F7('\0', 296, '\0', '\0'),
	F8('\0', 297, '\0', '\0'),
	F9('\0', 298, '\0', '\0'),
	F10('\0', 299, '\0', '\0'),
	F11('\0', 300, '\0', '\0'),
	F12('\0', 301, '\0', '\0'),
	F13('\0', 302, '\0', '\0'),
	F14('\0', 303, '\0', '\0'),
	F15('\0', 304, '\0', '\0'),
	F16('\0', 305, '\0', '\0'),
	F17('\0', 306, '\0', '\0'),
	F18('\0', 307, '\0', '\0'),
	F19('\0', 308, '\0', '\0'),
	F20('\0', 309, '\0', '\0'),
	F21('\0', 310, '\0', '\0'),
	F22('\0', 311, '\0', '\0'),
	F23('\0', 312, '\0', '\0'),
	F24('\0', 313, '\0', '\0'),
	F25('\0', 314, '\0', '\0'),
	PAD_0('0', 320, '\0', '\0'),
	PAD_1('1', 321, '\0', '\0'),
	PAD_2('2', 322, '\0', '\0'),
	PAD_3('3', 323, '\0', '\0'),
	PAD_4('4', 324, '\0', '\0'),
	PAD_5('5', 325, '\0', '\0'),
	PAD_6('6', 326, '\0', '\0'),
	PAD_7('7', 327, '\0', '\0'),
	PAD_8('8', 328, '\0', '\0'),
	PAD_9('9', 329, '\0', '\0'),
	PAD_DECIMAL('.', 330, '\0', '\0'),
	PAD_DIVIDE('/', 331, '\0', '\0'),
	PAD_MULTIPLY('*', 332, '\0', '\0'),
	PAD_SUBTRACT('-', 333, '\0', '\0'),
	PAD_ADD('+', 334, '\0', '\0'),
	PAD_ENTER('\n', 335, '\0', '\0'),
	PAD_EQUALS('=', 336, '\0', '\0'),
	LSHIFT('\0', 340, '\0', '\0'),
	LCTRL('\0', 341, '\0', '\0'),
	LALT('\0', 342, '\0', '\0'),
	LSUPER('\0', 343, '\0', '\0'),
	RSHIFT('\0', 344, '\0', '\0'),
	RCTRL('\0', 345, '\0', '\0'),
	RALT('\0', 346, '\0', '\0'),
	RSUPER('\0', 347, '\0', '\0'),
	MENU('\0', 348, '\0', '\0'),
	;
	private final char ascii;
	private final char shift;
	private final char shiftUK;
	private final int glfw;
	
	private Key(char ascii, int glfw, char shift, char shiftUK) {
		this.glfw = glfw;
		this.ascii = ascii;
		this.shift = shift;
		this.shiftUK = shiftUK;
	}
	
	private Key(char ascii, char shift, char shiftUK) {
		if (ascii >= 'a' && ascii <= 'z') {
			this.glfw = shift;
		} else {
			this.glfw = ascii;
		}
		this.ascii = ascii;
		this.shift = shift;
		this.shiftUK = shiftUK;
	}


	public int getGLFWBinding() {
		return glfw;
	}
	
	public char getASCII() {
		return ascii;
	}
	
	public char getASCIIShift() {
		if (shift == '\0') {
			return getASCII();
		}
		return shift;
	}
	
	public char getASCIIShiftUK() {
		if (shiftUK == '\0') {
			return getASCIIShift();
		}
		return shiftUK;
	}
	
	public String toString() {
		return name().replace("_", " ").toLowerCase();
	}
}
