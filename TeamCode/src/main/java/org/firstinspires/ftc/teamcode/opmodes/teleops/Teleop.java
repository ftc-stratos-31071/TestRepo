package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.IntakeSeqCmd;
import org.firstinspires.ftc.teamcode.commands.ShootBallCmd;
import org.firstinspires.ftc.teamcode.commands.ShooterOffCmd;
import org.firstinspires.ftc.teamcode.commands.ShooterOnCmd;
import org.firstinspires.ftc.teamcode.constants.ShooterConstants;
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

    Limelight3A limelight;
    double motorTargetX = 0.0;
    final double TURRET_LIMIT_DEG = 90.0;

    double servoPos = ShooterConstants.defaultPos;
    double shooterPower = 0.5;

    @Override
    public void onInit() {
        Intake.INSTANCE.defaultPos.schedule();
        Shooter.INSTANCE.defaultPos.schedule();
        Turret.INSTANCE.turret.zeroed();
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
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

        Gamepads.gamepad1().dpadRight().whenBecomesTrue(() -> {
            shooterPower = Math.min(1.0, shooterPower + 0.1);
        });

        Gamepads.gamepad1().dpadLeft().whenBecomesTrue(() -> {
            shooterPower = Math.max(0.0, shooterPower - 0.1);
        });

        Gamepads.gamepad1().rightBumper().whenBecomesTrue(() -> {
            shooterStartTime = System.currentTimeMillis();
            shooterTiming = true;
            ShooterOnCmd.create(shooterPower).schedule();
        });

//        Gamepads.gamepad1().a().whenBecomesTrue(ShootBallCmd.create());

        Gamepads.gamepad1().a().whenBecomesTrue(Intake.INSTANCE.turnOn);
        Gamepads.gamepad1().a().whenBecomesFalse(Intake.INSTANCE.zeroPower);

        Gamepads.gamepad1().b().whenBecomesTrue(Shooter.INSTANCE.zeroPower);

        // servo increment 0.1 steps
        Gamepads.gamepad1().dpadUp().whenBecomesTrue(() -> {
            servoPos = Math.min(1.0, servoPos - 0.1);
            Shooter.INSTANCE.moveServo(servoPos).schedule();
        });

        Gamepads.gamepad1().dpadDown().whenBecomesTrue(() -> {
            servoPos = Math.max(0.0, servoPos + 0.1);
            Shooter.INSTANCE.moveServo(servoPos).schedule();
        });

//        Gamepads.gamepad1().dpadDown().whenBecomesTrue(Shooter.INSTANCE.defaultPos);
//        Gamepads.gamepad1().dpadUp().whenBecomesTrue(Shooter.INSTANCE.moveServoPos);
    }

    boolean hasRumbled = false;

    // TIMER VARIABLES
    private long shooterStartTime = 0;
    private boolean shooterTiming = false;

    @Override
    public void onUpdate() {
        double rpm = Shooter.INSTANCE.getRPM() * 5;
        double targetRPM = shooterPower * 6000;
        LLResult result = limelight.getLatestResult();

        boolean shooterReady = Math.abs(rpm - targetRPM) < (targetRPM * 0.05);

        // timing logic
        long spinUpTimeMs = 0;
        if (shooterTiming) {
            spinUpTimeMs = System.currentTimeMillis() - shooterStartTime;

            if (shooterReady) {
                shooterTiming = false;
            }
        }

        // rumble when ready
        if (shooterReady && !hasRumbled) {
            Gamepads.gamepad1().getGamepad().invoke().rumble(500);
            hasRumbled = true;
        }

        if (!shooterReady) {
            hasRumbled = false;
        }

        telemetry.addData("Shooter RPM", rpm);
        telemetry.addData("Target RPM", targetRPM);
        telemetry.addData("Ready?", shooterReady);

        if (shooterTiming) {
            telemetry.addData("Spin-up Time (ms)", spinUpTimeMs);
        } else if (shooterReady) {
            telemetry.addData("Final Spin-up Time (ms)", spinUpTimeMs);
        }

        telemetry.addData("Shooter Power", shooterPower);

        if (result != null && result.isValid()) {
            double tx = result.getTx();

            motorTargetX = tx * 1.25;
            motorTargetX = Math.max(-TURRET_LIMIT_DEG, Math.min(TURRET_LIMIT_DEG, motorTargetX));

            telemetry.addData("Target X", tx);
            telemetry.addData("Clamped Turret Target", motorTargetX);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
        Turret.INSTANCE.runTurret(motorTargetX).schedule();

        telemetry.update();
    }
}
