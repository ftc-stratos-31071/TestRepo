# Installation

1. Open this project in Android Studio (macOS supported).
2. Connect an FTC-legal Robot Controller device.
3. Build and deploy `FtcRobotController`.

Hardware names to match in RC Configuration:

- Motors: `front_left`, `front_right`, `back_left`, `back_right`, `lift_motor`.
- Servos: `claw_servo`.

Troubleshooting:

- Sync Gradle and ensure SDK versions match project settings.
- Fix device name mismatches to avoid init crashes.
- Reverse motor directions on one side for mecanum.

