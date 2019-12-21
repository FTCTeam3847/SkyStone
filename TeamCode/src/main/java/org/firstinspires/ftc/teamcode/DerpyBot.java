package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

public class DerpyBot implements SkystoneBot {

    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    private final HardwareMap hardwareMap;
    private MecanumDriveController driverController;
    private BNO055IMU imu;
    private AngularPController headingController;


    public DerpyBot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    private BNO055IMU initImu(BNO055IMU imu) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu.initialize(parameters);
        return imu;
    }

    @Override
    public void init() {

        imu = initImu(hardwareMap.get(BNO055IMU.class, "imu"));
        headingController = new AngularPController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                0.0d,
                10.0d,
                0.0d);
        driverController = new MecanumDriveController(headingController);
        //Drivetrain:

        //Primary Port 3
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 1
        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 2
        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 0
        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    private void move4(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontMotor.setPower(leftFront);
        leftBackMotor.setPower(leftBack);
        rightFrontMotor.setPower(rightFront);
        rightBackMotor.setPower(rightBack);
    }

    private void move(DrivePower drivePower) {
        move4(drivePower.leftFor, drivePower.leftBack, drivePower.rightFor, drivePower.rightBack);
    }

    @Override
    public void move(DriveCommand driveCommand) {
        driverController.setTarget(driveCommand);
        move(driverController.getControl());
    }

    public void move(double x, double y, double turn) {
         driverController.setTarget(x, y, turn);
         move(driverController.getControl());
    }
}
