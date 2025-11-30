package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.constants.ShooterConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

@Config
public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();

    private Shooter() {}

    private final ServoEx servo = new ServoEx("HoodServo");
    private final MotorEx motor1 = new MotorEx("ShooterRight").brakeMode();
    private final MotorEx motor2 = new MotorEx("ShooterLeft").brakeMode().reversed();

    // PID control system for velocity control
    private final ControlSystem velocityController = ControlSystem.builder()
            .velPid(ShooterConstants.velocityKp,
                    ShooterConstants.velocityKi,
                    ShooterConstants.velocityKd)
            .build();

    private double targetVelocity = 0;
    private boolean velocityControlActive = false;

    // Dynamic servo commands - read values from ShooterConstants each time
    public final Command moveServoPos = new Command() {
        @Override
        public boolean isDone() {
            servo.setPosition(ShooterConstants.servoPos);
            return true;
        }
    }.requires(this);

    public final Command defaultPos = new Command() {
        @Override
        public boolean isDone() {
            servo.setPosition(ShooterConstants.defaultPos);
            return true;
        }
    }.requires(this);

    /**
     * Run shooter at a specific power (legacy method, not PID controlled)
     */
    public Command moveShooter(double shooterPower) {
        return new ParallelGroup(
                new SetPower(motor1, shooterPower),
                new SetPower(motor2, shooterPower)
        ).requires(this);
    }

    /**
     * Run shooter with PID velocity control at target RPM (battery-compensated)
     * This is the preferred method for consistent shooting
     */
    public Command runAtTargetRPM() {
        return runAtRPM(ShooterConstants.targetRPM);
    }

    /**
     * Run shooter with PID velocity control at specified RPM
     */
    public Command runAtRPM(double targetRPM) {
        return new ShooterVelocityCommand(targetRPM);
    }

    public Command moveServo(double servoPos) {
        return new SetPosition(servo, servoPos).requires(this);
    }

    // Dynamic commands that read from constants
    public final Command moveShooterReversed = new Command() {
        public void execute() {
            motor1.setPower(-ShooterConstants.motorPower);
            motor2.setPower(-ShooterConstants.motorPower);
        }

        @Override
        public boolean isDone() {
            return false;
        }
    }.requires(this);

    public final Command zeroPower = new Command() {
        @Override
        public boolean isDone() {
            motor1.setPower(ShooterConstants.zeroPower);
            motor2.setPower(ShooterConstants.zeroPower);
            return true;
        }
    }.requires(this);

    /**
     * Get current shooter RPM
     */
    public double getRPM() {
        double ticksPerSecond = motor1.getVelocity();
        return (ticksPerSecond / 112.0) * 60.0;
    }

    /**
     * Check if shooter is at target velocity (within tolerance)
     */
    public boolean isAtTargetRPM() {
        double currentRPM = getRPM();
        return Math.abs(currentRPM - ShooterConstants.targetRPM) < ShooterConstants.rpmTolerance;
    }

    /**
     * Check if shooter is at a specific RPM (within tolerance)
     */
    public boolean isAtRPM(double targetRPM) {
        double currentRPM = getRPM();
        return Math.abs(currentRPM - targetRPM) < ShooterConstants.rpmTolerance;
    }

    @Override
    public void periodic() {
        // Apply velocity control if active
        if (velocityControlActive) {
            // Get current motor state (includes position, velocity, etc.)
            // Calculate PID output using motor state
            double pidOutput = velocityController.calculate(motor1.getState());

            // Add feedforward term
            double feedforward = targetVelocity * ShooterConstants.velocityKf;

            // Combine PID + feedforward and clamp to [-1, 1]
            double power = Math.max(-1, Math.min(1, pidOutput + feedforward));

            // Apply power to both motors
            motor1.setPower(power);
            motor2.setPower(power);
        }
    }

    /**
     * Custom command for velocity-controlled shooting
     */
    private class ShooterVelocityCommand extends Command {
        private final double targetTicksPerSecond;

        public ShooterVelocityCommand(double targetRPM) {
            this.targetTicksPerSecond = (targetRPM * 112.0) / 60.0;
            requires(Shooter.INSTANCE);
        }

        public void init() {
            // Set target velocity and enable velocity control
            targetVelocity = targetTicksPerSecond;
            velocityControlActive = true;
            velocityController.reset();
        }

        public void execute() {
            // Velocity control happens in periodic()
        }

        public void end(boolean interrupted) {
            // Disable velocity control when command ends
            velocityControlActive = false;
            motor1.setPower(0);
            motor2.setPower(0);
        }

        @Override
        public boolean isDone() {
            // This command runs continuously until interrupted
            return false;
        }
    }
}
