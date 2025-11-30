package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.paths.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.PedroPathingCommand;
import org.firstinspires.ftc.teamcode.constants.PedroConstants;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

/**
 * Autonomous OpMode demonstrating Pedro Pathing 2.0.4 integration with NextFTC.
 *
 * This example:
 * 1. Moves the robot forward 24 inches using Pedro path following
 * 2. Performs a turret sweep sequence (left -> right -> center)
 *
 * The Follower is configured with Pinpoint localizer in PedroConstants.
 */
@Autonomous(name = "Auto2 - Pedro + Turret")
public class Auto2 extends NextFTCOpMode {

    // Define starting and ending poses
    private final Pose startPose = new Pose(0.0, 0.0, Math.toRadians(0.0));
    private final Pose forwardPose = new Pose(24.0, 0.0, Math.toRadians(0.0)); // 24 inches forward

    private PathChain moveForwardPath;
    private Follower follower;

    public Auto2() {
        addComponents(
                new SubsystemComponent(Turret.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    /**
     * Build the path that moves the robot forward 24 inches
     */
    private void buildPaths() {
        // Create a simple forward path using BezierLine
        // BezierLine in Pedro 2.0.4 takes Pose objects directly
        moveForwardPath = follower.pathBuilder()
                .addPath(new BezierLine(startPose, forwardPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), forwardPose.getHeading())
                .build();
    }

    @Override
    public void onInit() {
        // Create the follower instance with Pinpoint localizer
        follower = PedroConstants.createFollower(hardwareMap);

        // Set the starting pose for the follower
        follower.setStartingPose(startPose);

        // Build the paths
        buildPaths();

        // Zero the turret motor on initialization
        Turret.INSTANCE.turret.zeroed();

        // Ensure turret is in command-based control mode (not manual tracking mode)
        Turret.INSTANCE.disableManualControl();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Start Pose", "%.1f, %.1f, %.1f°",
            startPose.getX(), startPose.getY(), Math.toDegrees(startPose.getHeading()));
        telemetry.update();
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }

    /**
     * Autonomous routine: Move forward 24 inches, then sweep the turret
     */
    private Command autonomousRoutine() {
        return new SequentialGroup(
                // Move forward 24 inches using Pedro pathfollowing
                new PedroPathingCommand(follower, moveForwardPath),

                // Small delay to ensure robot has settled
                new Delay(0.5),

                // Turret sweep sequence: left -> right -> center
                Turret.INSTANCE.runTurret(-45.0),  // Turn left 45 degrees
                new Delay(0.5),

                Turret.INSTANCE.runTurret(45.0),   // Turn right 45 degrees
                new Delay(0.5),

                Turret.INSTANCE.runTurret(0.0)     // Return to center
        );
    }

    @Override
    public void onUpdate() {
        // Update telemetry with current robot pose during autonomous
        if (follower != null) {
            Pose currentPose = follower.getPose();
            telemetry.addData("Current Pose", "%.1f, %.1f, %.1f°",
                currentPose.getX(), currentPose.getY(), Math.toDegrees(currentPose.getHeading()));
            telemetry.addData("Is Busy", follower.isBusy());
            telemetry.update();
        }
    }
}
