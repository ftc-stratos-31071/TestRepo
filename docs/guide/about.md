# What is NextFTC?

NextFTC is a simple but powerful set of libraries for FTC robots that encourages a clear architecture:

- Subsystems encapsulate hardware and behavior (e.g., Lift, Claw).
- Commands describe actions and can be composed (then/and) and scheduled.
- Components plug common behaviors into an OpMode (e.g., Bulk reads, gamepad bindings, subsystem lifecycle).

In this repo you already use:

- Subsystems: `Lift` and `Claw` in `TeamCode/src/main/java/.../subsystems`.
- Hardware wrappers: `MotorEx`, `ServoEx` for safer, richer access to hardware.
- Control: `ControlSystem` for PID/feedforward motion control.
- Gamepad DSL: fluent triggers like `gamepad2().rightTrigger().greaterThan(0.2).whenBecomesTrue(...)`.
- Driver control: `MecanumDriverControlled` for holonomic teleop driving.

Why use it?

- Composable: Chain commands to express intent clearly.
- Testable: Subsystems isolate state and logic.
- Performant: Bulk reads and lightweight bindings reduce loop overhead.
- Maintainable: Clear separation of concerns keeps OpModes small and readable.

