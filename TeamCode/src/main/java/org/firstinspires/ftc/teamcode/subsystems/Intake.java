package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.constants.IntakeConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;

@Config
public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();

    private Intake() {}

    private final MotorEx intake = new MotorEx("IntakeMotor").brakeMode().reversed();
    private final ServoEx servo = new ServoEx("IntakeServo");

    // Dynamic servo commands - read values from IntakeConstants each time
    public final Command moveServoPos = new Command() {
        @Override
        public boolean isDone() {
            servo.setPosition(IntakeConstants.servoPos);
            return true;
        }
    }.requires(this);

    public final Command defaultPos = new Command() {
        @Override
        public boolean isDone() {
            servo.setPosition(IntakeConstants.defaultPos);
            return true;
        }
    }.requires(this);

    // Dynamic motor commands - read values from IntakeConstants each time
    public final Command turnOn = new Command() {
        public void execute() {
            intake.setPower(IntakeConstants.intakePower);
        }

        @Override
        public boolean isDone() {
            return false;
        }
    }.requires(this);

    public final Command turnOnReverse = new Command() {
        public void execute() {
            intake.setPower(-IntakeConstants.intakePowerSlow);
        }

        @Override
        public boolean isDone() {
            return false;
        }
    }.requires(this);

    public final Command zeroPower = new Command() {
        @Override
        public boolean isDone() {
            intake.setPower(IntakeConstants.zeroPower);
            return true;
        }
    }.requires(this);

    public final Command shoot = new Command() {
        public void execute() {
            intake.setPower(IntakeConstants.shootPower);
        }

        @Override
        public boolean isDone() {
            return false;
        }
    }.requires(this);
}
