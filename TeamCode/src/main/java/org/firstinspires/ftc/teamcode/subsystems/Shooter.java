package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.constants.ShooterConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();

    private Shooter() {}

    private final ServoEx servo = new ServoEx("shooterServo");
    private final MotorEx motor1 = new MotorEx("shooterMotor1").reversed();
    private final MotorEx motor2 = new MotorEx("shooterMotor2");
    public final Command moveServoPos = new SetPosition(servo, ShooterConstants.servoPos).requires(this);
    public final Command defaultPos = new SetPosition(servo, ShooterConstants.defaultPos).requires(this);

    public final Command moveShooter = new SequentialGroup(
            new SetPower(motor1, -ShooterConstants.motorPower).requires(this),
            new SetPower(motor2,  ShooterConstants.motorPower).requires(this)
    ).requires(this);

    public final Command zeroPower = new SequentialGroup(
            new SetPower(motor1, ShooterConstants.zeroPower).requires(this),
            new SetPower(motor2,  ShooterConstants.zeroPower).requires(this)
    ).requires(this);
}
