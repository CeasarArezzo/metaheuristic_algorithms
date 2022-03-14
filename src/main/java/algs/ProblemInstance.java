package algs;

import lombok.Data;

@Data
public class ProblemInstance
{
    private final int[][] graphMatrix;
    private final String name, type, edge_weight_type, edge_weight_format;
    private final int dimension;


    public ProblemInstance(int[][] graphMatrix, String name, String type, String edge_weight_type, String edge_weight_format, int dimension)
    {
        this.graphMatrix = graphMatrix;
        this.name = name;
        //TODO: change this to ENUM?
        this.type = type;
        this.edge_weight_type = edge_weight_type;
        this.edge_weight_format = edge_weight_format;
        this.dimension = dimension;
    }
}
