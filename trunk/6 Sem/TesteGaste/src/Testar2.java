
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
public class Testar2 extends FitnessFunction {

    private static final String AMARELO = ".";
    private static final String AZUL = ".";
    private static final String VERMELHO = "vermelho";

    @Override
    protected double evaluate(IChromosome sujeito) {
        double v = 0;

        for (int i = 0; i < sujeito.getGenes().length; i++) {
            StringGene gene = (StringGene) sujeito.getGene(i);
            String alelo = (String) gene.getAllele();
            int tamVermelho = Math.min(alelo.length(), VERMELHO.length());
//            int tamAzul = Math.min(alelo.length(), AZUL.length());
//            int tamAmarelo = Math.min(alelo.length(), AMARELO.length());
            if (alelo.equals(VERMELHO)) {
                v += 100;
                continue;
            } else if (alelo.length() == VERMELHO.length()) {
                v += 5;
            }
            for (int j = 0; j < tamVermelho; j++) {
                if (alelo.indexOf(VERMELHO.charAt(j)) != -1) {
                    if (VERMELHO.charAt(j) == alelo.charAt(j)) {
                        v += 2;
                    } else {
                        v += 0.1 * Math.abs(alelo.indexOf(VERMELHO.charAt(j)) - j);
                    }
                } else {
                    v -= 0.1;
//                    gene.applyMutation(j, 50);
                }
            }
            if (v < 0) {
                v = 0.001;
            }
        }

        return v;
    }

    public static void main(String[] args) {
        try {
            Configuration configuration = new DefaultConfiguration();
            FitnessFunction fitnessFunction = new Testar2();
            configuration.setFitnessFunction(fitnessFunction);
            configuration.setPreservFittestIndividual(true);
            Gene[] genes = new Gene[20];
            String alfa = "aehlmoruvz";
            for (int i = 0; i < genes.length; i++) {
                genes[i] = new StringGene(configuration, 0, 9, alfa);


            }

//            configuration.addGeneticOperator(new GaussianMutationOperator(configuration, 1));
//            configuration.addGeneticOperator(new AveragingCrossoverOperator(configuration));
            configuration.addGeneticOperator(new CrossoverOperator(configuration, .9));
//            configuration.addGeneticOperator(new InversionOperator(configuration));//136.79999999999998
            configuration.addGeneticOperator(new MutationOperator(configuration));//218.0
//            configuration.addGeneticOperator(new RangedSwappingMutationOperator(configuration, 3));//136.79999999999998
//            configuration.addGeneticOperator(new SwappingMutationOperator(configuration));// 134.30000000000004
//            configuration.addGeneticOperator(new TwoWayMutationOperator(configuration));//  135.20000000000002

            Chromosome chromosome = new Chromosome(configuration, genes);
            configuration.setSampleChromosome(chromosome);
            configuration.setPopulationSize(20000);
            Genotype population = Genotype.randomInitialGenotype(configuration);
            IChromosome bestSolutionSoFar = null;

            for (int i = 0; i < 100; i++) {
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
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(Testar2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void print(IChromosome melhorAteAgora) {
        for (int i = 0; i < melhorAteAgora.getGenes().length; i++) {
            System.out.println(melhorAteAgora.getGene(i).getAllele().toString());
        }
//        System.out.println(melhorAteAgora.getGene(1).getAllele().toString());
//        System.out.println(melhorAteAgora.getGene(2).getAllele().toString());


    }
}
