package dkeep.logic;

public abstract class Weapon {
	
	protected int x;
	protected int y;
	protected char symbol;
	protected char secsymbol;
	protected boolean above;
	
	public abstract int[] swing(int x, int y); //x and y are the coordinates of the enemy
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
}