public class NBody {
  public static void main(String[] args) {
    StdAudio.play("audio/2001.mid");
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String fileName = args[2];
    double radius = readRadius(fileName);
    Planet[] allPlanets = readPlanets(fileName);
    StdDraw.setScale(-radius, radius); // set scale of universe
    StdDraw.enableDoubleBuffering(); // prevents flickering

    for (double time = 0; time < T; time += dt) {
      animateUniverse(dt, allPlanets);
    } // end for loop simulating time
    printUniverse(radius, allPlanets);
  } // end main method

  private static void animateUniverse(double dt, Planet[] allPlanets) {
    /** Animates and updates each frame of the universe */
    int planetCount = allPlanets.length;
    double[] xForces = new double[planetCount];
    double[] yForces = new double[planetCount];
    StdDraw.picture(0, 0, "images/starfield.jpg"); // add background

    for (Planet planet:allPlanets) {
      planet.draw();
    } // end for loop drawing each planet

    for (int i = 0; i < planetCount; i++) {
      xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
      yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
    } // end for loop gathering net forces on each planet

    for (int i = 0; i < planetCount; i++) {
      allPlanets[i].update(dt, xForces[i], yForces[i]);
    } // end for loop updating each planet
    StdDraw.show();
    StdDraw.pause(10);
  } // end animate universe method

  private static void printUniverse(double radius, Planet[] allPlanets) {
    /** Prints the universe at time "T" */
    StdOut.printf("%d\n", allPlanets.length);
    StdOut.printf("%.2e\n", radius);

    for (int i = 0; i < allPlanets.length; i++) {
      StdOut.printf(
        "%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
        allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
        allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);
    } // end for loop printing out the final state of the universe
  } // end printUniverse method

  public static double readRadius(String fileName) {
    /** Returns the radius of the universe in the given file */
    In in = new In(fileName);
    int planetCount = in.readInt();
    double radius = in.readDouble();
    return radius;
  } // end readRadius method

  public static Planet[] readPlanets(String fileName) {
    /** Returns the planets of the universe in the given file */
    In in = new In(fileName);
    int planetCount = in.readInt();
    double radius = in.readDouble();
    Planet[] allPlanets = new Planet[planetCount];

    for (int i = 0; i < planetCount; i++) {
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m = in.readDouble();
      String img = in.readString();
      allPlanets[i] = new Planet(xP,yP,xV,yV,m,img);
    } // end for loop iterating over planets
    return allPlanets;
  } // end readPlanets method
} // end class NBody
