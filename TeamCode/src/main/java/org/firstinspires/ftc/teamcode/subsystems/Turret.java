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
    private Turret() { }

    private final MotorEx turret = new MotorEx("turret");

    private final ControlSystem turretController = ControlSystem.builder()
            .posPid(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD)
            .build();

    public Command runTurret(double degrees){
        double clicksDegreeProportion = 1440.0/360.0;
        double clicks = clicksDegreeProportion * degrees;
        return new RunToPosition(turretController, clicks);
    }

    @Override
    public void periodic() {
        turret.setPower(turretController.calculate(turret.getState()));
    }
}