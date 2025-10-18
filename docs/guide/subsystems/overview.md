A Subsystem encapsulates a cohesive piece of hardware and its control logic. It owns devices, exposes Commands to do useful actions, and may implement a `periodic()` loop.

Contract:

- Own hardware (e.g., `MotorEx`, `ServoEx`).
- Provide `Command`s (e.g., `toHigh`, `open`).
- Optionally implement `periodic()` to run control updates.
- Use `requires(this)` on commands to ensure proper mutual exclusion.

Lifecycle:

- Register subsystems by adding a `SubsystemComponent` to your OpMode.
- Commands that require a subsystem will be scheduled and interrupted safely when needed.

Example pattern:

```java
public class ExampleSubsystem implements Subsystem {
  public static final ExampleSubsystem INSTANCE = new ExampleSubsystem();
  private ExampleSubsystem() {}

  private final MotorEx motor = new MotorEx("example");

  public final Command toPosition = new RunToPosition(cs, 500).requires(this);

  @Override public void periodic() {
    motor.setPower(cs.calculate(motor.getState()));
  }
}
```

See the concrete `Lift` and `Claw` pages for details.
# Subsystems Overview


