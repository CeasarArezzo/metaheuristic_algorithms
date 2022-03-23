package algs;

import lombok.Data;
import parser.EdgeWeightFormatE;
import parser.EdgeWeightTypeE;
import parser.ProblemTypeE;

@Data
public class ProblemInstance
{
    private final int[][] graphMatrix;
    private final String name;
    private final ProblemTypeE type;
    private final EdgeWeightTypeE edge_weight_type;
    private final EdgeWeightFormatE edge_weight_format;
    private final int dimension;

    public ProblemInstance(int[][] graphMatrix, String name, ProblemTypeE type, EdgeWeightTypeE edge_weight_type, EdgeWeightFormatE edge_weight_format, int dimension)
    {
        this.graphMatrix = graphMatrix;
        this.name = name;
        this.type = type;
        this.edge_weight_type = edge_weight_type;
        this.edge_weight_format = edge_weight_format;
        this.dimension = dimension;
    }    

    public int[][] getGraphMatrix()
    {
        return graphMatrix;
    }
    
    public int getValue(int column, int row)
    {
        return graphMatrix[column][row];
    }
    
    public int getDimension()
    {
        return dimension;
    }
}
