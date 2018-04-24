public class NBodyExtreme {

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
      allPlanets = collisionChecker(allPlanets);
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

  private static Planet[] collisionChecker(Planet[] allPlanets) {
    Planet[] updatedPlanets = allPlanets;
    for (int i = 0; i < updatedPlanets.length; i++) {
      for (int j = i + 1; j < updatedPlanets.length; j++) {
        if (allPlanets[i].calcDistance(allPlanets[j]) < Math.pow(10, 10)) {
          if (allPlanets[i].mass > allPlanets[j].mass) {
            updatedPlanets = collisionCreater(allPlanets[i], allPlanets[j], allPlanets);
          } else {
            updatedPlanets = collisionCreater(allPlanets[j], allPlanets[i], allPlanets);
          } // end if else statement deciding merged planet location
        } // end if statement checking for collisions
      } // end inner for loop
    } // end outer for loop
    return updatedPlanets;
  } // end collisionChecker method

  private static Planet[] collisionCreater(Planet bigger, Planet smaller, Planet[] allPlanets) {
    Planet[] updatedPlanets = new Planet[allPlanets.length - 1];
    double xxPos = bigger.xxPos;
    double yyPos = bigger.yyPos;
    double xxVel = (bigger.mass * bigger.xxVel + smaller.mass * smaller.xxVel) / (bigger.mass + smaller.mass);
    double yyVel = (bigger.mass * bigger.yyVel + smaller.mass * smaller.yyVel) / (bigger.mass + smaller.mass);
    double mass = bigger.mass + smaller.mass;
    String imgFileName = bigger.imgFileName; // temporary solution

    int UPIndex = 0;
    for (int i = 0; i < allPlanets.length; i++) {
      if (allPlanets[i] != bigger && allPlanets[i] != smaller) {
        updatedPlanets[UPIndex] = allPlanets[i];
        UPIndex++;
      } // end if statement removing collided planets
    } // end for loop updating planet list
    updatedPlanets[UPIndex] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName); // add new combined planet
    return updatedPlanets;
  } // end collisionCreater method





  private static Planet spaceShipCreator() {
    Planet spaceShip = new Planet(radius/2, -radius, 0, 0, Math.pow(10,3), "images/joshhug");


  } // end spaceShipCreator method


  private static Planet spaceShipController() {
    int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
    InputMap imap = centerPanel.getInputMap(mapName);
    ActionMap amap = centerPanel.getActionMap();

    KeyStroke wKey = KeyStroke.getKeyStroke("w");
    imap.put(wKey, "forward");
    amap.put("forward", forwardCommand);

    KeyStroke aKey = KeyStroke.getKeyStroke("a");
    imap.put(aKey, "left");
    amap.put("left", leftCommand);

    KeyStroke dKey = KeyStroke.getKeyStroke("d");
    imap.put(dKey, "right");
    amap.put("right", rightCommand);

    KeyStroke spaceKey = KeyStroke.getKeyStroke("space");
    imap.put(spaceKey, "fire");
    amap.put("fire", fireCommand);

    requestFocus();
  } // end spaceShipController method


  private static Planet shootAction(Planet spaceShip, String direction, Planet[] allPlanets) {
    Planet[] updatedPlanets = new Planet[allPlanets.length + 1];
    double xxPos = spaceShip.xxPos;
    double yyPos = spaceShip.yyPos;
    double xxVel = 0;
    double yyVel = 0;
    double mass = 1;

    switch(direction) {
      case "up":
        yyVel = Math.pow(10,10);
        break;
      case "left":
        xxVel = - Math.pow(10,10);
        break;
      case "down":
        yyVel = - Math.pow(10,10);
        break;
      case "right":
        xxVel = Math.pow(10,10);
        break;
      default:
        break;
    } // end switch statement evaluating direction

    for (int i = 0; i < allPlanets.length + 1; i++) {
      if (i == allPlanets.length) {
        Planet bullet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, "images/L.jpeg");
        updatedPlanets[i] = bullet;
      } else {
        updatedPlanets[i] = allPlanets[i];
      } // end if statement adding bullet
    } // end for loop updating planet list
    return updatedPlanets;
  } // end shootAction method

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
} // end NBodyExtreme class
