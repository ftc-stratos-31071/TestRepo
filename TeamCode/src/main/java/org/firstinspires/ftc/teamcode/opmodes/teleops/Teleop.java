package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.IntakeSeqCmd;
import org.firstinspires.ftc.teamcode.commands.ShooterOffCmd;
import org.firstinspires.ftc.teamcode.commands.ShooterOnCmd;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "TeleOp")
public class Teleop extends NextFTCOpMode {
    public Teleop() {
        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Shooter.INSTANCE, Turret.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final MotorEx frontLeftMotor = new MotorEx("frontLeftMotor").brakeMode().reversed();
    private final MotorEx frontRightMotor = new MotorEx("frontRightMotor").brakeMode();
    private final MotorEx backLeftMotor = new MotorEx("backLeftMotor").brakeMode().reversed();
    private final MotorEx backRightMotor = new MotorEx("backRightMotor").brakeMode();

    @Override
    public void onInit() {
        Intake.INSTANCE.defaultPos.schedule();
        Shooter.INSTANCE.defaultPos.schedule();
        Turret.INSTANCE.turret.zeroed();
        Turret.INSTANCE.runTurret(0);
    }

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX().negate(),
                Gamepads.gamepad1().rightStickX().negate()
        );
        driverControlled.schedule();

        Gamepads.gamepad1().leftBumper().whenBecomesTrue(IntakeSeqCmd.create());
        Gamepads.gamepad1().leftBumper().whenBecomesFalse(ShooterOffCmd.create());

        Gamepads.gamepad1().rightBumper().whenBecomesTrue(ShooterOnCmd.create());

        Gamepads.gamepad1().a().whenBecomesTrue(Intake.INSTANCE.turnOn);
        Gamepads.gamepad1().a().whenBecomesFalse(Intake.INSTANCE.zeroPower);

        Gamepads.gamepad1().b().whenBecomesTrue(Shooter.INSTANCE.zeroPower);

        Gamepads.gamepad1().dpadDown().whenBecomesTrue(Intake.INSTANCE.defaultPos);
        Gamepads.gamepad1().dpadUp().whenBecomesTrue(Intake.INSTANCE.moveServoPos);
    }
}