package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class ShooterOnCmd {

    public static Command create() {

        return new SequentialGroup(
                new ParallelGroup(
                        Intake.INSTANCE.defaultPos,
                        Shooter.INSTANCE.moveShooterReversed,
                        Intake.INSTANCE.turnOnReverse
                ),
                new Delay(0.5),
                Intake.INSTANCE.zeroPower,
                Shooter.INSTANCE.moveShooter
        )
                .requires(Shooter.INSTANCE)
                .requires(Intake.INSTANCE);
    }
}
