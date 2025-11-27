package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.constants.IntakeConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

@Config
public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();

    private Intake() {}

    private final MotorEx intake = new MotorEx("IntakeMotor").brakeMode().reversed();
    private final ServoEx servo = new ServoEx("IntakeServo");
    public final Command moveServoPos = new SetPosition(servo, IntakeConstants.servoPos).requires(this);
    public final Command defaultPos = new SetPosition(servo, IntakeConstants.defaultPos).requires(this);
    public final Command turnOn = new SetPower(intake, IntakeConstants.intakePower).requires(this);
    public final Command turnOnReverse = new SetPower(intake, -IntakeConstants.intakePowerSlow).requires(this);
    public final Command zeroPower = new SetPower(intake, IntakeConstants.zeroPower).requires(this);
    public final Command shoot = new SetPower(intake, IntakeConstants.shootPower).requires(this);
}
