import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {
	void printMaze(int rows,int columns, Grid[][] grid)
	{
	for(int i=0;i<rows;i++)
	{
		for(int j=0;j<columns;j++)
		{
			if(i==0&&j==0)
			{
				String space ="   ";
				System.out.print(space);
				for(int k=0;k<columns-1;k++)
					System.out.print("_ ");
				System.out.println();
			}
			if(grid[i][j].leftWall)
				System.out.print("|");
			else
				System.out.print(" ");
			if(grid[i][j].bottomWall)
				System.out.print("_");
			else
				System.out.print(" ");
			if(j==columns-1&&i!=rows-1)
				System.out.print("|");
			}
		System.out.println();
	}
	}
	public static void main(String[] args) {
		
		MazeGenerator generator=new MazeGenerator();
		Scanner elements = new Scanner(System.in);
		System.out.println("Enter no.of rows");
		int rows = elements.nextInt();
		System.out.println("Enter no.of columns");
		int columns = elements.nextInt();
		Grid[][] grid = new Grid[rows][columns];
		List<Wall> list = new ArrayList<>();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				grid[i][j] = new Grid();
			if(i==0&&j==0)
			grid[i][j].leftWall=false;
			if(i==rows-1&&j==columns-1)
				grid[i][j].bottomWall=false;
		}
		}
		for(int i=0;i<rows;i++)
		{ 
			for(int j=0;j<columns;j++)
			{
				int current=i*columns+j;
				int left=current-1;int bottom= current+columns;
				if(j!=0)
					list.add(new Wall("vertical",left,current));
				if(i!=rows-1)
					list.add(new Wall("horizontal",current,bottom));
			}
		}
		System.out.println("Intial Maze");
		generator.printMaze(rows,columns,grid);
		DisjSets ds = new DisjSets(rows*columns);
		Random random = new Random();
		int count=0;
		while(count!=rows*columns-1)
		{
			Wall wall = list.get(random.nextInt(list.size()));
			int leftofWall = ds.find(wall.leftofWall);
			int rightofWall= ds.find(wall.rightofWall);
			if(wall.orientation.equals("vertical")&&leftofWall!=rightofWall)
			{
				int i = wall.rightofWall/columns;
				int j =wall.rightofWall%columns;
				ds.union(ds.find(leftofWall),ds.find(rightofWall));
				count++;
				grid[i][j].leftWall=false;
			}

			else
				if(wall.orientation.equals("horizontal")&&leftofWall!=rightofWall)
				{
					ds.union(ds.find(leftofWall),ds.find(rightofWall));
					count++;
					int i = wall.leftofWall/columns;
					int j = wall.leftofWall%columns;
					grid[i][j].bottomWall=false;
				}
		}
		System.out.println("The Final Maze");
		generator.printMaze(rows,columns,grid);
	}
}