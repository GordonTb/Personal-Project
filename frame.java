package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class frame extends JPanel implements ActionListener  {
	private final int WIDTH = 300;
	private final int HEIGHT = 300;
	private final int SNAKE_SIZE = 10;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int DELAY = 140;
	private final int SNAKE_BODYX[] =new int [ALL_DOTS];
	private final int SNAKE_BODYY[] =new int [ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean leftDirection = false;
	private boolean rightDirection = false;
	private boolean upDirection = true;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image Ball;
	private Image apple;
	private Image head;
	
	public frame(){
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		loadImages();
		initGame();
		
	}
	private void loadImages(){
		ImageIcon iid = new ImageIcon(getClass().getResource("/res/head.png"));
		Ball = iid.getImage();
		paintCompone(all);
		ImageIcon iia = new ImageIcon(getClass().getResource("/res/head.png"));
		apple = iia.getImage();
		
		ImageIcon iih = new ImageIcon(getClass().getResource("/res/head.png"));
		head = iih.getImage();
		
		
	}
	
	private void initGame(){
		
		dots = 3;
		
		for (int z = 0; z < dots; z++){
			SNAKE_BODYX[z]= 50-z*10;
			SNAKE_BODYY[z]= 50;
		}
		locateApple();
		
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	
	protected void paintComponet(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}
	private void doDrawing(Graphics g){
		
		if(inGame){
			g.drawImage(apple, 40, 40, this);
			
		
		for(int z = 0; z<dots;z++){
			if(z==0){
				g.drawImage(head,SNAKE_BODYX[z], SNAKE_BODYY[z],this);
			} else{
				g.drawImage(Ball,SNAKE_BODYX[z], SNAKE_BODYY[z],this);
			}
		}
		
	
		Toolkit.getDefaultToolkit().sync();
	
		}else{
		gameOver(g);
		}
	}
	private void gameOver(Graphics g){
		String msg= "Game Over";
		Font small = new Font ("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg))/2, HEIGHT );
		
	}
	private void checkApple(){
		if((SNAKE_BODYX[0]==apple_x) && (SNAKE_BODYY[0]== apple_y)){
			dots++;
			locateApple();
		}
	}
	private void move(){
		for(int z = dots; z>0; z--){
			SNAKE_BODYX[z]=SNAKE_BODYX[z-1];
			SNAKE_BODYY[z]=SNAKE_BODYY[z-1];
		}
		
		if(leftDirection){
			SNAKE_BODYX[0]-=SNAKE_SIZE;
		}
		if(upDirection){
			SNAKE_BODYY[0]-=SNAKE_SIZE;
		}
		if(downDirection){
			SNAKE_BODYY[0]+=SNAKE_SIZE;
		}
		if(rightDirection){
			SNAKE_BODYX[0]+=SNAKE_SIZE;
		}
	}
	private void  checkCollision(){
		for(int z = dots; z>0;z--){
			if((z > 4) && (SNAKE_BODYX[0]==SNAKE_BODYX[z])&& (SNAKE_BODYY[0]==SNAKE_BODYY[z])){
				inGame = false;
			}
		}
		if(SNAKE_BODYY[0]>= HEIGHT){
			inGame= false;
		}
		if(SNAKE_BODYY[0]< 0){
			inGame= false;
		}
		if(SNAKE_BODYX[0]>= WIDTH){
			inGame= false;
		}
		if(SNAKE_BODYX[0]<0){
			inGame= false;
		}
		if(!inGame){
			timer.stop(); 
		}
	}
	private void locateApple(){
		System.out.print("aa");
		int r =(int)(Math.random()*RAND_POS);
		apple_x= r*SNAKE_SIZE;
		
		r =(int)(Math.random()*RAND_POS);
		apple_y= r*SNAKE_SIZE;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame){
			
			checkApple();
			checkCollision();
			move();
		}
		repaint();
		
	}
	private class TAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			
		if(key == KeyEvent.VK_LEFT && !rightDirection ){
			leftDirection = true;
			upDirection = false;
			downDirection = false;
		}
		if(key == KeyEvent.VK_RIGHT&& !leftDirection ){
			rightDirection = true;
			upDirection = false;
			downDirection = false;
			
		}
		if(key == KeyEvent.VK_UP&& !downDirection ){
			upDirection = true;
			rightDirection = false;
			leftDirection = false;
			
		}
		if(key == KeyEvent.VK_DOWN&& !upDirection ){
			downDirection = true;
			rightDirection = false;
			leftDirection = false;
			
		}
		}
		
	}

}
