package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.constants.ShooterConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();

    private Intake() {}

    private final MotorEx intake = new MotorEx("intake");
    private final ServoEx servo = new ServoEx("intakeServo");
    public final Command moveServoPos = new SetPosition(servo, IntakeConstants.servoPos).requires(this);
    public final Command defaultPos = new SetPosition(servo, IntakeConstants.defaultPos).requires(this);
    public final Command movePos = new SetPower(intake, IntakeConstants.intakePower).requires(this);
    public final Command zeroPower = new SetPower(intake, IntakeConstants.zeroPower).requires(this);
}
