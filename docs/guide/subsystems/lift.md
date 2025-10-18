# Lift Subsystem

File: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/subsystems/Lift.java`

Hardware and control:

- Motor: `lift_motor` (rename as needed to match RC config).
- Control: `ControlSystem` with position PID and optional elevator feedforward.

Provided commands:

- `toLow` → target 0 ticks.
- `toMiddle` → target 500 ticks.
- `toHigh` → target 1200 ticks.

Behavior:

- `periodic()` sets motor power using `controlSystem.calculate(motor.getState())`.

Tuning guide:

1. Start with only P gain (I=0, D=0). Raise P until it moves crisply with minimal oscillation.
2. If overshoot or ringing occurs, add some D.
3. If there’s steady-state error under load, add a small I or use `elevatorFF` to offset gravity.
4. Validate at all waypoints (low/mid/high) and with game loads.

Usage example:

```java
Gamepads.gamepad2().dpadUp().whenBecomesTrue(Lift.INSTANCE.toHigh);
Gamepads.gamepad2().dpadLeft().whenBecomesTrue(Lift.INSTANCE.toMiddle);
Gamepads.gamepad2().dpadDown().whenBecomesTrue(Lift.INSTANCE.toLow);
```

Notes:

- Ensure the encoder direction matches the sign of the setpoints. If moving the wrong way, reverse the motor in hardware config or code.
- Clamp physical limits in your control logic if your mechanism can hard-stop.

