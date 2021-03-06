package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerator.Direction;
import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;

public class Maze2dDisplay extends MazeDisplay {
	private Maze3d	currentMaze;
	int				LoadedMaze2	= 0;
	MazeWindow mw;
	public Maze2dDisplay(Composite parent, int style) {
		super(parent,style);
		this.setBackground(new Color(null,255,255,255));
	}
	@Override
	protected void drawMaze(PaintEvent e) {
		if(mazeData == null){
			return;
		}
		// if (LoadedMaze2 == 1) {
		// currentMaze = new Maze3d(mazeData.length, mazeData[0].length,
		// mazeData[0][0].length);
		// for (int i = 0; i < mazeData[0][0].length; i++) {
		// for (int j = 0; j < mazeData[0].length; j++) {
		// for (int j2 = 0; j2 < mazeData.length; j2++) {
		// if (mazeData[j][j2][i] == 0) {
		// currentMaze.setFree(j2, j, i);
		// }
		// if (mazeData[j][j2][i] == 1) {
		// currentMaze.setWall(j2, j, i);
		// // }
		// }
		// }
		// }
		// currentMaze.setStartPosition(character.getPos());
		// currentMaze.setGoalPosition(character.getGoalPosition());
		// }
		// LoadedMaze2 = 0;
		// }
		if((mazeData != null && currentMaze == null) || (LoadedMaze2 == 1)){
			currentMaze = new Maze3d(mazeData.length,mazeData[0].length,mazeData[0][0].length);
			for(int i = 0; i < mazeData[0][0].length; i++){
				for(int j = 0; j < mazeData[0].length; j++){
					for(int j2 = 0; j2 < mazeData.length; j2++){
						if(mazeData[j][j2][i] == 0){
							currentMaze.setFree(j2,j,i);
						}
						if(mazeData[j][j2][i] == 1){
							currentMaze.setWall(j2,j,i);
							// }
						}
					}
				}
				currentMaze.setStartPosition(character.getPos());
				currentMaze.setGoalPosition(character.getGoalPosition());
			}
			LoadedMaze2 = 0;
		}
		e.gc.setBackground(new Color(null,150,150,150));
		int width = getSize().x;
		int height = getSize().y;
		int cellWidth = width / mazeData.length;
		int cellHeight = height / mazeData[0].length;
		// [rows][columns][levels]
		for(int i = 0; i < mazeData.length; i++){
			for(int j = 0; j < mazeData[i].length; j++){
				int x = j * cellWidth;
				int y = i * cellHeight;
				if(mazeData[i][j][character.getPos().getZ()] != 0){
					e.gc.setBackground(new Color(null,150,150,150));
					e.gc.fillRectangle(x,y,cellWidth,cellHeight);
				}
			}
		}
		character.draw(e,cellWidth,cellHeight);
	}
	@Override
	public void goFloorDown() {
		Position pos = character.getPos();
		moveCharacter(pos.getX(),pos.getY(),pos.getZ() - 1);
	}
	@Override
	public void goFloorUp() {
		Position pos = character.getPos();
		moveCharacter(pos.getX(),pos.getY(),pos.getZ() + 1);
	}
	@Override
	public void goUp() {
		Position pos = character.getPos();
		moveCharacter(pos.getX(),pos.getY() - 1,pos.getZ());
	}
	@Override
	public void goDown() {
		Position pos = character.getPos();
		moveCharacter(pos.getX(),pos.getY() + 1,pos.getZ());
	}
	@Override
	public void goRight() {
		Position pos = character.getPos();
		moveCharacter(pos.getX() + 1,pos.getY(),pos.getZ());
	}
	@Override
	public void goLeft() {
		Position pos = character.getPos();
		moveCharacter(pos.getX() - 1,pos.getY(),pos.getZ());
	}

	@Override
	protected void debugStatus() {
		Position pos = character.getPos();
		System.out.println("Current Position[pos.get()]: {" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "}");
		System.out.println("Current Position[pos.get()+1]: {" + (pos.getX() + 1) + "," + (pos.getY() + 1) + "," + (pos.getZ() + 1) + "}");
		System.out.println(mazeData.length);
		System.out.println(mazeData.toString());
		System.out.println("\n");
		System.out.println(mazeData[(pos.x)][((pos.y) + 1)][(pos.z)]);
	}
	private boolean moveCharacter(int x, int y, int z) {
		Position pos = new Position(x,y,z);
		// if (currentMaze == null) {
		/// print mazedata
		// for (int j2 = 0; z < mazeData[0][0].length; z++) {
		// for (int i = 0; i < mazeData[0].length; i++) {
		// for (int j = 0; j < mazeData.length; j++) {
		// System.out.print(mazeData[i][j][j2] + " ");
		// }
		// System.out.print("\n");
		// }
		// }
		// System.out.println("\n");
		// System.out.println(currentMaze);
		if((x >= 0 && x < currentMaze.getMaze().length) && (y >= 0 && y < mazeData[1].length) && (z >= 0 && z < mazeData[0][1].length)){
			if(((currentMaze).getMaze()[pos.x][pos.y][pos.z] == 0)){
				Position p = character.getPos();
				p.setX(x);
				p.setY(y);
				p.setZ(z);
				character.setPos(p);
				getDisplay().syncExec(new Runnable(){
					@Override
					public void run() {
						redraw();
						getShell().update();
						getDisplay().update();
					}
				});
				return true;
			}
		}
		return false;
	}
	public Maze3d getcurrMaze() {
		return currentMaze;
	}
	public void setcurrMaze(Maze3d m) {
		currentMaze = m;
		int[][][] mazeData = new int[m.getColumns()][m.getRows()][m.getLevels()];
		for(int i = 0; i < currentMaze.getLevels(); i++){
			for(int j = 0; j < currentMaze.getColumns(); j++){
				for(int j2 = 0; j2 < currentMaze.getRows(); j2++){
					if(currentMaze.getMaze()[j][j2][i] == 0){
						mazeData[j][j2][i] = 0;
					}
					if(currentMaze.getMaze()[j][j2][i] == 1){
						mazeData[j][j2][i] = 1;
						// }
					}
				}
			}
			character.setPos(currentMaze.getStartPosition());
			character.setGoalPos(currentMaze.getGoalPosition());
		}
		this.mazeData = mazeData;
		LoadedMaze2 = 1;
	}
}
