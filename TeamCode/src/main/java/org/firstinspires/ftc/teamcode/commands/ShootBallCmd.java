package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class ShootBallCmd {

    public static Command create() {

        return new SequentialGroup(
                Intake.INSTANCE.shoot,
                new Delay(0.75),
                Intake.INSTANCE.zeroPower
        )
                .requires(Intake.INSTANCE);
    }
}
