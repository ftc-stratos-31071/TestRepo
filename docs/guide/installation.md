# Installation

These steps assume Android Studio on macOS and an FTC legal environment.

1. Clone this repo and open it in Android Studio.
2. Let Gradle sync; the included modules and dependencies are already configured for NextFTC usage in this project.
3. Select the `FtcRobotController` run configuration and connect an FTC-legal Robot Controller (RC) device.
4. Build and deploy to the RC phone.

Project structure highlights:

- App modules: `FtcRobotController` (app) and `TeamCode` (your code).
- Your TeleOp: `org.firstinspires.ftc.teamcode.opmodes.teleops.Teleop`.
- Subsystems: `org.firstinspires.ftc.teamcode.subsystems.Lift` and `Claw`.

Robot configuration names (change in code or RC config to match your robot):

- Motors: `front_left`, `front_right`, `back_left`, `back_right`, and `lift_motor`.
- Servos: `claw_servo`.

Build tips:

- If Gradle sync fails, ensure Android SDK platforms/build-tools match the project’s gradle settings.
- If hardware names don’t match RC configuration, the app will crash at init; fix either the names in code or the RC configuration.
- Set motor directions using `.reversed()` for wheels that need to be inverted.

