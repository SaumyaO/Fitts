package com.example.fitts;

public class SimLevel {

    public SimLevel()
    {

    }

    public SimLevel(int rows, int columns)
    {
        this.intRows=rows;
        this.intColumn=columns;
    }

    int intRows;
    public int getRows()
    {
        return intRows;
    }

    public void setRows (int rows)
    {
        intRows = rows;
    }

    int intColumn;
    public int getColumn()
    {
        return intColumn;
    }

    public void setColumn (int cols)
    {
        intColumn = cols;
    }

}
