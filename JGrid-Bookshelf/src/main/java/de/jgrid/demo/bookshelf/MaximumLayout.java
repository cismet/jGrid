package de.jgrid.demo.bookshelf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

public class MaximumLayout implements LayoutManager, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Insets insets;

    public MaximumLayout(int top, int left, int bottom, int right) {
        insets = new Insets(top, left, bottom, right);
    }

    public MaximumLayout() {
        this(0, 0, 0, 0);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void layoutContainer(Container parent) {
        for (int i = 0; i < parent.getComponentCount(); i++) {
            parent.getComponent(i).setBounds(insets.left, insets.top, parent.getWidth() - (insets.left + insets.right),
                parent.getHeight() - (insets.top + insets.bottom));

        }

    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension d = new Dimension(0, 0);
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (d.height < c.getMinimumSize().height) {
                d.height = c.getMinimumSize().height;
            }
            if (d.width < c.getMinimumSize().width) {
                d.width = c.getMinimumSize().width;
            }
        }
        d.width = d.width + insets.left + insets.right;
        d.height = d.height + insets.top + insets.bottom;
        return d;
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension d = new Dimension(0, 0);
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (d.height < c.getPreferredSize().height) {
                d.height = c.getPreferredSize().height;
            }
            if (d.width < c.getPreferredSize().width) {
                d.width = c.getPreferredSize().width;
            }
        }
        d.width = d.width + insets.left + insets.right;
        d.height = d.height + insets.top + insets.bottom;
        return d;
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    public void setInsets(int top, int left, int bottom, int right) {
        insets = new Insets(top, left, bottom, right);
    }

    public void setTopInset(int top) {
        insets.top = top;
    }

    public void setLeftInset(int left) {
        insets.left = left;
    }

    public void setBottomInset(int bottom) {
        insets.bottom = bottom;
    }

    public void setRightInset(int right) {
        insets.right = right;
    }

    public Insets getInsets() {
        return insets;
    }
}
