package ethanmcmike.go.models;

public class Intersection {
    private char ascii;
	
	public Intersection() {
		this.clear();
	}
	
	public boolean set(char color) {
		if(color < 0x40 || color > 0x5A) return false;
		ascii = color;
		return true;
	}
	
	public void clear() {
		ascii = '@';
	}
	
	public char check() {
		return ascii;
	}
	public char check(char color) {
		if(ascii == color) {
			ascii += 0x20;
			return color;
		} else
			return ascii;
	}
	public void setChecked() {
		if(ascii >= 0x40 && ascii <= 0x5A)
			ascii += 0x20;
	}
	
	public char getColor() {
		if(ascii >= 0x61 && ascii <= 0x7A) ascii -= 0x20;
		return ascii;
	}
	
	public char getChar() {
		if(ascii == '@' || ascii == '`') return (char)(ascii - 0x20);
		return ascii;
	}
	
	void reset() {
		if(ascii >= 0x60 && ascii <= 0x7A) ascii -= 0x20;
	}
}
