package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.ftc.components.BulkReadComponent;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Turret;

@TeleOp(name = "PIDTest")
public class PIDTest extends NextFTCOpMode {
    Limelight3A limelight;

    double motorTargetX = 0.0;
    final double TURRET_LIMIT_DEG = 60.0;  // *** Turret limit set to ±60 degrees ***

    @Override
    public void onInit() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
    }

    public PIDTest() {
        addComponents(
                new SubsystemComponent(Turret.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    public Command firstRoutine(double degrees) {
        return new ParallelGroup(
                Turret.INSTANCE.runTurret(degrees)
        );
    }

    @Override
    public void onUpdate() {
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx();  // Limelight horizontal offset

            motorTargetX = tx;

            // *** Clamp turret angle to ±60° to prevent mechanical damage ***
            motorTargetX = Math.max(-TURRET_LIMIT_DEG, Math.min(TURRET_LIMIT_DEG, motorTargetX));

            telemetry.addData("Target X", tx);
            telemetry.addData("Clamped Turret Target", motorTargetX);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }

        firstRoutine(motorTargetX).invoke();
        telemetry.update();
    }
}