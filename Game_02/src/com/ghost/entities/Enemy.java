package com.ghost.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.ghost.main.Game;
import com.ghost.world.AStar;
import com.ghost.world.Camera;
import com.ghost.world.Vector2i;



public class Enemy extends Entity{
	

	public static boolean ghostMod = false;
	public static boolean frutaPega = false;
	public static int ghostFrames = 0;
	public int ghostTime = 60*4;    
	public int startFrames = 0;
	public boolean Start = false;
	public static int Spd = 90;
	public static boolean Slow;
	public int SlowTime;
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}

	public void tick(){
			depth = 0;
			startFrames++;
			if(startFrames == 60) {
				Start = true;
			}
			if(Start == true) {
				
	   	if(ghostMod == false) {
			if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			
				if(new Random().nextInt(100) < Spd)
					followPath(path);
				
				if(x % 16 == 0 && y % 16 == 0) {
					if(new Random().nextInt(100) < 10) {
						Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
						Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
						path = AStar.findPath(Game.world, start, end);
					}
				}
	   	
	   	}
	   	}
			if(Slow == true)
			{
				Spd = 40;
				SlowTime++;
			}
			if(SlowTime == 60*2)
			{
					SlowTime = 0;
					Spd = 90;
                 System.out.println(Spd);
                 Slow = false;
			}
			
					
		if(frutaPega == true)
		{
			ghostMod = true;
			//System.out.println(ghostFrames);
			ghostFrames++;
		
		  if (ghostFrames == 60*5) {
			//System.out.println("Segundo foi foi");
			ghostMod = false;
			frutaPega = false;
		  }
		}else {
			ghostMod = false;
		}
	}
	
	



	public void render(Graphics g) {
		super.render(g);
		if(ghostMod == true) {
			g.drawImage(Entity.ENEMYGHOST, this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
	}
	
	
}
