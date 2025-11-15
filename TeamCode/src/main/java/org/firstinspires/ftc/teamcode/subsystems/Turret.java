package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.constants.TurretConstants;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Turret implements Subsystem {
    public static final Turret INSTANCE = new Turret();

    private Turret() {}

    private final MotorEx turret = new MotorEx("turret");
    private ControlSystem controlSystem = ControlSystem.builder()
            .posPid(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD)
            .build();

}
