# Claw Subsystem

File: `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/subsystems/Claw.java`

Hardware:

- Servo: `claw_servo`.

Provided commands:

- `open`  → `SetPosition(0.1)`
- `close` → `SetPosition(0.2)`

Adjusting positions:

- Tune 0.0–1.0 positions to match your linkage and servo horn orientation.
- Use Android Studio logcat/telemetry to print the current target while tuning.

Safety:

- Ensure the servo range is not mechanically over-constrained; consider adding min/max clamps.

Example binding:

```java
Gamepads.gamepad2().leftBumper().whenBecomesTrue(Claw.INSTANCE.open);
Gamepads.gamepad2().rightBumper().whenBecomesTrue(Claw.INSTANCE.close);
```

