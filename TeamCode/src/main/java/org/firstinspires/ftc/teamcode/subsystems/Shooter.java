package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.constants.ShooterConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
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
    public final Command moveServoPos = new SetPosition(servo, ShooterConstants.servoPos).requires(this);
    public final Command defaultPos = new SetPosition(servo, ShooterConstants.defaultPos).requires(this);

    public final Command moveShooter = new ParallelGroup(
            new SetPower(motor1, ShooterConstants.motorPower).requires(this),
            new SetPower(motor2,  ShooterConstants.motorPower).requires(this)
    ).requires(this);

    public final Command moveShooterReversed = new ParallelGroup(
            new SetPower(motor1, -ShooterConstants.motorPower).requires(this),
            new SetPower(motor2,  -ShooterConstants.motorPower).requires(this)
    ).requires(this);

    public final Command zeroPower = new ParallelGroup(
            new SetPower(motor1, ShooterConstants.zeroPower).requires(this),
            new SetPower(motor2,  ShooterConstants.zeroPower).requires(this)
    ).requires(this);
}
