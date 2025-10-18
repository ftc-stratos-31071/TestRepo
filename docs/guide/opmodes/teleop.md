# TeleOp

File: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/teleops/Teleop.java`

Key ideas in this TeleOp:

- Components are added in the OpMode constructor:
  - `SubsystemComponent(Lift, Claw)` registers your subsystems.
  - `BulkReadComponent` enables fast bulk hardware reads.
  - `BindingsComponent` activates the gamepad binding DSL.
- Four drive motors are wrapped in `MotorEx`; left side is reversed for correct mecanum drive.
- `MecanumDriverControlled` maps GP1 sticks to field-centric-esque drive inputs (Y strafe and rotation as configured).
- Gamepad 2 handles the manipulator (Lift + Claw) with clean bindings.

Driver control scheduling:

```java
new MecanumDriverControlled(
  frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor,
  Gamepads.gamepad1().leftStickY(),
  Gamepads.gamepad1().leftStickX(),
  Gamepads.gamepad1().rightStickX()
).schedule();
```

Manipulator bindings:

```java
Gamepads.gamepad2().dpadUp()
  .whenBecomesTrue(Lift.INSTANCE.toHigh)
  .whenBecomesFalse(Claw.INSTANCE.open);

Gamepads.gamepad2().rightTrigger().greaterThan(0.2)
  .whenBecomesTrue(Claw.INSTANCE.close.then(Lift.INSTANCE.toHigh));

Gamepads.gamepad2().leftBumper()
  .whenBecomesTrue(Claw.INSTANCE.open.and(Lift.INSTANCE.toLow));
```

Notes:

- Use thresholds (e.g., 0.2) for analog triggers to avoid accidental activation.
- Update hardware names if your RC configuration uses different device names.
- If your robot drives backward/sideways, adjust motor reversal or stick mapping.

