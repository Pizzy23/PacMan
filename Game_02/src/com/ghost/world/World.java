package com.ghost.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.ghost.entities.Enemy;
import com.ghost.entities.Entity;
import com.ghost.entities.Fruta;
import com.ghost.entities.FrutaCaixas;
import com.ghost.entities.FrutaSlow;
import com.ghost.entities.FrutaSpd;
import com.ghost.entities.FrutaSpecial;
import com.ghost.entities.Player;
import com.ghost.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000){
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						


					}else if(pixelAtual == 0xFFbc00ff) {
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xFF00F119) {
						Fruta fruta = new Fruta(xx*16,yy*16,16,16,0,Entity.Fruta_sprite);
						Game.entities.add(fruta);
						Game.frutas_mapa ++;
					}else if (pixelAtual == 0xFF8e8989) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY1);
						Game.entities.add(enemy);
					}else if (pixelAtual == 0xFFFF0000) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY2);
						Game.entities.add(enemy);
					}else if (pixelAtual == 0xFF0026FF) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY3);
						Game.entities.add(enemy);
					}else if (pixelAtual == 0xFFff0097) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY4);
						Game.entities.add(enemy);
					}else if (pixelAtual == 0xFFfbe046) {
						FrutaSpecial frutaSpecial = new FrutaSpecial(xx*16,yy*16,16,16,0,Entity.Fruta_Special_sprite);
						Game.entities.add(frutaSpecial);	
						Game.frutas_mapa ++;
					}else if (pixelAtual == 0xFF186fab) {
						FrutaSlow FrutaSlow = new FrutaSlow(xx*16,yy*16,16,16,0,Entity.Fruta_Slow_sprite);
						Game.entities.add(FrutaSlow);
						Game.frutas_mapa++;
					}else if (pixelAtual == 0xFF89d900) {
					FrutaSpd frutaSpd = new FrutaSpd(xx*16,yy*16,16,16,0,Entity.Fruta_Spd_sprite);
					Game.entities.add(frutaSpd);	
					Game.frutas_mapa ++;
					
			       }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static void restartGame(String level){
		Game.entities.clear();
		Game.player = new Player (0,0,16,16,2,Game.spritesheet.getSprite(16, 0,16,16));
	    Game.entities.add(Game.player);
		Game.frutas_Atual = 0;
	    Game.frutas_mapa = 0;
		Game.world = new World ("/level1.png");

		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static void renderMap() {
		for(int i = 0; i < Game.miniMPix.length; i++) {
			Game.miniMPix[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile) {
					Game.miniMPix[xx+(yy*WIDTH)] = 0xff0000;
					
				}
			}
		}
		
		
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		Game.miniMPix[xPlayer+(yPlayer*WIDTH)] = 0x0000ff;
		
	}
   	
}
