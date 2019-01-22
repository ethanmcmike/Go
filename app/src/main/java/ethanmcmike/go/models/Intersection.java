package ethanmcmike.go.models;

public class Intersection {
    private char ascii;
	
	public Intersection() {
		ascii = '+';
	}
	
	public boolean set(char color) {
		if(ascii != '+' || color < 0x41 || color > 0x5A) return false;
		ascii = color;
		return true;
	}
	
	public void clear() {
		ascii = '+';
	}
	
	public char check() {
		if(ascii >= 0x41 && ascii <= 0x5A) {
			char temp = ascii;
			ascii += 0x20;
			return temp;
		} else
			return ascii;
	}
	public char check(char color) {
		if(ascii == color) {
			char temp = ascii;
			ascii += 0x20;
			return temp;
		} else
			return ascii;
	}
	
	public char getColor() {
		if(ascii >= 0x61 && ascii <= 0x7A) ascii -= 0x20;
		return ascii;
	}
	
	public char getChar() {
		return ascii;
	}
	
	void reset() {
		if(ascii >= 0x61 && ascii <= 0x7A) ascii -= 0x20;
	}
}
