import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class PascalPanel extends JPanel {
	PTriangle triangle;
	int distinguishedRemainder;
	long topLeftX, topLeftY;
	long rowsOnScreen;
	Color[] colors;
	float brightness = 0.8f;
	
	final int type; // 0 = mod , 1 = multiplicity
	public static int MOD = 0, MULTIPLICITY = 1;

	PascalPanel(int type, int prime) {
		this.type = type;
		this.setBackground(Color.gray);
		triangle = new PTriangle();
		rowsOnScreen = 20;
		topLeftX = 0;
		topLeftY = 0;
		distinguishedRemainder = 0;
		reset(prime );
	}
	
	void setBrightness(float f)
	{
		brightness = f;
		this.resetColors();
	}

	void reset(int p) {
		
		triangle.getTriangle(1, p, p);
		if (type == MOD)
			colors = new Color[p];
		else if (type == MULTIPLICITY)
		{
			colors = new Color[10];
		}
		resetColors();
		this.repaint();
	}
	
	void resetColors()
	{
		for (int i = 0; i < colors.length; i++) {
			colors[i] = new Color(Color.HSBtoRGB(i / (float)colors.length,
					0.9f, brightness));
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = (int) Math.max(1, (getHeight() / rowsOnScreen) );
		//System.out.println("width : " +width );
		int firstX = (topLeftX < 0)? (int)(-topLeftX * getHeight() / rowsOnScreen) : 0; 
		for (int y = 0; y < getHeight(); y += width)
		{
			long m = (rowsOnScreen > getWidth())? topLeftY + (rowsOnScreen * y) / getHeight(): topLeftY + y / width;
			for (int x = firstX; x < getWidth(); x += width)
			{
				long n = (rowsOnScreen > getWidth())? topLeftX + (rowsOnScreen * x) / getHeight(): topLeftX + x/width;
				//System.out.println("m:" + m + " n:" + n + " x:" + x + " y:" + y);
				// convert m \choose n to a color
				if (n > m)
					break; // then go to the next row
				else if (n < 0)
					continue;
				else
				{
					if (type == MOD)
					{
						int mod = triangle.chooseModFromLucas(m, n);
						if (mod == this.distinguishedRemainder)
							g.setColor(Color.white);
						else
								g.setColor(colors[mod]);
					}else
					{
						int multiplicity =triangle.multiplicity(m, n);
						if (multiplicity < colors.length)
							g.setColor(colors[multiplicity]);
						else
							g.setColor(Color.gray);
					}
					g.fillRect(x, y, width, width);
				}
			}
		}
	}
}
