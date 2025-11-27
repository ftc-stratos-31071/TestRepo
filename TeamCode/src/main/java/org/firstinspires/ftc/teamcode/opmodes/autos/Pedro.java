package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "Auto1")
public class Pedro extends NextFTCOpMode {
    public Pedro() {
        addComponents(
//                new PedroComponent(Constants::createFollower)
                BulkReadComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh,
                new ParallelGroup(
                        Lift.INSTANCE.toMiddle,
                        Claw.INSTANCE.close
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open,
                        Lift.INSTANCE.toLow
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}