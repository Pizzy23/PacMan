package com.ghost.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ghost.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("Frutas: "+Game.frutas_mapa+"/"+Game.frutas_Atual, 25, 25);
	}
	
}
