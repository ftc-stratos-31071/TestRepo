package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.constants.IntakeConstants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();

    private Intake() {}

    private final MotorEx intake = new MotorEx("intake");
    public final Command movePos = new SetPower(intake, IntakeConstants.intakePower).requires(this);
    public final Command zeroPower = new SetPower(intake, IntakeConstants.zeroPower).requires(this);
}
