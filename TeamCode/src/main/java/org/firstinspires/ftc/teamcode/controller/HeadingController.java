package org.firstinspires.ftc.teamcode.controller;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.PI;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

/**
 * Proportional Controller that reads angular input values between 0..2·π
 * radians, and produces control values bounded by [-1.0..-clamp, 0.0, clamp..1.0, NaN].
 * <p>
 * This controller supports:
 * <ul>
 * <li>any supplier of an absolute angular input value, e.g. an IMU's orientation
 * sensor</li>
 * <li>runtime recalibration to a reference angle supplied by any external source,
 * e.g. Vuforia</li>
 * <li>error term tolerance, under which the control value produced is always 0.0f</li>
 * <li>control value gain</li>
 * <li>control value clamping [-1.0..-clamp, 0.0, clamp..1.0]</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 *  void init() {
 *    imu = initImu();
 *    heading = new HeadingController(
 *         // read the heading angle from the imu
 *         () -> normalizeTo2PI(imu.getAngularOrientation().firstAngle),
 *         1.0f, // tolerance
 *         3.0f, // gain
 *         0.1f  // clamp
 *    );
 *
 *    // known starting position of North-East at power-up
 *    heading.calibrateTo(PI/4);
 *  }
 *
 *  void getLast() {
 *    double h;
 *    // updated heading after reading from the supplier (may take time)
 *    h = heading.getCurrent();
 *
 *    // last known heading, does not read from the supplier (fast)
 *    h = heading.getLast();
 *
 *    // we want to turn until facing due West
 *    heading.setTarget(PI);
 *
 *    // turning rate of either 0.0f or NaN means stop turning
 *    robot.setTurnRate(heading.setTarget());
 *
 *    ...
 *
 *    // We don't have a target heading right now so let
 *    // setTarget() be NaN.
 *    heading.setTarget(Math.NaN);
 *  }
 * </pre>
 * <p>
 * Great care was taken to make this class have no dependencies aside from
 * the Java standard library. This makes the class easily testable, and
 * and easily embeddable into a simulation environment.
 */
public class HeadingController implements Controller<Double, Double, Double> {
    private double zero;
    private double lastAbsolute;

    private double target = NaN;

    private final Supplier<Double> absolute;
    private final double tolerance;
    private final double gain;
    private final double clamp;

    /**
     * Constructs a new [HeadingController] given a supplier of an absolute
     * angle, an error tolerance, a gain value, and a clamp value.
     *
     * @param absolute  Supplier of an absolute angle reading between [0..2·π].
     *                  example:
     *                  `() -> normalizeTo2PI(imu.getAngularOrientation().firstAngle`).
     *                  This argument is provided as a Supplier so as to keep
     *                  this class from depending on any specific hardware
     *                  device or simulation environment.
     * @param tolerance Minimum absolute value of a non-zero error term. Actual
     *                  errors with smaller absolute values are returned as an
     *                  error of 0.0f.
     * @param gain      Multiplier applied to the control value before clamping.
     *                  π/gain == minimum error value that produces maximum
     *                  control value.
     *                  example: gain == 2.0 produces 1.0 control value for all
     *                  error values > π/2.
     * @param clamp     Minimum absolute value of the control value. Without a
     *                  clamp, a proportional controller may never reach its
     *                  target as the error value asymptotically approaches but
     *                  never reaches zero.
     *                  Example: when the control value is used as a turning
     *                  rate, clamp == 0.1f means the control value will never
     *                  command a turn slower than 10% max rate.
     */
    public HeadingController(
            Supplier<Double> absolute,
            double tolerance,
            double gain,
            double clamp
    ) {
        this.absolute = absolute;
        this.tolerance = tolerance;
        this.gain = gain;
        this.clamp = clamp;
    }

    /**
     * Adjusts the controller's current value based on a provided
     * reference angle.
     *
     * @param reference angle in radians between [0..2·π]
     */
    public void calibrateTo(double reference) {
        checkAngleArgument(reference);
        this.zero = subtractRadians(readAbsolute(), reference);
    }

    /**
     * Sets the target angle. When set to an angle, calls to
     * [HeadingController#setTarget()] will provide a
     * value. When set to NaN, [HeadingController#setTarget()]
     * will also provide NaN.
     *
     * @param target angle in radians within [0..2·π] or NaN.
     */
    public void setTarget(Double target) {
        checkAngleArgumentOrNaN(target);
        this.target = target;
    }

    /**
     * Retrieves the controller's target angle, or NaN if
     * no target angle is requested.
     *
     * @return angle in radians within [0..2·π] or NaN
     */
    public double getTarget() {
        return target;
    }

    /**
     * Reads from the absolute angle provider, and returns the
     * calibration-adjusted current angle. This method must be
     * called in order for the controller to getCurrent its state.
     * e.g. call from within a main getLast().
     *
     * @return a value in radians within [0..2·π].
     * @see HeadingController#getLast() ()
     * @see HeadingController#calibrateTo(double)
     */
    public Double getCurrent() {
        readAbsolute();
        return getLast();
    }

    /**
     * The controller's main output, value proportional to the error term,
     * multiplied by the controller's gain and then clamped, or NaN if the
     * target angle is also NaN.
     *
     * @return a value within [-1.0..-clamp, 0.0, clamp..1.0, NaN]
     */
    public Double getControl() {
        return calcControlValue(getProportion(), gain, clamp, 1.0f);
    }

    /**
     * The last known angle, as adjusted by calibration. Does
     * not read from the absolute angle provider.
     *
     * @return last known angle in radians between
     * [0..2·π], as adjusted by calibration.
     * @see HeadingController#getCurrent()
     * @see HeadingController#calibrateTo(double)
     */
    public double getLast() {
        return subtractRadians(getAbsolute(), getZero());
    }

    /**
     * Returns the controller's error term - the difference between the
     * target and current angles, or 0.0f if the difference is less than
     * the controller's error tolerance. Does not read from the absolute
     * angle provider.
     *
     * @return A value in radians [0.0, tolerance..2·π]
     * @see HeadingController#getCurrent()
     */
    public double getError() {
        return calcAngularError(getTarget(), getLast(), tolerance);
    }

    /**
     * The pure proportion of the error term to π.
     *
     * @return A value between [-1.0..1.0].
     */
    public double getProportion() {
        return calcAngularProportion(getError());
    }

    /**
     * The offset in radians from the absolute provider's zero angle to
     * the controller's calibrated zero angle.
     *
     * @return a value within[0..2·π]
     */
    public double getZero() {
        return zero;
    }

    /**
     * The last known absolute angle as read from the absolute provider.
     *
     * @return a value within[0..2·π]
     */
    public double getAbsolute() {
        return lastAbsolute;
    }

    private double readAbsolute() {
        this.lastAbsolute = absolute.get();
        checkAngleArgument(this.lastAbsolute);
        return this.lastAbsolute;
    }

    private static void checkAngleArgumentOrNaN(double given) {
        if (isNaN(given)) return;
        checkAngleArgument(given);
    }

    private static void checkAngleArgument(double given) {
        if (given < 0 || given > 2*PI)
            throw new IllegalArgumentException(
                    "Angle value must be between [0..2·π] radians!"
            );
    }

    // ----------------------------------------------------------------------
    // Below here are the main algorithmic functions used by the controller.
    //   - package-visible to allow for unit-testing the algorithms
    //   - static since they are stateless logic only



    static double calcAngularError(double desired, double current, double tolerance) {
        if (isNaN(desired)) return NaN;

        double err = subtractRadians(desired, current);
        return (Math.abs(err) < Math.abs(tolerance)) ? 0.0f : err;
    }

    static double calcAngularProportion(double err) {
        return (isNaN(err)) ? NaN : (1.0-Math.abs((err-PI)/PI))*signum(err-PI);
    }

    static double calcControlValue(double proportion, double gain, double min, double max) {
        if (isNaN(proportion)) return NaN;
        if (0.0f == proportion) return 0.0f;

        final double propGain = proportion * gain;

        if (propGain < -max) return -max;
        else if (propGain > max) return max;
        else if (propGain > -min && propGain < min) return min * signum(propGain);
        else return propGain;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "zero=%.2f·π, abs=%.2f·π, target=%.2f·π, ctrl=%.2f, crnt=%.2f·π",
                zero/PI,
                lastAbsolute/PI,
                target/PI,
                getControl(),
                getCurrent()/PI
        );
    }
}