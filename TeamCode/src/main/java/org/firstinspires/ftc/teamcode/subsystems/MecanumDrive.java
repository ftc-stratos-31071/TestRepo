package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import dev.nextftc.core.subsystems.Subsystem;

/**
 * Mecanum drivetrain subsystem with IMU integration.
 *
 * Motor configuration:
 * - front_left: left front wheel
 * - front_right: right front wheel
 * - back_left: left rear wheel
 * - back_right: right rear wheel
 *
 * NOTE: If your robot rotates in the WRONG DIRECTION during turnToLimelight():
 * 1. Try reversing the left OR right side motors (not both)
 * 2. Or swap which side gets positive/negative power in setPowerRotation()
 */
public class MecanumDrive implements Subsystem {
    public static final MecanumDrive INSTANCE = new MecanumDrive();

    private MecanumDrive() {}

    // Motors - using standard FTC DcMotorEx for LinearOpMode compatibility
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    // IMU for heading tracking
    private IMU imu;

    /**
     * Initialize the drivetrain hardware.
     * Call this from your OpMode's init phase.
     *
     * @param hardwareMap the hardware map from your OpMode
     * @param imu the IMU instance from hardwareMap
     */
    public void init(HardwareMap hardwareMap, IMU imu) {
        this.imu = imu;

        // Get motors from hardware map
        frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
        frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
        backLeft = hardwareMap.get(DcMotorEx.class, "back_left");
        backRight = hardwareMap.get(DcMotorEx.class, "back_right");

        // Set motor directions (adjust if your robot drives backward or rotates wrong)
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // Set zero power behavior for all motors
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize IMU
        // Adjust orientation based on how your Control Hub is mounted
        // LogoFacingDirection: which way the REV logo faces (UP, DOWN, LEFT, RIGHT, FORWARD, BACKWARD)
        // UsbFacingDirection: which way the USB ports face
        IMU.Parameters imuParams = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
        );
        imu.initialize(imuParams);
        imu.resetYaw();
    }

    /**
     * Get the current robot heading from IMU.
     *
     * @return heading in degrees (-180 to 180)
     */
    public double getHeading() {
        if (imu == null) return 0.0;
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    /**
     * Reset the IMU heading to zero.
     * Useful for setting a new "forward" direction.
     */
    public void resetHeading() {
        if (imu != null) {
            imu.resetYaw();
        }
    }

    /**
     * Set motor powers for in-place rotation.
     * Positive power = rotate counterclockwise (left), negative = clockwise (right)
     *
     * @param power rotation power, clipped to [-1.0, 1.0]
     */
    public void setPowerRotation(double power) {
        // Clip power to valid range
        power = Math.max(-1.0, Math.min(1.0, power));

        // For pure rotation: left side and right side spin opposite directions
        // If rotation direction is WRONG, swap the signs here:
        frontLeft.setPower(-power);   // Left side backward for CCW rotation
        backLeft.setPower(-power);
        frontRight.setPower(power);   // Right side forward for CCW rotation
        backRight.setPower(power);
    }

    /**
     * Stop all drive motors.
     */
    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    /**
     * Set motor powers for mecanum drive (field-centric or robot-centric).
     * This method is included for completeness but not used in the Limelight autonomous.
     *
     * @param forward forward/backward power
     * @param strafe left/right strafe power
     * @param rotate rotation power
     */
    public void setPowerMecanum(double forward, double strafe, double rotate) {
        // Calculate individual wheel powers using mecanum drive equations
        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backLeftPower = forward - strafe + rotate;
        double backRightPower = forward + strafe - rotate;

        // Normalize powers if any exceed 1.0
        double maxPower = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    @Override
    public void periodic() {
        // No periodic updates needed for direct motor control
    }
}

