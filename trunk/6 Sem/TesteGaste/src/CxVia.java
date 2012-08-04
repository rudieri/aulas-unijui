
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.*;
import org.jgap.impl.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author rudieri
 */
public class CxVia extends FitnessFunction {

    public static final String CIDADES = "ABCDEF";
    public static final int dAB = 5;
    public static final int dAC = 2;
    public static final int dAD = 15;
    public static final int dBC = 8;
    public static final int dBF = 14;
    public static final int dEC = 6;
    public static final int dDE = 20;
    public static final int dDF = 10;

    private static void print(IChromosome melhorAteAgora) {
        for (int i = 0; i < melhorAteAgora.getGenes().length; i++) {
            System.out.println(melhorAteAgora.getGene(i).getAllele().toString());
        }
    }

    @Override
    protected double evaluate(IChromosome sujeito) {
        double v = 800;
        String aleloAnteiror = null;
        int distTotal = 0;
        StringBuilder jahForam = new StringBuilder();
        for (int i = 0; i < sujeito.getGenes().length; i++) {
            StringGene gene = (StringGene) sujeito.getGene(i);
            String alelo = (String) gene.getAllele();
            if (aleloAnteiror == null) {
                aleloAnteiror = alelo;
                jahForam.append(alelo);
                continue;
            }
            if (aleloAnteiror.equals(alelo)) {
                v-=100;
            }
            if (jahForam.indexOf(alelo)!=-1) {
                v-= 20;
            }
            jahForam.append(alelo);
            Field field = null;
            try {
                field = getClass().getField("d" + alelo + aleloAnteiror);
            } catch (NoSuchFieldException | SecurityException ex) {
                try {
                    field = getClass().getField("d" + aleloAnteiror + alelo);
                } catch (NoSuchFieldException | SecurityException ex1) {
                    // Sem distância
                    return 0;
                }
            }
            try {
                Integer get = (Integer) field.get(null);
                distTotal += get;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(CxVia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return v - distTotal;
    }

    public static void main(String[] args) throws InvalidConfigurationException {
        Configuration configuration = new DefaultConfiguration();
        FitnessFunction fitnessFunction = new CxVia();
        configuration.setFitnessFunction(fitnessFunction);
        configuration.setPreservFittestIndividual(true);
        Gene[] genes = new Gene[CIDADES.length()];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new StringGene(configuration, 1, 1, CIDADES);

        }
        configuration.setUniqueKeysActive(true);
        
        configuration.addGeneticOperator(new MutationOperator(configuration, 99));//  135.20000000000002
        configuration.addGeneticOperator(new GaussianMutationOperator(configuration, .9));//  135.20000000000002

        Chromosome chromosome = new Chromosome(configuration, genes);
        configuration.setSampleChromosome(chromosome);
        configuration.setPopulationSize(2000);
        Genotype population = Genotype.randomInitialGenotype(configuration);
        IChromosome bestSolutionSoFar = null;

        for (int i = 0; i < 50; i++) {
//                System.out.println("Temporário: ");
            bestSolutionSoFar = population.getFittestChromosome();
            if (i % 5 == 0) {
                System.out.println("I: " + i + " >> " + bestSolutionSoFar.getFitnessValue());
                print(bestSolutionSoFar);
            }
//                print(bestSolutionSoFar);
            population.evolve();
//                System.out.println("- - - - - - - - - - - - - - - - ");

        }
        System.out.println("The best solution contained the following: ");

        print(bestSolutionSoFar);
    }
}
