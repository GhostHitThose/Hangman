package me.maxrenner.ux.buttons;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public abstract class Button {
    @Getter private final int x,y,width,height;
    @Getter private final Color color, hoveredColor;
    @Getter @Setter private boolean visible;
    private boolean hovered;

    public Button(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = visible;
        this.hoveredColor = hoveredColor;
    }

    public void setHovered(int x, int y){
        hovered = ((this.x <= x && this.x + width >= x) && this.y <= y && this.y+height >= y);
    }

    public void setClicked(int x, int y){
        if(((this.x <= x && this.x + width >= x) && this.y <= y && this.y+height >= y)){
            onClick();
        }
    }

    public void build(Graphics2D g){
        if(!visible) return;

        if(hovered)
            g.setColor(getHoveredColor());
        else
            g.setColor(getColor());
        g.fillRect(getX(),getY(),getWidth(),getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(getX(),getY(),getWidth(),getHeight());
    }

    public abstract void onClick();
}
