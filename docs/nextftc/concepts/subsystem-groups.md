# OpModes

In NextFTC, an OpMode is the entry point for driver-controlled (TeleOp) or autonomous code. Extend `NextFTCOpMode` and configure components and bindings.

Pattern:

```java
@TeleOp(name = "My TeleOp")
public class MyTeleOp extends NextFTCOpMode {
  public MyTeleOp() {
    addComponents(
      new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
      BulkReadComponent.INSTANCE,
      BindingsComponent.INSTANCE
    );
  }

  @Override public void onStartButtonPressed() {
    // schedule commands or set up driver control
  }
}
```

Lifecycle hooks:

- Constructor: add components.
- `onInit()`: optional pre-start setup.
- `onStartButtonPressed()`: schedule initial commands or driver-control.
- `onStop()`: cleanup.
# Subsystem Groups

Group related subsystems for higher-level coordination (e.g., `Intake + Conveyor + Shooter`). A group can expose composite commands that coordinate timing and state.

Example idea:

```java
public class Manipulator {
  public static Command scoreHigh() {
    return Claw.INSTANCE.close
      .then(Lift.INSTANCE.toHigh)
      .then(Claw.INSTANCE.open);
  }
}
```

Use `.requires(...)` on the underlying commands to ensure mutual exclusion works across the group.

