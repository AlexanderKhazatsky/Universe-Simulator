public class Planet {
  public double xxPos; // Its current x position
  public double yyPos; // Its current y position
  public double xxVel; // Its current velocity in the x direction
  public double yyVel; // Its current velocity in the y direction
  public double mass; // Its mass
  public String imgFileName; // The name of the file that corresponds to the image that depicts the planet
  private static final double G = 6.67 * Math.pow(10, -11);

  public Planet(double xP, double yP, double xV, double yV, double m, String img) {
    /** Creates an instance of the planet class */
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  } // end planet creator method

  public Planet(Planet p) {
    /** Clones an instance of the planet class */
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
  } // end planet cloner method

  public double calcDistance(Planet rocinante) {
    /** Calculates the distance between this planet and the given planet */
    return Math.sqrt(
      (xxPos - rocinante.xxPos) * (xxPos - rocinante.xxPos)
      + (yyPos - rocinante.yyPos) * (yyPos - rocinante.yyPos));
  } // end calcDistance method

  public double calcForceExertedBy(Planet rocinante) {
    double distance = calcDistance(rocinante);
    return (G * mass * rocinante.mass)/(distance * distance);

  } // end calcForceExertedBy method

  public double calcForceExertedByX(Planet rocinante) {
    /** Calculates the force exerted between this planet and the given planet on the X axis */
    double distance = calcDistance(rocinante);
    double force = calcForceExertedBy(rocinante);
    return force * (rocinante.xxPos - xxPos)/distance;
  } // end calcForceExertedByX method

  public double calcForceExertedByY(Planet rocinante) {
    /** Calculates the force exerted between this planet and the given planet on the Y axis */
    double distance = calcDistance(rocinante);
    double force = calcForceExertedBy(rocinante);
    return force * (rocinante.yyPos - yyPos)/distance;
  } // end calcForceExertedByY method

  public double calcNetForceExertedByX(Planet[] allPlanets) {
    /** Calculates the net force exerted between this planet and the given planets on the X axis */
    double netForce = 0;
    for (Planet planet:allPlanets) {
      if (! planet.equals(this)) netForce += calcForceExertedByX(planet);;
    } // end for loop combining net forces
    return netForce;
  } // end calcNetForceExertedByX method

  public double calcNetForceExertedByY(Planet[] allPlanets) {
    /** Calculates the net force exerted between this planet and the given planets on the Y axis */
    double netForce = 0;
    for (Planet planet:allPlanets) {
      if (! planet.equals(this)) netForce += calcForceExertedByY(planet);
    } // end for loop combining net forces
    return netForce;
  } // end calcNetForceExertedByX method

  public void update(double dt, double fX, double fY) {
    /** Calculates how much the forces exerted on the planet will cause that planet to accelerate */
    double xxAcc = fX/mass; // calculates the acceleration on the X axis
    double yyAcc = fY/mass; // calculates the acceleration on the Y axis
    xxVel = xxVel + dt * xxAcc; // calculates the new velocity on the X axis
    yyVel = yyVel + dt * yyAcc; // calculates the new velocity on the Y axis
    xxPos = xxPos + dt * xxVel; // calculates the new position on the X axis
    yyPos = yyPos + dt * yyVel; // calculates the new position on the Y axis
  } // end update method

  public void draw() {
    /** Draws the planet at it's correct position in the universe */
    String imageToDraw = "images/" + imgFileName;
    StdDraw.picture(xxPos, yyPos, imageToDraw);
  } // end draw method
} // end planet class
