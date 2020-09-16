import java.util.Scanner;

public class HeatEnergyRunner {
    
    public static void main(final String[] args) {

       final Scanner reader = new Scanner(System.in);

        System.out.println("Enter in the mass of the water, in grams");
        final double mass = reader.nextDouble();
        System.out.println("Enter the inital temperature, in Celcius");
        double initialTemp = reader.nextDouble();
        // double initialTemp = Math.max(reader.nextDouble(), -273.14); Same as top code
        if (initialTemp < -273.14)
            initialTemp = -273.14;

        System.out.println("Enter the final temperature, in Celcius");
        double finalTemp = reader.nextDouble();
        if (finalTemp < -273.14)
            finalTemp = -273.14;

        String initialPhase = "liquid";
        if (initialTemp < 0)
            initialPhase = "solid";
        if (initialTemp > 100)
            initialPhase = "vapor";

        String finalPhase = "liquid";
        if (finalTemp < 0)
            finalPhase = "solid";
        if (finalTemp > 100)
            finalPhase = "vapor";

        System.out.println("Mass: " + mass + "g");
        System.out.println("Initial Temperature: " + initialTemp + "C " + initialPhase);
        System.out.println("Final Temperature: " + finalTemp + "C " + finalPhase + "\n");

        boolean endothermic = false;
        if (finalTemp > initialTemp)
            endothermic = true;

        double heatEnergy = 0;

        if (initialPhase.equals("solid")) {
            heatEnergy += tempChangeSolid(mass, initialTemp, finalTemp, finalPhase, endothermic);

            if (!finalPhase.equals("solid")) {
                heatEnergy += fusion(mass, endothermic);
                heatEnergy += tempChangeLiquid(mass, 0, finalTemp, finalPhase, endothermic);
            }

            if (finalPhase.equals("vapor")) {
                heatEnergy += vaporization(mass, endothermic);
                heatEnergy += tempChangeVapor(mass, 100, finalTemp, finalPhase, endothermic);
            }
        } // end of section for inital phase: solid

        System.out.println("Total Heat Energy: " + heatEnergy + "kJ");

    } // main ends here

    public static double tempChangeSolid(final double mass, final double initialTemp, double finalTemp,
            final String finalPhase, final boolean endothermic) {
        if (!finalPhase.equals("solid"))
            finalTemp = 0;
        final double energyChange = round(mass * 0.002108 * (finalTemp - initialTemp));
        if (endothermic)
            System.out.println("Heating (solid): " + energyChange + "kJ");
        else
            System.out.println("Cooling (solid): " + energyChange + "kJ");
        return energyChange;
    }

    public static double tempChangeLiquid(final double mass, final double initialTemp, double finalTemp,
            final String finalPhase, final boolean endothermic) {
        if (finalPhase.equals("solid"))
            finalTemp = 0;
        if (finalPhase.equals("vapor"))
            finalTemp = 100;
        final double energyChange = round(mass * 0.004184 * (finalTemp - initialTemp));
        if (endothermic)
            System.out.println("Heating (liquid): " + energyChange + "kJ");
        else
            System.out.println("Cooling (liquid): " + energyChange + "kJ");
        return energyChange;
    }

    public static double tempChangeVapor(final double mass, final double initialTemp, double finalTemp,
            final String finalPhase, final boolean endothermic) {
        if (!finalPhase.equals("vapor"))
            finalTemp = 0;
        final double energyChange = round(mass * 0.001996 * (finalTemp - initialTemp));
        if (endothermic)
            System.out.println("Heating (vapor): " + energyChange + "kJ");
        else
            System.out.println("Cooling (vapor): " + energyChange + "kJ");
        return energyChange;
    }

    public static double fusion(final double mass, final boolean endothermic) {
        double energyChange;
        if (endothermic) {
            energyChange = round(0.333 * mass);
            System.out.println("Melting: " + energyChange + "kJ");
        } else {
            energyChange = round(-0.333 * mass);
            System.out.println("Freezing: " + energyChange + "kJ");
        }
        return energyChange;
    }

    public static double vaporization(final double mass, final boolean endothermic) {
        double energyChange; 
        if(endothermic) {
            energyChange = round(2.257*mass);
            System.out.println("Evaporation: " + energyChange + "kJ");
        }
        else {
            energyChange = round(-2.257*mass);
            System.out.println("Condensation: " + energyChange + "kJ"); 
        }
        return energyChange; 
    }

    public static double round(double x) {
        x *= 1000; 
        if(x > 0)
            return (int)(x + 0.5)/1000.0;
        else   
            return (int)(x - 0.5)/1000.0; 
    }
}
    // suporting methods