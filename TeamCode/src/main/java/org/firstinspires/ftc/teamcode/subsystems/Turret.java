package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.constants.TurretConstants;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.controllable.RunToPosition;

@Config
public class Turret implements Subsystem {
    public static final Turret INSTANCE = new Turret();
    private Turret() {}

    public final MotorEx turret = new MotorEx("turret").reversed();

    private final ControlSystem turretController = ControlSystem.builder()
            .posPid(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD)
            .build();

    public Command runTurret(double degrees) {
        double clicksPerDegree = 1360.0 / 360.0;
        double clicks = clicksPerDegree * degrees;

        // This is correct for your library version.
        return new RunToPosition(turretController, clicks);
    }

    @Override
    public void periodic() {
        turret.setPower(turretController.calculate(turret.getState()));
    }

}