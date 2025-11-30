package org.firstinspires.ftc.teamcode.constants;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizer;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.Drivetrain;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PedroConstants {

    public static Follower createFollower(HardwareMap hardwareMap) {
        // Create FollowerConstants with default configuration
        FollowerConstants followerConstants = new FollowerConstants();

        // Create PinpointConstants and configure offsets
        PinpointConstants pinpointConstants = new PinpointConstants();
        pinpointConstants.forwardPodY = -5.0;  // Forward pod Y offset in inches
        pinpointConstants.strafePodX = 0.5;    // Strafe pod X offset in inches
        pinpointConstants.distanceUnit = DistanceUnit.INCH;
        pinpointConstants.hardwareMapName = "pinpoint";

        // Create Pinpoint localizer with constants
        Localizer localizer = new PinpointLocalizer(hardwareMap, pinpointConstants);

        // Create MecanumConstants and configure motor names
        MecanumConstants mecanumConstants = new MecanumConstants();
        mecanumConstants.leftFrontMotorName = "frontLeftMotor";
        mecanumConstants.rightFrontMotorName = "frontRightMotor";
        mecanumConstants.leftRearMotorName = "backLeftMotor";
        mecanumConstants.rightRearMotorName = "backRightMotor";

        // Create Mecanum drivetrain with constants
        Drivetrain drivetrain = new Mecanum(hardwareMap, mecanumConstants);

        // Create Follower with 3 parameters (FollowerConstants, Localizer, Drivetrain)
        Follower follower = new Follower(followerConstants, localizer, drivetrain);

        // Set starting pose
        follower.setStartingPose(new Pose(0, 0, 0));

        return follower;
    }
}