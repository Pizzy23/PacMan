package com.ghost.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ghost.main.Game;
import com.ghost.world.Camera;
import com.ghost.world.Tile;
import com.ghost.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;

 
	public static boolean pegarFruta = false;
	public int right_dir = 0,left_dir = 1;
	public int dir = right_dir;
    public BufferedImage sprite_left,sprite_left3,sprite_left5 ;
    public BufferedImage sprite_rigth,sprite_rigth3,sprite_rigth5 ;
	public boolean frutaSpd = false;
	public double spd = 2;
	public int pSpdF = 0;
	public boolean Slow = false;
	private int CUR_LEVEL = 0, MAX_LEVEL = 1;
	public int EndTime = 0;
	
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		sprite_rigth = Game.spritesheet.getSprite(64, 0,16,16);
		sprite_rigth3 = Game.spritesheet.getSprite(64, 16, 16,16);
		sprite_rigth5 = Game.spritesheet.getSprite(64, 32, 16,16);
		sprite_left = Game.spritesheet.getSprite(80, 0, 16,16);
		sprite_left3 = Game.spritesheet.getSprite(80, 16, 16,16);
		sprite_left5 = Game.spritesheet.getSprite(80, 32, 16,16);
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=spd;
			dir = right_dir;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=spd;
			dir = left_dir;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=spd;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=spd;
		}
		verificarPegaFruta();
		verificarPegaFrutaSpecial();
		verificarPegaFruta3();
		verificarPegaFruta4();
		updateCamera();
		
		if(Game.frutas_mapa == Game.frutas_Atual) {
			EndTime++;
			Enemy.ghostMod = true;
		}
			if(EndTime == 60*2) {
			Enemy.ghostMod = false;
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL)
			{
				CUR_LEVEL = 1;
				System.out.println(CUR_LEVEL);
			}
			String newWorld = "level" + CUR_LEVEL + ".png";
			World.restartGame(newWorld);

		}
		
		if(frutaSpd == true)
		{
			spd = 2.5;
			//System.out.println(ghostFrames);
			pSpdF++;
		
		  if (pSpdF == 60*2) {
			  pSpdF = 0;
			//System.out.println("Segundo foi foi");
			spd = 2;
			frutaSpd = false;
		  }
		}else {
			spd = 2;
		}
		
	}
	
    public void updateCamera()
    {
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2),0,World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2),0,World.HEIGHT * 16 - Game.HEIGHT);

    }
	
	public void verificarPegaFruta() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
			if(Entity.isColidding(this, current)) {
				Game.frutas_Atual++;
				Game.entities.remove(i);
				return;
			} 
		}
	}
}
	public void verificarPegaFruta3() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof FrutaSpd) {
			if(Entity.isColidding(this, current)) {
				frutaSpd = true;
				Game.frutas_Atual++;
				Game.entities.remove(i);
				return;
			} 
		}
	}
}
	public void verificarPegaFruta4() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof FrutaSlow) {
			if(Entity.isColidding(this, current)) {
				Enemy.Slow = true;
				Game.frutas_Atual++;
				Game.entities.remove(i);
				return;
			} 
		}
	}
}

	public void verificarPegaFrutaSpecial() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof FrutaSpecial) {
			if(Entity.isColidding(this, current)) {
				Enemy.ghostFrames = 0;
				Enemy.frutaPega = true;
				System.out.println("Peguei");
				Game.frutas_Atual++;
				Game.entities.remove(i);
				return;
			} 
		}
	}
}

	
	public void render(Graphics g) {
		if(Game.frutas_Atual <= 3) {
		if(dir == right_dir) {
			g.drawImage(sprite_rigth,this.getX() - Camera.x,this.getY() - Camera.y,null);	
		}else if(dir == left_dir){
			g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}else if(Game.frutas_Atual == 4 || Game.frutas_Atual == 5 || Game.frutas_Atual == 6 ||
			 Game.frutas_Atual == 7 || Game.frutas_Atual == 8 || Game.frutas_Atual == 9 ||
			 Game.frutas_Atual == 10) {
		if(dir == right_dir) {
			g.drawImage(sprite_rigth3,this.getX() - Camera.x,this.getY() - Camera.y,null);	
		}else if(dir == left_dir){
			g.drawImage(sprite_left3,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}else if(Game.frutas_Atual >= 11) {
		if(dir == right_dir) {
			g.drawImage(sprite_rigth5,this.getX() - Camera.x,this.getY() - Camera.y,null);	
		}else if(dir == left_dir){
			g.drawImage(sprite_left5,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	}


}
