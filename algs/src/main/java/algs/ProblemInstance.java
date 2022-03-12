package main.java.algs;

import lombok.Data;

@Data
public class ProblemInstance
{
    private final int[][] graphMatrix;
    private final String name, type, edge_weight_type, display_data_type;
    private final int dimension;


    public ProblemInstance(int[][] graphMatrix, String name, String type, String edge_weight_type, String display_data_type, int dimension)
    {
        this.graphMatrix = graphMatrix;
        this.name = name;
        this.type = type;
        this.edge_weight_type = edge_weight_type;
        this.display_data_type = display_data_type;
        this.dimension = dimension;
    }
}
