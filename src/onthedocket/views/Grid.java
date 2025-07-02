package onthedocket.views;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Grid extends Container {
	private int rows;
	private int cols;
	
	public Grid() {
		this(5, 5);
	}

	public Grid(int rows, int cols) {
		setRows(rows);
		setColumns(cols);
		
		setLayout(new GridLayout(rows, cols));
		
		clear();
	}
	
	public void insertComponent(Component comp, int row, int col) {
        if(row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Cell (" + row + ", " + col + ")");
        }
        int index = row * cols + col;
        remove(index);
        add(comp, index);
        revalidate();
        repaint();
    }
	
	private void clearRow(int row) {
		if(row < 0 || row >= rows) {
			throw new IndexOutOfBoundsException("Row " + row);
		}
		
		for(int c = 0; c < cols; c++) {
			int index = row * cols + c;
			remove(index);
			add(new JPanel(), index);
		}
		revalidate();
		repaint();
	}
	
	public void clearColumn(int col) {
        if(col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Column " + col);
        }
        
        for(int r = 0; r < rows; r++) {
            int index = r * cols + col;
            remove(index);
            add(new JPanel(), index);
        }
        revalidate();
        repaint();
    }

	
	private void clear() {
		removeAll();
		
		for(int i = 0; i < rows*cols; i++) {
			add(new JPanel());
		}
		
		revalidate();
		repaint();
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
		clear();
	}
	
	public int getColumns() {
		return cols;
	}
	
	public void setColumns(int cols) {
		this.cols = cols;
		clear();
	}
}
